package cl.miempresa.accesos.modelo.providers;

import cl.altair.acceso.modelo.Directorio;

public class TreeDirectorio {
	private Directorio[] directorio;
	private String nombre;
	public Directorio[] getDirectorio() {
		return directorio;
	}
	public void setDirectorio(Directorio[] directorio) {
		this.directorio = directorio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
