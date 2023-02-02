package factory;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import daoAbstract_implementacion.ReparacionDaoImpl;
import daoAbstract_implementacion.RevisionDaoImpl;
import daoImplementacion_mysql.ReparacionDaoImpl_Mysql;
import daoImplementacion_mysql.RevisionDaoImpl_Mysql;
import daoImplementacion_obejctDB.ReparacionDaoImpl_objectDB;
import daoImplementacion_obejctDB.RevisionDaoImpl_objectDB;

public class Factory {
	
	public Factory() {
		
	}
	
	public RevisionDaoImpl revisionFactory() throws FileNotFoundException, IOException {
	
	RevisionDaoImpl revisionDao = null;
	Properties properties= new Properties();
	properties.load(new FileInputStream(new File("dbconfig.properties")));
	String sgbd = properties.get("SGBD").toString();
	
	switch(sgbd) {
	
		case "mysql": revisionDao = new RevisionDaoImpl_Mysql(); break;
	
		case "objectDB": revisionDao = new RevisionDaoImpl_objectDB(); break;
	
		default: System.out.println("tipo de base de datos no encontrado");
	}
	return revisionDao;
    
	}
	
	public ReparacionDaoImpl reparacionFactory() throws FileNotFoundException, IOException {
		
		ReparacionDaoImpl reparacionDao = null;
		Properties properties= new Properties();
		properties.load(new FileInputStream(new File("dbconfig.properties")));
		
		String sgbd = properties.get("SGBD").toString();
		
		switch(sgbd) {
		
			case "mysql": reparacionDao = new ReparacionDaoImpl_Mysql(); break;
		
			case "objectDB": reparacionDao = new ReparacionDaoImpl_objectDB(); break;
		
			default: System.out.println("tipo de base de datos no encontrado");
		}
		
		return reparacionDao;
	    
		}
}
