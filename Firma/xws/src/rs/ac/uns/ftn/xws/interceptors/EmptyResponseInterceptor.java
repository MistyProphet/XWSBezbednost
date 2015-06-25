//package rs.ac.uns.ftn.xws.interceptors;
//
//import java.io.IOException;
//
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.Interceptor;
//import javax.interceptor.InvocationContext;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import javax.ws.rs.NotFoundException;
//import javax.ws.rs.BadRequestException;
//import javax.xml.bind.JAXBException;
//
//import rs.ac.uns.ftn.xws.util.FourOhFour;
//import rs.ac.uns.ftn.xws.util.ServiceException;
//
//@Interceptor
//@FourOhFour
//public class EmptyResponseInterceptor {
//
//	@AroundInvoke
//	public Object intercept(InvocationContext context) throws Exception {
//        try { 
//		    Object result = context.proceed();
//            if (result == null) {
//                System.out.println("Triggered!");
//                throw new NotFoundException("Resource not found. ");
//            }
//		    return result;
//        } catch (IOException e) {
//            throw new ServiceException("Resource not found. ", Status.NOT_FOUND);
//        } catch (JAXBException e) {
//            throw new ServiceException("Error parsing document. ", Status.BAD_REQUEST); 
//        } catch (Exception e) {
//            throw new ServiceException("Unencountered error", Status.SERVICE_UNAVAILABLE);
//        }
//	}
//}
