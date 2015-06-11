package com.project.xmldb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.io.IOUtils;
import org.basex.BaseXHTTP;
import org.basex.rest.Result;
import org.basex.rest.Results;
import org.w3c.dom.Node;

import com.project.common_types.TBanka;
import com.project.mt900.Mt900RTGS;
import com.project.mt900.Mt900RTGS.PodaciOZaduzenju;
import com.project.mt900.Mt900RTGSService;
import com.project.util.Util;

public class EntityManagerBaseX<T, ID extends Serializable> {

	/*
	 * Izbaciti u XML/properties konfiguraciju
	 */
	public static final String REST_URL = "http://localhost:8081/BaseX821/rest/";//Promeniti

	public static final String BASEX_CONTEXT_PATH = "org.basex.rest";
	
	private String schemaName;
	
	private String contextPath;
	
	private Marshaller marshaller;
	
	private Unmarshaller unmarshaller, basex_unmarshaller;
	
	private JAXBContext context, basex_context;
	
	private URL url;
	
	private HttpURLConnection conn;
	
	public EntityManagerBaseX(String schemaName, String contextPath) throws JAXBException {
		setSchemaName(schemaName);
		setContextPath(contextPath);
		
		context = JAXBContext.newInstance(contextPath);
		marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		unmarshaller = context.createUnmarshaller();
		
		basex_context = JAXBContext.newInstance(BASEX_CONTEXT_PATH);
		basex_unmarshaller = basex_context.createUnmarshaller();
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
	
	public static void main(String[] args) {
		try{
			BaseXHTTP http = null;
			
			/* Startovanje baze podataka */
			if (!isRunning()) 
				http = new BaseXHTTP("-Uadmin", "-Padmin");
			
			File file = new File("src/resource/");
			
			URL url = new URL(REST_URL + "mt900rtgs");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(RequestMethod.PUT);
			conn.disconnect();
			
			Mt900RTGS test = new Mt900RTGS();
	    	PodaciOZaduzenju pod = new PodaciOZaduzenju();
	    	TBanka t = new TBanka();
	    	t.setBrojRacunaBanke("123456789112345678");
	    	t.setNazivBanke("ABC");
	    	t.setSWIFTKod("33334568");
	    	pod.setBankaDuznika(t);
	    	try {
				pod.setDatumValute(Util.getXMLGregorianCalendarNow());
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
				e.getCause().printStackTrace();
			}
	    	pod.setIDPorukeNaloga("");
	    	double c=5000.0;
	        BigDecimal b = new BigDecimal(c);
	    	pod.setIznos(b);
	    	pod.setSifraValute("RSD");
	    	test.setIDPoruke("CB999");
	    	test.setPodaciOZaduzenju(pod);
	    	t.setId(Long.parseLong("1234"));
	    	test.setId(Long.parseLong("33"));
	    	Mt900RTGSService r = new Mt900RTGSService();
	    	r.create(test);
	    	
			
			if (http instanceof BaseXHTTP) 
				http.stop();
			System.out.println("Done");
		} catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	public static void printStream(InputStream input) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		for (String line; (line = br.readLine()) != null;) {
			System.out.println(line);
		}
	}
	
	public static int createResource(String schemaName, String resourceId, InputStream resource) throws Exception {
		System.out.println("=== PUT: create a new resource: " + resourceId + " in database: " + schemaName + " ===");
		URL url = new URL(REST_URL + schemaName + "/" + resourceId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);
		
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

		/* Response kod vracen od strane servera */
		int responseCode = printResponse(conn);

		/* Ako je sve proslo kako treba... */
		if (responseCode == HttpURLConnection.HTTP_OK) 
			return conn.getInputStream();
		
		conn.disconnect();
		return null;
	}
	
	public static int printResponse(HttpURLConnection conn) throws Exception {
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		return responseCode;
	}
	
	@SuppressWarnings("unchecked")
	public T find(ID resourceId) throws IOException, JAXBException {
		T entity = null;
		
		url = new URL(REST_URL + schemaName + "/" + resourceId);
		conn = (HttpURLConnection) url.openConnection();
		
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();

		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		if (responseCode == HttpURLConnection.HTTP_OK) 
			return (T) unmarshaller.unmarshal(conn.getInputStream());
		
		conn.disconnect();
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws IOException, JAXBException {
		Results wrappedResults = null;
		List<T> results = new ArrayList<T>();
		
		StringBuilder builder = new StringBuilder(REST_URL);
		builder.append(schemaName);
		builder.append("?query=collection('invoice')");
		builder.append("&wrap=yes");

		url = new URL(builder.substring(0));
		conn = (HttpURLConnection) url.openConnection();

		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();

		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			wrappedResults = (Results) basex_unmarshaller.unmarshal(conn.getInputStream());
			for (Result result : wrappedResults.getResult())
				results.add((T) unmarshaller.unmarshal((Node)result.getAny()));
		}
		
		conn.disconnect();
		return results;
	}
	
	/*
	 * Takes both, XQuery and XUpdate statements.
	 */
	public InputStream executeQuery(String xQuery, boolean wrap) throws IOException {
		InputStream result = null;
		String wrapString = wrap ? "yes" : "no";
		String wrappedQuery = "<query xmlns='http://basex.org/rest'>" + 
				"<text><![CDATA[%s]]></text>" + 
				"<parameter name='wrap' value='" + wrapString + "'/>" +
			"</query>";
		wrappedQuery = String.format(wrappedQuery, xQuery);

		url = new URL(REST_URL + schemaName);
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.POST);
		conn.setRequestProperty("Content-Type", "application/query+xml");
		
		/*
		 * Generate HTTP POST body.
		 */
		System.out.println(wrappedQuery);
		OutputStream out = conn.getOutputStream();
		out.write(wrappedQuery.getBytes("UTF-8"));
		out.close();

		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();

		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		if (responseCode == HttpURLConnection.HTTP_OK)
			result = conn.getInputStream();
		
		return result;
	}
	
	public void persist(T entity, Long id) throws JAXBException, IOException {

		String resourceId = String.valueOf(id);
		
		url = new URL(REST_URL + schemaName + "/" + resourceId);
		System.out.println(url);
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);

		marshaller.marshal(entity, conn.getOutputStream());
		
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		
		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		conn.disconnect();
	}
	
	public void delete(ID resourceId) throws IOException {
		url = new URL(REST_URL + schemaName + "/" + resourceId);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(RequestMethod.DELETE);
		
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		
		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		conn.disconnect();
	}
	
	public void update(T entity, ID resourceId) throws IOException, JAXBException {
		url = new URL(REST_URL + schemaName + "/" + resourceId);
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);

		marshaller.marshal(entity, conn.getOutputStream());
		
		int responseCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		
		System.out.println("\n* HTTP response: " + responseCode + " (" + message + ')');
		
		conn.disconnect();
	}

	/**
	 * Implements some sort of identity strategy, since it isn't natively supported by XMLDB.
	 * @return the next id value.
	 * @throws IOException
	 */
	public Long getIdentity() throws IOException {

		String xQuery = "max(//@id)";
		InputStream input = executeQuery(xQuery, false);
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		
		String line = br.readLine();
		if (line != null)
			return Long.valueOf(line) + 1L;
		return 1L;
	}
	
	/*
	 * Get/set methods
	 */
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public String getSchemaName() {
		return schemaName;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public JAXBContext getContext() {
		return context;
	}

	public void setContext(JAXBContext context) {
		this.context = context;
	}
	
}
