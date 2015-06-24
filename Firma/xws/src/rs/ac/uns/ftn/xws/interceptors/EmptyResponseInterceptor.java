package rs.ac.uns.ftn.xws.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import javax.ws.rs.NotFoundException;

import rs.ac.uns.ftn.xws.util.FourOhFour;

@Interceptor
@FourOhFour
public class EmptyResponseInterceptor {

	@AroundInvoke
	public Object intercept(InvocationContext context) throws NullPointerException {
        try { 
		    Object result = context.proceed();
            if (result == null) {
                System.out.println("Triggered!");
                throw new NullPointerException("Resource not found. ");
            }
		    return result;
        } catch (Exception e) {
            //FIXME Hack. Should find a way to catch null pointer errors 
            //and convert them to something meaningful later.
            
            throw new NotFoundException("Resource not found. ");
        }
	}
}
