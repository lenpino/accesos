package cl.altair.utiles.generales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class InfoRegistro {
	private static String clave;
	private static String serie;
	private String HOST_NAME;
	private final static Logger LOGGER = Logger.getLogger(InfoRegistro.class.getName());
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	InfoRegistro.getInfoRegistro("lenpino@gmail.com");
    }
    
    public static Document creaRespXML(String xml) {
    	LOGGER.info("Creando objeto DOM con la respuesta: " + xml);
	    try {
	       DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
	       DocumentBuilder bldr;
	       bldr = fctr.newDocumentBuilder();
	       InputSource insrc = new InputSource(new StringReader(xml));
	       return bldr.parse(insrc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
    }

    public static void getInfoRegistro(String email) throws IOException{
        try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://localhost:8090/sia/rest-services/usuario/cliente/" + email);
			HttpResponse response = client.execute(request);
			//Lee la respuesta
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			//Crea el String con la respuesta
			StringBuilder theText = new StringBuilder();
			while((line=rd.readLine())!=null){
			   theText.append(line+"\n");
			}
			//Crea el documento
			Document resp = creaRespXML(theText.toString());
			XPath xpath = XPathFactory.newInstance().newXPath();
			//Lee el nodo de errores
			XPathExpression error = xpath.compile("//error");
			Object isError = error.evaluate(resp, XPathConstants.NODE);			
			//Si no hay error extrae la clave y el numero de serie
			if(isError == null){				
				XPathExpression pwd = xpath.compile("//ref/text()");
				clave = (String)pwd.evaluate(resp, XPathConstants.STRING);
				
				XPathExpression serial = xpath.compile("//refid/text()"); 
				serie = (String)serial.evaluate(resp, XPathConstants.STRING);
	
				LOGGER.info("CLAVE = " + clave);
				LOGGER.info("SERIE = " + serie);
			} else {
				XPathExpression mensaje = xpath.compile("//mensaje/text()");
				String elMensaje = (String)mensaje.evaluate(resp, XPathConstants.STRING);
				System.out.println("MENSAJE = " + elMensaje);
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} 
    }
    
	public static String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		InfoRegistro.clave = clave;
	}

	public static String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		InfoRegistro.serie = serie;
	}
}