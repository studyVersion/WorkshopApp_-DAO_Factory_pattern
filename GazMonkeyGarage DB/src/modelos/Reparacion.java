package modelos;

import javax.persistence.Entity;

@Entity
public abstract class Reparacion extends Trabajo{

	protected double costeMaterial;
	protected double fijoReparacion;

	
	public Reparacion(String descripcion) {
		super(descripcion);
		this.costeMaterial = 0;
		this.fijoReparacion = 0;
	}


	
	public double getCosteMaterial() {
		return costeMaterial;
	}



	public void setCosteMaterial(double nuevoPrecio) {
		this.costeMaterial += nuevoPrecio;
	}

//
//
//	public void aumentarPrecioPiezas(double nuevoPrecio) {
//
//		this.costeMaterial += nuevoPrecio;
//	}

	
}
