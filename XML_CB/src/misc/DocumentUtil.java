package misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class DocumentUtil {

	public static void printDocument(Document document) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(new DOMSource(document), result);
	}

	public static void printSource(Source source) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
	}
	
	public static Document convertToDocument(Source request) {
		Document r = null;
		try{
			DocumentBuilder db = getDocumentBuilder();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(baos);
			transformer.transform(request, result);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			r = db.parse(bais);
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	private static DocumentBuilder getDocumentBuilder() {
		try {
			// Setup document builder
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			return builder;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
