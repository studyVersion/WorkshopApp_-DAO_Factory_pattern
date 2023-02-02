package modelos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity

public class Revision extends Trabajo implements Serializable {
	
	private int precioExtra;
	
	public Revision(String descripcion) {
		super(descripcion);
		this.precioExtra = 20;
        
	}
    
	public int getPrecioExtra() {
		return precioExtra;
	}

	@Override
	public double obtenerCoste() {		 
		return getPrecio() + precioExtra;
	
	}
	
	
	
}

