package rs.ac.uns.ftn.xws.sessionbeans.partners;

import java.io.IOException;
import java.util.List;

import rs.ac.uns.ftn.xws.entities.partner.Partner;
import rs.ac.uns.ftn.xws.sessionbeans.common.GenericDaoLocal;

public interface PartnerDaoLocal extends GenericDaoLocal<Partner, Long>{

    public List<String> findAllTINs() throws IOException;
    public Boolean isBusinessPartner(String TIN);

}
