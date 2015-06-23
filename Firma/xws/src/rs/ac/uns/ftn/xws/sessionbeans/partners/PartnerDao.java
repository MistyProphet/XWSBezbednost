package rs.ac.uns.ftn.xws.sessionbeans.partners;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import rs.ac.uns.ftn.xws.entities.partner.Partner;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDao;

@Stateless
@Local(PartnerDaoLocal.class)
public class PartnerDao extends GenericDao<Partner, Long> implements PartnerDaoLocal {
    
    @Context
	private HttpServletRequest request;

	public static final String contextPath = "rs.ac.uns.ftn.xws.entities.partner";
	
	public static final String schemaName = "partner";
	
	public PartnerDao() {
		super(contextPath, schemaName);
	}

    public List<String> findAllTINs() throws IOException {
        List<String> tins = new ArrayList<String>();

        InputStream stream = em.executeQuery(
                "(//TIN)",
                false);

        String result = IOUtils.toString(stream);
        String[] results = result.split("\n");
        for (int i=0; i < results.length; i++) {
            String r = results[i];
            r = r.replace("<TIN>", "");
            r = r.replace("</TIN>", "");
            r = r.trim();
            results[i] = r;
        }

        return Arrays.asList(results);
    }

    public Boolean isBusinessPartner(String TIN) throws IOException, JAXBException {
        List<Partner> partners = em.runQuery("partner", "(//Partner[TIN=\"" + TIN + "\"])");
        System.out.println("size   : " + partners.size());
        if (partners.size() > 0)
            return true;
        return false;
    }
}
