package rs.ac.uns.ftn.xws.interceptors;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

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
        String URL = request.getRequestURL().toString();
        String[] parts = URL.split("/");
        for (int i=0; i< parts.length; i++)
            if (parts[i].equals("partneri") && parts.length > i+1) {
                if (!partnerDao.isBusinessPartner(parts[i+1])) {
                    System.out.println(parts[i+1]);
                    throw new ServiceException("Partner does not exist. ", Status.FORBIDDEN);
                }
            }
		Object result = context.proceed();
		return result;
	}
}
