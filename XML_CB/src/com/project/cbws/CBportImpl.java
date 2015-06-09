
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.project.cbws;

import java.math.BigDecimal;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import misc.RESTUtil;

import com.project.common_types.TBanka;
import com.project.mt103.Mt103;
import com.project.mt103.Mt103.PodaciOBankama;
import com.project.mt900.Mt900Clearing;
import com.project.mt900.Mt900RTGS;
import com.project.mt910.Mt910;
import com.project.nalog_za_placanje.Uplata;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-08T18:34:00.001+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "CBservice",
                      portName = "CBport",
                      targetNamespace = "http://www.project.com/CBws",
                      wsdlLocation = "WEB-INF/wsdl/Banka.wsdl",
                      endpointInterface = "com.project.cbws.CBport")
                      
public class CBportImpl implements CBport {

    private static final Logger LOG = Logger.getLogger(CBportImpl.class.getName());

    /* (non-Javadoc)
     * @see com.project.cbws.CBport#recieveMT102CB(com.project.mt102.Mt102  mt102 )*
     */
    public com.project.mt900.Mt900Clearing recieveMT102CB(com.project.mt102.Mt102 mt102) throws RecieveMT102Fault    { 
    	try { // TODO 4 sve poruke MT103, MT900 i MT910 snimiti u bazu (rekao
			// cverdelj) npr CB/MT103 itd..

		if (CBUtil.ClearingTransaction(mt102)) {

			RESTUtil.objectToDB("Poruke/MT102", mt102.getIDPoruke(), mt102);
		//	Service service = Service.create(new URL("wsdlbanke"),
			//		new QName("servisbanke"));
			// NjihovaBankaPort bankaPort =
			// service.getPort("portName",NjihovaBankaPort.class);
			Mt910 mt910 = new Mt910(mt102);
			// bankaPort.recieveMT910(mt910);
			RESTUtil.objectToDB("Poruke/MT910", mt910.getIDPoruke(), mt910);
			// bankaPort.recieveMT103(mt103);
			Mt900Clearing mt900 = new Mt900Clearing(mt102);
			mt900.setIDPoruke("1");
			RESTUtil.objectToDB("Poruke/MT900", mt900.getIDPoruke(), mt900);
			return mt900;
		} else {
			throw new RecieveMT102Fault("sendMT102fault...");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	throw new RecieveMT102Fault("sendMT102fault...");
  }

    /* (non-Javadoc)
     * @see com.project.cbws.BankaPort#recieveMT103CB(com.project.mt103.Mt103  mt103 )*
     */
    public com.project.mt900.Mt900RTGS recieveMT103CB(com.project.mt103.Mt103 mt103) throws RecieveMT103Fault    { 

		// TODO 1 prebaciti pare sa jednog racuna na drugi
		/*
		 * iz CB/Racuni izvuci podatke o racunima banke C i D. Uvecati racun D
		 * za iznos, a C smanjiti uz proveru da ne ode u minus ili ispod
		 * likvidnosti
		 */
		// TODO 2 Instancirati servis banke D i poslati joj MT103 i MT910
		/*
		 * Service service = Service.create(wsd banke D, serviceName); i pozvati
		 * metodu primi MT103 i primi MT910
		 */
		// TODO 3 vratiti MT900
		try { // TODO 4 sve poruke MT103, MT900 i MT910 snimiti u bazu (rekao
				// cverdelj) npr CB/MT103 itd..

			if (CBUtil.RTGSTransaction(mt103.getPodaciOBankama().getBankaDuznika().getSWIFTKod(), mt103.getPodaciOBankama()
					.getBankaPoverioca().getSWIFTKod(), mt103.getUplata().getIznos())) {

				RESTUtil.objectToDB("Poruke/MT103", mt103.getIDPoruke(), mt103);
			//	Service service = Service.create(new URL("wsdlbanke"),
				//		new QName("servisbanke"));
				// NjihovaBankaPort bankaPort =
				// service.getPort("portName",NjihovaBankaPort.class);
				Mt910 mt910 = new Mt910(mt103);
				// bankaPort.recieveMT910(mt910);
				RESTUtil.objectToDB("Poruke/MT910", mt910.getIDPoruke(), mt910);
				// bankaPort.recieveMT103(mt103);
				Mt900RTGS mt900 = new Mt900RTGS(mt103);
				mt900.setIDPoruke(("1"));
				RESTUtil.objectToDB("Poruke/MT900", mt900.getIDPoruke(), mt900);
				return mt900;
			} else {
				throw new RecieveMT103Fault("sendMT103fault...");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RecieveMT103Fault("sendMT103fault...");
	}

	public static void main(String[] args) {

		Mt103 request = new Mt103();
		request.setIDPoruke("CCtRS04");
		PodaciOBankama pb = new PodaciOBankama();
		TBanka duznik = new TBanka();
		duznik.setBrojRacunaBanke("111-1231231231231-32");
		duznik.setSWIFTKod("AAAARS01");
		TBanka poverioc = new TBanka();
		poverioc.setBrojRacunaBanke("111-1231555551231-32");
		poverioc.setSWIFTKod("BBBBRS01");
		
		pb.setBankaDuznika(duznik);
	pb.setBankaPoverioca(poverioc);
		request.setPodaciOBankama(pb);
		
		Uplata u = new Uplata();
		u.setIznos(new BigDecimal(4354));
		
		request.setUplata(u);
		

		CBportImpl b = new CBportImpl();
		try {
			b.recieveMT103CB(request);
		} catch (RecieveMT103Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}