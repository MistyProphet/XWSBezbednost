package rs.ac.uns.ftn.xws.interceptors;

import java.io.IOException;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import rs.ac.uns.ftn.xws.entities.users.User;
import rs.ac.uns.ftn.xws.sessionbeans.partners.PartnerDaoLocal;
import rs.ac.uns.ftn.xws.util.Authenticate;
import rs.ac.uns.ftn.xws.util.ServiceException;
import rs.ac.uns.ftn.xws.util.ValidateSupplier;

@Interceptor
@ValidateSupplier
public class PINInterceptor {

	private static Logger log = Logger.getLogger(PINInterceptor.class);

    @EJB
    private PartnerDaoLocal partnerDao;

	@Context
	private HttpServletRequest request;

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception{
        // Throws errors and causes responses if something is wrong. Exits immediately.
        processURL(request);

        try {
		    Object result = context.proceed();
		    return result;
        } catch (JAXBException e) {
            System.out.println("#################################################################");
            e.printStackTrace();
            throw new ServiceException("Document not found. ", Status.NOT_FOUND);
        } catch (IOException e) {
            System.out.println("#################################################################");
            e.printStackTrace();
            throw new ServiceException("Document not found. ", Status.SERVICE_UNAVAILABLE);
        } catch (Exception e) { 
            System.out.println("#################################################################");
            e.printStackTrace();
            throw new ServiceException("Strange error. ", Status.NOT_ACCEPTABLE);
        }
	}

    private void processURL(HttpServletRequest request) {
        String URL = request.getRequestURL().toString();
        String[] parts = URL.split("/");
        for (int i=0; i< parts.length; i++) {
            if (parts[i].equals("partneri") && parts.length > i+1) {
                if (!partnerDao.isBusinessPartner(parts[i+1])) {
                    if (request.getMethod().equals("POST") || 
                        request.getMethod().equals("PUT")  ||
                        request.getMethod().equals("DELETE"))
                            throw new ServiceException("Partner does not exist. ", Status.FORBIDDEN);
                    else if (request.getMethod().equals("GET"))
                        throw new ServiceException("Resource not found. ", Status.NOT_FOUND);
                }
            }
        }
    }
}
