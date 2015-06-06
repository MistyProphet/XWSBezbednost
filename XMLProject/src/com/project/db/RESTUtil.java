package com.project.db;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.basex.BaseXHTTP;

import com.project.rest.misc.RequestMethod;

/**
 * Klasa demonstrira upotrebu REST API-a BaseX XML baze podataka.
 * Sadrzi set reusable CRUD operacija, sa primerom njihove upotrebe. 
 * 
 * @author Igor Cverdelj-Fogarasi
 *
 */
public class RESTUtil {

	public static final String REST_URL = ResourceBundle.getBundle("basex").getString("rest.url");
	
	public static void main(String[] args) throws Exception {

		BaseXHTTP http = null;
		
		/* Startovanje baze podataka */
		if (!isRunning()) http = new BaseXHTTP("-Uadmin", "-Padmin");
		
		File file = new File("src/main/resources/");
		
		/* Test CRUD operacija */
		createSchema("factbook");
		createResource("factbook", "factbook.xml", new FileInputStream(new File(file, "factbook.xml")));
		
		printStream(retrieveResource("(//city/name)[position()= 10 to 15]", "factbook", RequestMethod.GET));
		
		createSchema("firma");
		createResource("firma", "uplatnica.xml", new FileInputStream(new File(file, "uplatnica_prazna.xml")));
		
		printStream(retrieveResource("//ulica/text()", "firma", RequestMethod.GET));
		
		updateResource("firma", "uplatnica.xml", new FileInputStream(new File(file, "uplatnica.xml")));
		
		printStream(retrieveResource("//ulica/text()", "firma", RequestMethod.GET));

		/* XSD sema za REST query je data u src/main/resources folderu (mora se postovati ova gramatika za slanje zahteva. Nigde se ovaj fajl ne koristi, 
		 * tu je za prikaz izgleda gramatike zahteva)*/
		// CDATA zato sto hocemo da se obezbedimo da nas FLWOR izraz ostane u izvornom obliku i tako dodje do servera. Inace bi se mogli parsirati entiteti
		// npr. u xQUERY se ne koristi < vec ^lt
		String xml = "<query xmlns='http://basex.org/rest'>"
				+ 		"<text>"
				+ 			"<![CDATA[%s]]>"
				+ 		"</text>"
				+ 		"<parameter name='wrap' value='yes'/>"
				+ 		"</query>";
		String xquery = "for $i in //ulica/text() return string-length($i)";
		// na mesto %s u CDATA se ugradjuje query string
		printStream(retrieveResource(String.format(xml, xquery), "firma", RequestMethod.POST));
		
		dropSchema("factbook");
		
		deleteResource("firma", "uplatnica.xml");
		dropSchema("firma");

		/* Zaustavljanje baze */
		if (http instanceof BaseXHTTP) http.stop();
		
	}

	public static int createSchema(String schemaName) throws Exception {
		System.out.println("=== PUT: create a new database: " + schemaName + " ===");
		URL url = new URL(REST_URL + schemaName);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(RequestMethod.PUT);
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

	public static int deleteResource(String schemaName, String resourceId) throws Exception {
		if (resourceId != null && !resourceId.equals(""))
			System.out.println("=== DELETE: delete resource: " + resourceId + " from database: " + schemaName + " ===");
		else 
			System.out.println("=== DELETE: delete existing database: " + schemaName + " ===");
		
		URL url = new URL(REST_URL + schemaName + "/" + resourceId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(RequestMethod.DELETE);
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
	
}
