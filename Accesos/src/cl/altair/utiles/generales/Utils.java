package cl.altair.utiles.generales;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class.getName());
	static char[] carr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String GUID() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	public static long now() {
		return new Date().getTime();
	}

	public static long expiration(long period) {
		return (new Date().getTime()) + period;
	}

	public static byte[] getStreamContent(InputStream stream)
			throws IOException {
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		byte[] buffer = new byte[16384];
		while (true) {
			int bytesRead = stream.read(buffer);
			if (bytesRead < 0)
				break;
			content.write(buffer, 0, bytesRead);
		}
		return content.toByteArray();
	}

	public static byte[] hashBytes(byte[] data) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(data);
		sha1hash = md.digest();
		return sha1hash;
	}
	
	public static String hashBytesString(byte[] data) throws NoSuchAlgorithmException,
	UnsupportedEncodingException {
		byte[] sha1hash = hashBytes(data);
		return new String(Hex.encodeHex(sha1hash));	
	}

	/**
	 * Realiza el digest de un valor usando SHA-1 y lo devuelve
	 * en formato base64.  
	 * @param data - Data a calcular digest.
	 * @return Digest en base64.
	 * @throws Exception
	 * @author Jjeneral
	 */
	public static String hashBytesBase64(byte[] data) throws Exception {
		byte[] sha1hash = hashBytes(data);
		return base64FromBytes(sha1hash);
	}
	

	public static byte[] bytesFromBase64(String encoded) throws UnsupportedEncodingException {
		return Base64.decodeBase64(encoded.getBytes("ASCII"));
	}
	
	public static int copyStream(InputStream is, OutputStream os)
		throws IOException {
		
		int bytesCopied = 0;
		byte[] buffer = new byte[128 * 1024];
		while(true) {
			int bytesRead = is.read(buffer);
			if (bytesRead == -1) break;
			bytesCopied += bytesRead;
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
		return bytesCopied;
	}

	public static String base64FromBytes(byte[] byteArray) throws Exception {

		return new String(Base64.encodeBase64Chunked(byteArray), "ASCII");
	}

	public static String base64FromBigInteger(BigInteger bigInteger) throws Exception {
		byte[] bytes = bigInteger.toByteArray();
		byte[] result;
		if (bytes[0] == 0) {
			result = new byte[bytes.length-1];
			System.arraycopy(bytes, 1, result, 0, result.length);
		}
		else result = bytes;
		return base64FromBytes(result);
	}

	public static String urlEscape(String url) throws UnsupportedEncodingException {

		String escaped = URLEncoder.encode(url, "UTF-8");
		log.finest("clean: " + url + ", escaped: " + escaped);
		return escaped;
	}

	public static String urlDecode(String encoded) throws UnsupportedEncodingException {

		String decoded = URLEncoder.encode(encoded, "UTF-8");
		log.finest("escaped: " + encoded + ", clean: " + decoded);
		return decoded;
	}

	public static String uriSafeEncode(String s) {
		String result = "";
		for(int n=0; n < s.length(); n++) {
			char aChar = s.charAt(n);

			if ((aChar >= 'A' && aChar <= 'Z') ||
				(aChar >= 'a' && aChar <= 'z') ||
				(aChar >= '0' && aChar <= '0')) {
				
				result += s.charAt(n);
			} else {
				if (aChar >= '\u0000' && aChar <= '\u00ff') {
					result += "$" + hexByte(aChar);
				} else {
					result += "$u" + hexWord(aChar);
				}
			}
		}
		log.finest("before: " + s + ", after: " + result);
		return result;
	}
	
	private static String charToHex(char c, int minWidth) {
		if (minWidth < 1) minWidth = 1;
		int value = c;
		String result = "";
		while (value > 0) {
			result = Character.forDigit(value % 16, 16) + result;
			value = value / 16;
		}
		int paddingWidth = (minWidth - result.length());
		if (paddingWidth > 0) {
			while (paddingWidth > 0) {
				result += "0" + result;
				paddingWidth -= 1;
			} 
		}
		return result;
	}
	
	private static String hexWord(char c) {
		
		return charToHex(c, 4);
	}

	private static String hexByte(char c) {
		
		return charToHex(c, 2);
	}

	public static String urlSafeDecode(String encoded) {
		StringBuilder sb = new StringBuilder();
		StringReader sr = new StringReader(encoded);
		while (true) {
			try {
				int i = sr.read();
				if (i == -1) break;
				char c = (char)i;
				if (c == '$') {
					char hex1 = (char) sr.read();
					char hex2 = (char) sr.read();
					char decoded;
					if (hex1 != 'u') {
						decoded = (char)(decodeHexDigit(hex1) * 16 + decodeHexDigit(hex2)); 
					} else {
						char hex3 = (char) sr.read();
						char hex4 = (char) sr.read();
						decoded = (char)(((decodeHexDigit(hex1) * 16 + decodeHexDigit(hex2)) * 16 + decodeHexDigit(hex3)) * 16 + decodeHexDigit(hex4)); 
					}
					sb.append(decoded);
				}
				else
					sb.append(c);
			} catch (Exception e) {
				break;
			}
		}
		String result = sb.toString();
		log.finest("before: " + encoded + ", after: " + result);
		return result;
	}

	private static int decodeHexDigit(char c) {

		return Character.digit(c, 16);
	}

	public static String makeSafeFileName(String docTypeName) {
		
		Pattern pattern = Pattern.compile("[^A-Za-z0-9+_.-]+");
		String[] parts = pattern.split(docTypeName);
		List<String> nonEmptyParts = new ArrayList<String>();
		for (String anItem : parts) {
			if (!anItem.equals(""))
				nonEmptyParts.add(anItem);
		}
		String result = "";
		for (String anItem : nonEmptyParts) {
			if (result.length() > 0)
				result += "_" + anItem;
			else
				result = anItem;
		}
		return result;
	}
	
	/**
	 * Valida si una fecha es válida con al menos uno de los formatos de fecha recibidos.
	 * @param datetimeString - cadena con fecha a verificar.
	 * @param formatPatterns - Lista de patrones de fecha soportados por <code>java.text.SimpleDateFormat</code>
	 * @return Verdadero si la fecha concuerda con alguno de los patrones, Falso de lo contrario.
	 * @author Jjeneral
	 * @see java.text.SimpleDateFormat
	 */
	public static boolean isValidDateTime(String datetimeString, ArrayList<String> formatPatterns) {
		boolean result = false;
		for (String pattern : formatPatterns) {
			try {
				parseDate(datetimeString, pattern);
				return true;
			} catch (ParseException e) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Evalúa una cadena conteniendo una fecha y la transforma a un objeto java.
	 * @param datetimeString Cadena con fecha a evaluar.
	 * @param formatPattern Patrón de fecha/hora soportado por <code>java.text.SimpleDateFormat</code>
	 * @return Objeto Date resultante.
	 * @throws ParseException
	 * @author Jjeneral
	 * @see java.text.SimpleDateFormat
	 */
	public static Date parseDate(String datetimeString, String formatPattern) throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat();		
		dateFormatter.setLenient(false);
		synchronized (datetimeString) { 
			dateFormatter.applyPattern(formatPattern);
			return dateFormatter.parse(datetimeString);
		}
	}
	
	public static String getFormattedDate(String pattern) {
		return getFormattedDate(pattern, new Date());
	}
	
	public static String getFormattedDate(String pattern, Date date) {
		String formattedDate = new SimpleDateFormat(pattern).format(date);
		return formattedDate;
	}	
	
	public static String string2Link(String linkValue) {
		return "<a  target=\"_blank\" href=\"" + linkValue + "\">" + linkValue + "</a>";
	}
	
	public static String getJsUnicode(String string) {
		string = string.replace("Á", "\\" + "u00C1");
		string = string.replace("á", "\\" + "u00E1");
		string = string.replace("É", "\\" + "u00C9");
		string = string.replace("é", "\\" + "u00E9");
		string = string.replace("Í", "\\" + "u00CD");
		string = string.replace("í", "\\" + "u00ED");
		string = string.replace("Ó", "\\" + "u00D3");
		string = string.replace("ó", "\\" + "u00F3");
		string = string.replace("Ú", "\\" + "u00DA");
		string = string.replace("ú", "\\" + "u00FA");
		
		return string;
		
	}

	public static String formatMto(Object monto, String formato) {
		try {
			if(monto == null)
				return "";
			if(monto.toString().equalsIgnoreCase(""))
				return (String)monto;
			java.util.Locale pais = java.util.Locale.GERMANY;
			String value = monto.toString();
			java.text.DecimalFormat dec = (java.text.DecimalFormat) java.text.NumberFormat.getInstance(pais);
			java.lang.Double valor = null;
			dec.applyPattern(formato);
			valor = java.lang.Double.valueOf(value);
			return dec.format(valor.doubleValue());
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public static String formatRut(Object rut) {
		String value = rut.toString();
		value = value.trim();
		java.util.Locale pais = java.util.Locale.GERMANY;
		if (!value.equalsIgnoreCase("")) {
			char DV = value.charAt(value.length() - 1);
			value = value.substring(0, value.length() - 1);
			java.text.DecimalFormat dec = (java.text.DecimalFormat) java.text.NumberFormat.getInstance(pais);
			java.lang.Double valor = null;
			dec.applyPattern("#,##0");
			valor = java.lang.Double.valueOf(value);
			return dec.format(valor.doubleValue()) + "-" + DV;
		}
		else
			return "";
	}

}
