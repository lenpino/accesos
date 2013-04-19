package cl.altair.accesos.mantenedores.maestros;

import java.util.HashSet;
import java.util.Set;

import cl.altair.acceso.dao.ComunaDAO;
import cl.altair.acceso.dao.EdificioDAO;
import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.TipoDependenciaDAO;
import cl.altair.acceso.modelo.Comuna;
import cl.altair.acceso.modelo.Dependencia;
import cl.altair.acceso.modelo.Direccion;
import cl.altair.acceso.modelo.Directorio;
import cl.altair.acceso.modelo.Edificio;
import cl.altair.acceso.modelo.Empresa;

public class CreaEdificio {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CreaEdificio creador = new CreaEdificio();
		creador.nuevoEdificio();
		System.out.println("EXITO!!");
	}

	public static Edificio nuevoEdificio(){
		EdificioDAO edao = new EdificioDAO();
		Edificio elEdificio = new Edificio();
		elEdificio.setNombre("Edificio");
		elEdificio.setTipo("Oficina");
		elEdificio.setPisos(new Integer(10));
//		elEdificio.setPropietario(obtienePropietario());
		elEdificio.setDireccion(obtieneDireccion());
//		elEdificio.setDependencias(obtieneDependencias());
//		elEdificio.setDirectorio(obtieneDirectorio());
		EntityManagerHelper.beginTransaction();
    	edao.save(elEdificio);
    	EntityManagerHelper.commit();
 		EntityManagerHelper.closeEntityManager();
		return elEdificio;
	}
	
	public Empresa obtienePropietario(){
		Empresa unaEmpresa = new Empresa();
		unaEmpresa.setRazonSocial("Comunidad Edificio Telefonica");
		unaEmpresa.setRut(Integer.valueOf("90430000"));
		unaEmpresa.setDv("4");
		unaEmpresa.setTelefono("(56-2) 26757330");	
		unaEmpresa.setEstado("creado");
		return unaEmpresa;
	}
	
	public static Direccion obtieneDireccion(){
		Direccion laDireccion =  new Direccion();
		laDireccion.setCiudad("Santiago");
		laDireccion.setDireccion1("LA DIRECCION #12345");
		ComunaDAO cdao = new ComunaDAO();
		Comuna laComuna = cdao.findByNombre("Providencia").get(0);
		laDireccion.setComuna(laComuna);
		laDireccion.setRegion(laComuna.getProvincia().getRegion());
		return laDireccion;
	}
	
	public Set<Dependencia> obtieneDependencias(){
		TipoDependenciaDAO tddao = new TipoDependenciaDAO();
//		UsuarioinmuebleDAO uidao = new UsuarioinmuebleDAO();
		Set<Dependencia> lasDependencias = new HashSet<Dependencia>();
		
		
		//Una Dependencia - Oficina 1001
		Dependencia primeraDependencia = new Dependencia();
		primeraDependencia.setIdentificador("Oficina 3006");
		primeraDependencia.setPiso(Integer.parseInt("30"));
		primeraDependencia.setReservable(false);
		primeraDependencia.setTipo(tddao.findByNombre("Oficina").get(0));
//		primeraDependencia.setUsuarioInmueble(uidao.findByNombre("Entel").get(0));
		lasDependencias.add(primeraDependencia);

		//Una Dependencia - Oficina 705
		Dependencia segundaDependencia = new Dependencia();
		segundaDependencia.setIdentificador("Oficina 915");
		segundaDependencia.setPiso(Integer.parseInt("9"));
		segundaDependencia.setReservable(false);
		segundaDependencia.setTipo(tddao.findByNombre("Oficina").get(0));
//		segundaDependencia.setUsuarioInmueble(uidao.findByNombre("TOYOTA").get(0));
		lasDependencias.add(segundaDependencia);
		
		return lasDependencias;
	}
		
	private Directorio obtieneDirectorio(){
//		DirectorioDAO ddao = new DirectorioDAO();
		Directorio elDirectorio = new Directorio("Directorio Telefonica");
		return elDirectorio;
	}

}
