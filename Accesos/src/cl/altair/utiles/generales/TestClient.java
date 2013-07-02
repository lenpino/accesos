package cl.altair.utiles.generales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		TestClient yo = new TestClient();
		try {
			TestClient.getInfoRegistro("lenpin@gmail.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				String clave = (String)pwd.evaluate(resp, XPathConstants.STRING);
				
				XPathExpression serial = xpath.compile("//serial/text()"); 
				String serie = (String)serial.evaluate(resp, XPathConstants.STRING);
				
				XPathExpression version = xpath.compile("//version/text()"); 
				String ver = (String)version.evaluate(resp, XPathConstants.STRING);
	
				System.out.println("CLAVE = " + clave);
				System.out.println("SERIE = " + serie);
				System.out.println("VERSION = " + ver);
				
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

    public static Document creaRespXML(String xml) {
    	System.out.println("Creando objeto DOM con la respuesta: " + xml);
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

}
