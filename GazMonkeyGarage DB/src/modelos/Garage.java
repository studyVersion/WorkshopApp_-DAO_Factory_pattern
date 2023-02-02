package modelos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import factory.Factory;
import modelos.*;

public class Garage {

	private Factory factory;

	public Garage() throws FileNotFoundException, IOException {

		factory = new Factory();

	}

	/*
	 * los tipos : 1 parra revision ; 2 parra reparacion mecanica; 3 parra
	 * reparacion
	 */
	public int registrarTrabajo(int tipo, String descripcion) throws FileNotFoundException, IOException {
		int codigo = -1;

		if (tipo == 1) {

			Revision rev = new Revision(descripcion);
			
			rev.setIdentificador(factory.revisionFactory().getUltimoID());
			factory.revisionFactory().insertRevision(rev);
			
			codigo = rev.getIdentificador();

		} else if (tipo == 2) {

			ReparacionMecanica repMecanica = new ReparacionMecanica(descripcion);
			
			repMecanica.setIdentificador(factory.reparacionFactory().getUltimoID());
			factory.reparacionFactory().insertReparacion(repMecanica);
			
			codigo = repMecanica.getIdentificador();

		} else if (tipo == 3) {

			ReparacionChapaPintura chapaPintura = new ReparacionChapaPintura(descripcion);
			
			chapaPintura.setIdentificador(factory.reparacionFactory().getUltimoID());
			factory.reparacionFactory().insertReparacion(chapaPintura);
			
			codigo = chapaPintura.getIdentificador();
		}
		return codigo;

	}// registrarTrabajo

	/*
	 * Si el código = 0 las horas se incrementan, si 1 las horas son negativas, si 2
	 * el trabajo está terminado, si 3 la ID es incorrecta
	 */

	public int aumentarHoras(int id, int horas) throws FileNotFoundException, IOException {
		int codigo = 0;
		Revision rev = null;
		Reparacion rep = null;

		if (horas > 0) {
			rev = factory.revisionFactory().getRevision(id);
			rep = factory.reparacionFactory().getReparacion(id);
			
			if (rev != null) {
				if (!rev.isFinalizado()) {
					
					rev.setHorasEmpleadas(horas);
					factory.revisionFactory().updateRevision(rev);

				} else {
					codigo = 2;
				}

			} else if (rep != null) {
				if (!rep.isFinalizado()) {
					
					rep.setHorasEmpleadas(horas);
					factory.reparacionFactory().updateReparacion(rep);
				} else {
					codigo = 2;
				}
			} else {
				codigo = 3;
			}
		}
		
		return codigo;
		
	}// aumentarHoras

	
	
	/*
	 * si el código = 0 el precio de piezas se incrementa, si 1 el nuevo precio es
	 * negativo, si 2 el trabajo está terminado, si 3 la ID es incorrecta,
	 */
	public int aumentarCostePiezas(int id, double nuevoPrecio) throws FileNotFoundException, IOException {
		int codigo = 0;
		Reparacion rep = null;

		if (nuevoPrecio > 0) {
			rep = factory.reparacionFactory().getReparacion(id);
			if (rep != null) {
				if (!rep.isFinalizado()) {
					rep.setCosteMaterial(nuevoPrecio);
					factory.reparacionFactory().updateReparacion(rep);
				} else {
					codigo = 2;
				}
			} else {
				codigo = 3;
			}

		} else {
			codigo = 1;
		}
		return codigo;

	}// aumentarCostePiezas

	/*
	 * Si el codigo = -1 el trabajo no existe, si -2 el trabajo ya esta terminado,
	 * 
	 */

	public int finalizarTrabajo(int id) throws FileNotFoundException, IOException {
		int codigo = 0;
		Revision revision = factory.revisionFactory().getRevision(id);
		Reparacion reparacion = factory.reparacionFactory().getReparacion(id);

		if (revision != null) {
			if (!revision.isFinalizado()) {
				revision.finalizarTrabajo();
				factory.revisionFactory().updateRevision(revision);
			} else {
				codigo = -2;
			}
		} else if (reparacion != null) {
			if (!reparacion.isFinalizado()) {
				reparacion.finalizarTrabajo();
				factory.reparacionFactory().updateReparacion(reparacion);
			} else {
				codigo = -2;
			}
		} else {
			codigo = -1;
		}

		return codigo;

	}// finalizarTrabajo

	public String muestraTrabajo(int id) throws FileNotFoundException, IOException {
		String value = "";
		Revision revision = factory.revisionFactory().muestrarRevision(id);
		Reparacion reparacion = factory.reparacionFactory().muestrarReparacion(id);
		
		if (revision != null) {
			value = revision.toString();
		} else if (reparacion != null) {
			value = reparacion.toString();
		}

		return value;

	}// muestraTrabajo

}
