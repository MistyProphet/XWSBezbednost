package misc;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.basex.BaseXHTTP;

import com.project.common_types.TBankarskiRacunKlijenta;
import com.project.common_types.TRacunKlijenta;
import com.project.document.crypto.sig.CertificateReader;
import com.project.nalog_za_placanje.Uplata;
import com.project.stavka_preseka.StavkaPreseka;
import com.project.stavka_preseka.Transakcija;
import com.project.stavka_preseka.TransakcijaService;
import com.project.transakcije.Transakcije;
import com.project.util.Util;


/**
 * Klasa demonstrira upotrebu REST API-a BaseX XML baze podataka.
 * Sadrzi set reusable CRUD operacija, sa primerom njihove upotrebe. 
 * 
 * @author Igor Cverdelj-Fogarasi
 *
 */
public class RESTUtil<T> {

	public static String REST_URL = "";
	
	static {
        ResourceBundle b = ResourceBundle.getBundle ("resource.basex");
        REST_URL =  (String) b.getObject("rest.url");
	}
	
	public static void main(String[] args) throws Exception {

		BaseXHTTP http = null;
		
		
		File file = new File("src/resource/");
		dropSchema("Banke");
		dropSchema("Banka");

		createSchema("Banka");
		createSchema("Banke");
		createResource("Banke", "Podaci", new FileInputStream(new File(file, "banke.xml")));

		createResource("Banka/001", "Nalozi", new FileInputStream(new File(file, "NalogZaPlacanje.xml")));
		createResource("Banka/001", "MT900rtgs", new FileInputStream(new File(file, "MT900.xml")));
		createResource("Banka/001", "MT900clearing", new FileInputStream(new File(file, "MT900.xml")));
		createResource("Banka/001", "MT910", new FileInputStream(new File(file, "MT910.xml")));
		createResource("Banka/001", "indeksiPoruka", new FileInputStream(new File(file, "indeksiPoruka.xml")));
		
		createResource("Banka/002", "Nalozi", new FileInputStream(new File(file, "NalogZaPlacanje.xml")));
		createResource("Banka/002", "MT900rtgs", new FileInputStream(new File(file, "MT900.xml")));
		createResource("Banka/002", "MT900clearing", new FileInputStream(new File(file, "MT900.xml")));
		createResource("Banka/002", "MT910", new FileInputStream(new File(file, "MT910.xml")));
		createResource("Banka/002", "indeksiPoruka", new FileInputStream(new File(file, "indeksiPoruka.xml")));
		
		createResource("Banka/001", "Racuni", new FileInputStream(new File(file, "klijenti1.xml")));
		createResource("Banka/002", "Racuni", new FileInputStream(new File(file, "klijenti2.xml")));
		
		//Generisanje podataka za crl listu
		CertificateReader.readFromBase64EncFile();
		
		Transakcije ttt = new Transakcije();
		//Generisanje 3 test transakcije
		Transakcija t1 = new Transakcija();
		t1.setId(new Long(11));
		TRacunKlijenta rac1 = new TRacunKlijenta();
		rac1.setBrojRacuna("001-0000000000001-00");
		rac1.setId(new Long(1));
		rac1.setVlasnik("Pera Peric");
		TBankarskiRacunKlijenta racun1 = new TBankarskiRacunKlijenta();
		racun1.setRacun(rac1);
		racun1.setRaspolozivaSredstva(500);
		racun1.setStanje(500);
		racun1.setValuta("RSD");
		t1.setRacunKlijenta(racun1);
		
		t1.setStanjePosleTransakcije(new BigDecimal(500));
		t1.setStanjePreTransakcije(new BigDecimal(600));
		StavkaPreseka stavka1 = new StavkaPreseka();
		stavka1.setDatumValute(Util.getXMLGregorianCalendarNow());
		stavka1.setSifraValute("RSD");
		stavka1.setSmer("U");
		Uplata u1 = new Uplata();
		u1.setIznos(new BigDecimal(100));
		stavka1.setUplata(u1);
		t1.setStavkaPreseka(stavka1);
		ttt.getTransakcija().add(t1);
		//objectToDB("BankaRacuni/001/1/Transakcije", t1.getId().toString(), t1);
		
		Transakcija t2 = new Transakcija();
		t2.setId(new Long(22));
		TRacunKlijenta rac2 = new TRacunKlijenta();
		rac2.setBrojRacuna("001-0000000000001-00");
		rac2.setId(new Long(1));
		rac2.setVlasnik("Pera Peric");
		TBankarskiRacunKlijenta racun2 = new TBankarskiRacunKlijenta();
		racun2.setRacun(rac2);
		racun2.setRaspolozivaSredstva(500);
		racun2.setStanje(500);
		racun2.setValuta("RSD");
		t2.setRacunKlijenta(racun2);
		
		t2.setStanjePosleTransakcije(new BigDecimal(500));
		t2.setStanjePreTransakcije(new BigDecimal(600));
		StavkaPreseka stavka2 = new StavkaPreseka();
		XMLGregorianCalendar d = Util.getXMLGregorianCalendarNow();
		d.setDay(4);
		stavka2.setDatumValute(Util.getXMLGregorianCalendarNow());
		stavka2.setSifraValute("RSD");
		stavka2.setSmer("U");
		Uplata u2 = new Uplata();
		u2.setIznos(new BigDecimal(200));
		stavka2.setUplata(u2);
		t2.setStavkaPreseka(stavka2);
		ttt.getTransakcija().add(t2);
		//objectToDB("BankaRacuni/001/1/Transakcije", t2.getId().toString(), t2);
		/*
		Transakcija t3 = new Transakcija();
		t3.setId(new Long(1));
		TRacunKlijenta rac3 = new TRacunKlijenta();
		rac3.setBrojRacuna("001-0000000000002-00");
		rac3.setId(new Long(2));
		rac3.setVlasnik("Mika Mikic");
		TBankarskiRacunKlijenta racun3 = new TBankarskiRacunKlijenta();
		racun3.setId(new Long(2));
		racun3.setRacun(rac3);
		racun3.setRaspolozivaSredstva(new BigDecimal(500));
		racun3.setStanje(new BigDecimal(500));
		racun3.setValuta("RSD");
		t3.setRacunKlijenta(racun3);
		
		t3.setStanjePosleTransakcije(new BigDecimal(500));
		t3.setStanjePreTransakcije(new BigDecimal(600));
		StavkaPreseka stavka3 = new StavkaPreseka();
		stavka3.setDatumValute(Util.getXMLGregorianCalendarNow());
		stavka3.setSifraValute("RSD");
		stavka3.setSmer("U");
		Uplata u3 = new Uplata();
		u3.setIznos(new BigDecimal(300));
		stavka3.setUplata(u3);
		t3.setStavkaPreseka(stavka3);
		objectToDB("BankaRacuni/001/2/Transakcije", t3.getId().toString(), t3);
		*/

		//XMLGregorianCalendar date = Util.getXMLGregorianCalendarNow();
		
		//GregorianCalendar cal = new GregorianCalendar();
		//cal.setTime(new Date());
		//XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
		
		objectToDB("Banka/001/Racuni/1", "Transakcije", ttt);
		//Test poziv metode iz TransakcijaService klase
		TransakcijaService.getTransakcijeZaPresek(Util.getXMLGregorianCalendarNow(), "001-0000000000001-00", 1, 1, 1);
		
		if (http instanceof BaseXHTTP) http.stop();
		
	}

	public static int createSchema(String schemaName) throws Exception {
		System.out.println("=== PUT: create a new database: " + schemaName + " ===");
		URL url = new URL(REST_URL + schemaName);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);
		String userpass = "admin:admin";
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.connect();
		int responseCode = printResponse(conn);
		conn.disconnect();
		return responseCode;
	}
	
	public static int createResource(String schemaName, String resourceId, InputStream resource) throws Exception {
		System.out.println("=== PUT: create a new resource: " + resourceId + " in database: " + schemaName + " ===");
		URL url = new URL(REST_URL + schemaName + "/" + resourceId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);

		String userpass = "admin:admin";
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.connect();
		/* Preuzimanje output stream-a iz otvorene konekcije */
		OutputStream out = new BufferedOutputStream(conn.getOutputStream());

		/* Slanje podataka kroz stream */
		System.out.println("\n* Send document...");
		IOUtils.copy(resource, out);
		IOUtils.closeQuietly(resource);
		IOUtils.closeQuietly(out);
		
		int responseCode = printResponse(conn);
		conn.disconnect();
		return responseCode;
	}
	
	public static void objectToDB(String schemaName, String resourceId, Object o){
		JAXBContext context;
		
		try {
			context = JAXBContext.newInstance(o.getClass());
		
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			URL url = new URL(REST_URL + schemaName + "/" + resourceId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(RequestMethod.PUT);
			conn.setDoOutput(true);
	
			String userpass = "admin:admin";
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.connect();
			
			OutputStream out = conn.getOutputStream();
			marshaller.marshal(o, out);
			
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(out);
			
			RESTUtil.printResponse(conn);
			conn.disconnect();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ovde se menja ceo stari resurs novim. Elegantnije je putem XUPDATE, pogledati u dokumentaciji
	// u principu se u query element ugradjuje update
	public static int updateResource(String schemaName, String resourceId, InputStream resource) throws Exception {
		return createResource(schemaName, resourceId, resource);
	}
	
	public static InputStream retrieveResource(String query, String schemaName, String method) throws Exception {
		if (method == RequestMethod.GET)
			return retrieveResource(query, schemaName, "UTF-8", true);
		else if (method == RequestMethod.POST)
			return retrieveResourcePost(query, schemaName);
		return null;
	}
	
	public static InputStream retrieveResourcePost(String xquery, String schemaName) throws Exception {
		System.out.println("=== POST: execute a query ===");
		URL url = new URL(REST_URL + schemaName);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.POST);
		
		conn.setRequestProperty("Content-Type", "application/query+xml");

		String userpass = "admin:admin";
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.connect();
		
		OutputStream out = conn.getOutputStream();
		out.write(xquery.getBytes("UTF-8"));
		out.close();

		/* Response kod vracen od strane servera */
		int responseCode = printResponse(conn);

		/* Ako je sve proslo kako treba... */
		if (responseCode == HttpURLConnection.HTTP_OK) 
			return conn.getInputStream();
		
		conn.disconnect();
		return null;
	}
	
	public static InputStream retrieveResource(String query, String schemaName, String encoding, boolean wrap) throws Exception {
		System.out.println("=== GET: execute a query: " + query + " ===");

		StringBuilder builder = new StringBuilder(REST_URL);
		builder.append(schemaName);
		builder.append("?query=");
		builder.append(query.replace(" ", "+"));
		builder.append("&encoding=");
		builder.append(encoding);
		if (wrap) builder.append("&wrap=yes");

		URL url = new URL(builder.substring(0));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		

		String userpass = "admin:admin";
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.connect();
		
		/* Response kod vracen od strane servera */
		int responseCode = printResponse(conn);

		/* Ako je sve proslo kako treba... */
		if (responseCode == HttpURLConnection.HTTP_OK) 
			return conn.getInputStream();
		
		conn.disconnect();
		return null;
	}

	public static int deleteResource(String schemaName, String resourceId) throws Exception {
		if (resourceId != null && !resourceId.equals(""))
			System.out.println("=== DELETE: delete resource: " + resourceId + " from database: " + schemaName + " ===");
		else 
			System.out.println("=== DELETE: delete existing database: " + schemaName + " ===");		
		
		URL url = new URL(REST_URL + schemaName + "/" + resourceId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		String userpass = "admin:admin";
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		
		conn.setRequestMethod(RequestMethod.DELETE);
		conn.connect();
		int responseCode = printResponse(conn);
		conn.disconnect();
		return responseCode;
	}
	
	public static int dropSchema(String schemaName) throws Exception {
		return deleteResource(schemaName, "");
	}

	public static int printResponse(HttpURLConnection conn) throws Exception {
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		return responseCode;
	}
	
	public static void printStream(InputStream input) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		for (String line; (line = br.readLine()) != null;) {
			System.out.println(line);
		}
	}
	
	public static boolean isRunning() throws Exception {
		try {
			((HttpURLConnection) new URL(REST_URL).openConnection()).getResponseCode(); 
			// getResponseCode Vraca status code HTTP zahteva: 200 OK ili 401 Unauthorized. Ukoliko se desila greska prilikom konektovanja na server bacice Exception
		} catch (ConnectException e) {
			return false;
		}
		return true;
	}
	
	public static Object doUnmarshall(String query, String schema, Object o){
		try{
			InputStream in = RESTUtil.retrieveResource(query, schema, RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(o.getClass(), o.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			// set optional properties
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			String xml = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = br.readLine()) != null;) {
				xml=xml+line+"\n";
			}
			StringReader reader = new StringReader(xml);
			Object rac = (Object) unmarshaller.unmarshal(reader);
			return rac;
		}catch(Exception e){
			return null;
		}
	}
	
	public static Object doUnmarshallTransactions(String query, String schema, Object o){
		try{
			InputStream in = RESTUtil.retrieveResource(query, schema, RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(o.getClass(), o.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			// set optional properties
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	
			String xml = "<ns5:Transakcije xmlns:ns5=\"http://www.project.com/transakcije\" xmlns:ns2=\"http://www.project.com/common_types\" xmlns:ns3=\"http://www.project.com/nalog_za_placanje\" xmlns:ns4=\"http://www.project.com/stavka_preseka\">\n";
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = br.readLine()) != null;) {
				xml=xml+line+"\n";
			}
			xml=xml+"</ns5:Transakcije>";
			StringReader reader = new StringReader(xml);
			Object rac = (Object) unmarshaller.unmarshal(reader);
			return rac;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean doMarshall(String schemaName, Object o){
		try{
			JAXBContext context = JAXBContext.newInstance(o.getClass());
		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    
			URL url = new URL(REST_URL + schemaName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(RequestMethod.PUT);
			conn.setDoOutput(true);
			String userpass = "admin:admin";
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			m.marshal(o, out);
			
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(out);
			RESTUtil.printResponse(conn);
			conn.disconnect();
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
    public Long getMaxID(Long idBanke, String schemaName, String itemName) throws IOException {
    	String xQuery = "xs:integer(max(//*:" + itemName + "/@id))";
    	InputStream input = null;
		try {
			input = RESTUtil.retrieveResource(xQuery, schemaName, "UTF-8", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedReader br = new BufferedReader(new InputStreamReader(input));
    	Long result = null;
		String line = br.readLine();

		if (line != null) 
			result = Long.parseLong(line);

    	return result;
    }
	
}
