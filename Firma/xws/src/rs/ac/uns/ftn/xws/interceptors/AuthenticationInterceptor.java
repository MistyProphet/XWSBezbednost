package rs.ac.uns.ftn.xws.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.service.Service;
import rs.ac.uns.ftn.xws.entities.user.User;
import rs.ac.uns.ftn.xws.util.Authenticate;
import rs.ac.uns.ftn.xws.util.ServiceException;
import rs.ac.uns.ftn.xws.xmldb.EntityManagerBaseX;

@Interceptor
@Authenticate
public class AuthenticationInterceptor {
    //FIXME Far from optimal. Cache results somewhere.    
    //
    private static final String base_url = "http://localhost:8080/Firma/api/";

	public AuthenticationInterceptor() {
		super();
	}

	@Context
	private HttpServletRequest request;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if (user == null)
			throw new ServiceException("Not logged in", Status.UNAUTHORIZED);
		
        Service service = getService(context.getMethod()); 
        if (userHasPrivileges(user, service)) {
		    Object result = context.proceed();
		    return result;
        } else {
            String roles = ""
            for (String role : service.getRolesAllowed().getRole())
                roles.concat(" / " + role);

        roles.conca
            throw new ServiceException("You need to be " + service. + " to access the " + service.getName() + " service. ");
	}
    
    private boolean userHasPrivileges(User user, Service service) {
        Set<String> userRoles = new HashSet<String>(user.getRoles().getRole());
        Set<String> serviceRoles = new HashSet<String>(service.getRolesAllowed().getRole());
        userRoles.retainAll(serviceRoles);
        return !userRoles.isEmpty();
    }

    private Service getService(Method method) throws IOException, JAXBException {
        String methodName = method.getName();

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(base_url + "service/" + methodName); 
        HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("Failed to get roles: \nStatus code: " + response.getStatusLine().getStatusCode());
        
        return unmarshallService(response.getEntity().getContent());
    }

    private Service unmarshallService(InputStream stream) throws JAXBException {
        EntityManagerBaseX<Service, Long> em = new EntityManagerBaseX<Service, Long>("service", "rs.ac.uns.ftn.xws.entities.service");
        return (Service) em.getUnmarshaller().unmarshal(stream);
    }
}
