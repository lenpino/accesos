package cl.altair.utiles.generales;

import java.math.BigInteger;
import java.sql.Date;
import java.util.logging.Logger;

import cl.altair.acceso.dao.EntityManagerHelper;
import cl.altair.acceso.dao.ProgramaDAO;
import cl.altair.acceso.dao.UsuarioDAO;
import cl.altair.acceso.modelo.Programa;
import cl.altair.acceso.modelo.Rol;
import cl.altair.acceso.modelo.Usuario;


public class RegistroUsr {

	private final static Logger LOGGER = Logger.getLogger(RegistroUsr.class.getName());
	public static void registraUsr(String login){
		try {

			UsuarioDAO udao= new UsuarioDAO();			
			Usuario usuario = new Usuario();
			
			//Puebla el objeto del usuario con los datos requeridos
			LOGGER.info("Puebla el objeto del usuario con los datos requeridos");
			//Se inicializa como activo el usuario
			usuario.setEstado("activo");
			//El login es el correo electronico
			usuario.setLogin(login);
			//La clave es la que quedo registrada en la base de registros
			usuario.setClave(InfoRegistro.getClave());
			//Fecha de ingreso en la activacion de la aplicacion
			usuario.setFechaingreso(new java.sql.Date(System.currentTimeMillis()));
			
			//Crea la relacion entre el usuario y el rol administrador
			Rol elRol = new Rol();
			elRol.setNombre("Administrador");
			usuario.getRoles().add(elRol);
			LOGGER.info("Crea la relacion entre el usuario y el rol administrador");

			EntityManagerHelper.beginTransaction();
			udao.save(usuario);
			EntityManagerHelper.commit();
			EntityManagerHelper.closeEntityManager();
			
			LOGGER.info("Registro de usuario basico creado");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void registraPrograma(String serie, String version){
		ProgramaDAO pdao = new ProgramaDAO();
		java.util.Date utilDate = new java.util.Date();
		long lnMilisegundos = utilDate.getTime();
		java.sql.Timestamp fechaActual = new java.sql.Timestamp(lnMilisegundos);
		// Puebla entidad con los datos necesarios
		Programa elPrograma = new Programa();
		elPrograma.setSerial(new BigInteger(serie));
		elPrograma.setVersion(version);
		elPrograma.setActivacion(new Date(fechaActual.getTime()));				
		elPrograma.setEstado("activado");
		//Graba informacion del programa en la base de datos
		EntityManagerHelper.beginTransaction();
		pdao.save(elPrograma);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		
		LOGGER.info("Registro del programa grabado en base de datos");
		
	}
}
