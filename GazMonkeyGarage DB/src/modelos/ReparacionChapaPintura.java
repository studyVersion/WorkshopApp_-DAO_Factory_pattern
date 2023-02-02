package modelos;

import javax.persistence.Entity;

@Entity
public class ReparacionChapaPintura extends Reparacion {


	
	public ReparacionChapaPintura(String descripcion) {
		super(descripcion);
		this.fijoReparacion = 1.3;
	}
    
	
	@Override
	public double obtenerCoste() {
		this.precio = this.getPrecio() + this.costeMaterial * this.fijoReparacion;
		
		return precio;
	}
	
	
}
