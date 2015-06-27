package rs.ac.uns.ftn.xws.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.sessionbeans.services.ServiceDaoLocal;
import rs.ac.uns.ftn.xws.util.Authenticate;
import rs.ac.uns.ftn.xws.util.ServiceException;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

@Interceptor
@Authenticate
public class AuthenticationInterceptor {
    private static final String base_url = "http://localhost:8080/Firma/api/";

    private DefaultHttpClient client;
    private EntityManagerBaseX<Service, Long> em; 

	public AuthenticationInterceptor() throws JAXBException {
		super();
        client = new DefaultHttpClient();
        em = new EntityManagerBaseX<Service, Long>("service", "rs.ac.uns.ftn.xws.entities.service");
	}

	@Context
	private HttpServletRequest request;

    @EJB
    private ServiceDaoLocal serviceDao;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception{
        Service service = getService(context.getMethod()); 
        // roles required
        if (service != null && service.getRolesAllowed() != null && service.getRolesAllowed().getRole().size() != 0) {
		    User user = (User) request.getSession().getAttribute("user");
		    if (user == null)
		    	throw new ServiceException("Not logged in", Status.UNAUTHORIZED);
		    
            if (!userHasPrivileges(user, service)) {
                String roles = "";
                for (String role : service.getRolesAllowed().getRole())
                    roles.concat(" / " + role);
                throw new ServiceException("Not authorized. \nRoles required: "+roles, Status.UNAUTHORIZED);
            }
        }
		Object result = context.proceed();
		return result;
	}
    
    private boolean userHasPrivileges(User user, Service service) {
        Set<String> userRoles = new HashSet<String>(user.getRoles().getRole());
        Set<String> serviceRoles = new HashSet<String>(service.getRolesAllowed().getRole());
        //service doesn't require a role
        if (serviceRoles.isEmpty())
            return true;
        userRoles.retainAll(serviceRoles);
        return !userRoles.isEmpty();
    }

    private Service getService(Method method) throws IOException, JAXBException {
        String methodName = method.getName();

        HttpGet get = new HttpGet(base_url + "service/" + methodName); 
        HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            //FIXME
            return fillMissingService(method.getName());
            //throw new RuntimeException("Failed to get roles for " + method.getName() + ": \nStatus code: " + response.getStatusLine().getStatusCode());
        } 

        return unmarshallService(response.getEntity().getContent());
    }

    private Service fillMissingService(String methodName) throws IOException, JAXBException {
        //FIXME dont use a dao, use REST!
        Service newService = new Service();
        newService.setRolesAllowed(new Service.RolesAllowed());

        newService.setName(methodName);

        serviceDao.persist(newService);
        return newService;
    }

    private Service unmarshallService(InputStream stream) throws JAXBException {
        return (Service) em.getUnmarshaller().unmarshal(stream);
    }
}
