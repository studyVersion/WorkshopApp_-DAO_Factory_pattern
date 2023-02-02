package modelos;

import javax.persistence.Entity;

@Entity
public class ReparacionMecanica extends Reparacion {


	public ReparacionMecanica(String descripcion) {
		super(descripcion);
		this.fijoReparacion = 1.1;
	}


	@Override
	public double obtenerCoste() {
		this.precio = this.getPrecio() + this.costeMaterial * this.fijoReparacion;
		
		return precio;
	}
	

}
