package cl.altair.utiles.generales;

import java.util.HashSet;
import java.util.Set;

import cl.altair.acceso.modelo.Dependencia;

public class Piso {
	private String piso;
	private Set<Dependencia> dependencias;
	public String getPiso() {
		return piso;
	}
	public Piso(String piso, Dependencia primeraDependencia){
		this.piso = piso;
		dependencias = new HashSet<Dependencia>();
		dependencias.add(primeraDependencia);
	}
	public Piso(String piso){
		this.piso = piso;
		dependencias = new HashSet<Dependencia>();
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public Set<Dependencia> getDependencias() {
		return dependencias;
	}
	public void getDependencias(Set<Dependencia> dependencias) {
		this.dependencias = dependencias;
	}
}
