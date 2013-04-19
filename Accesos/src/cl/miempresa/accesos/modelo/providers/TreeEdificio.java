package cl.miempresa.accesos.modelo.providers;

import java.util.HashMap;
import java.util.Set;

import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.utiles.generales.Piso;

public class TreeEdificio {
	private Edificio[] edificio;
	private Piso[] pisos;
	private String nombre;

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Piso[] getPisos() {
		return pisos;
	}
	public void setPisos(Piso[] pisos) {
		this.pisos = pisos;
	}
	public static Object[] getTreeEdificio(Set<Dependencia> lasDependencias){
		HashMap<Integer,Piso> losPisos = new HashMap<Integer,Piso>();
		for(Dependencia unaDependencia: lasDependencias){
			if(!losPisos.containsKey(unaDependencia.getPiso())){
				Piso unPiso = new Piso(unaDependencia.getPiso().toString(), unaDependencia);
				losPisos.put(unaDependencia.getPiso(), unPiso);
			} else {
				losPisos.get(unaDependencia.getPiso()).getDependencias().add(unaDependencia);
			}
		}
		return losPisos.values().toArray();
	}
	
	public void setPisos(Set<Dependencia> lasDependencias){
		HashMap<Integer,Piso> losPisos = new HashMap<Integer,Piso>();
		for(Dependencia unaDependencia: lasDependencias){
			if(!losPisos.containsKey(unaDependencia.getPiso())){
				Piso unPiso = new Piso(unaDependencia.getPiso().toString(), unaDependencia);
				losPisos.put(unaDependencia.getPiso(), unPiso);
			} else {
				losPisos.get(unaDependencia.getPiso()).getDependencias().add(unaDependencia);
			}
		}
		this.pisos = (Piso[])losPisos.values().toArray();
	}
	
	public static Object[] getTreeEdificio(Edificio elEdificio){
		HashMap<Integer,Piso> losPisos = new HashMap<Integer,Piso>();
		for(int i=0;i<elEdificio.getPisos().intValue();i++){
			losPisos.put(new Integer(i+1), new Piso(i+1 +""));
		}
		Set<Dependencia> lasDependencias = elEdificio.getDependencias();
		for(Dependencia unaDependencia: lasDependencias){
			losPisos.get(unaDependencia.getPiso()).getDependencias().add(unaDependencia);
		}
		return losPisos.values().toArray();
	}

	public Edificio[] getEdificio() {
		return edificio;
	}
	public void setEdificio(Edificio[] edificio) {
		this.edificio = edificio;
	}
}
