package cl.altair.utiles.generales;

public class Validador {
	public static boolean isNumber(String string){
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!('0' <= chars[i] && chars[i] <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isRUT(int rut, char dv){
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10)
        {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return dv == (char) (s != 0 ? s + 47 : 75);		
	}
	
	public static boolean isDV(String string){
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!(('0' <= chars[i] && chars[i] <= '9') || chars[i] == 'K' || chars[i] == 'k')) {
				return false;
			}
		}
		return true;
	}

}
