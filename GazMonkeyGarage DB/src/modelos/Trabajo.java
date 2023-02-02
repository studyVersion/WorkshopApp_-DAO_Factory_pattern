package modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public abstract class Trabajo {

    
    @Id 
    protected int identificador;
    protected String descripcion;
    protected int horasEmpleadas;
    protected int fijo;
    protected double precio;
    protected boolean finalizado;

    public Trabajo() {
    }

    public Trabajo(String descripcion) {
        this.fijo = 30;
        this.descripcion = descripcion;
        this.horasEmpleadas = 0;
        this.precio = 0;
        this.finalizado = false;
    }
	public abstract double obtenerCoste();
	
	
	public int getHorasEmpleadas() {
		return horasEmpleadas;
	}


	public int getIdentificador() {
		return identificador;
	}


	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}


	public String getDescripcion() {
		return descripcion;
	} 
	
   
	public double getPrecio() {

		return horasEmpleadas * fijo;
	}
	

	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public boolean isFinalizado() {
		return finalizado;
	}
    
	


	public void finalizarTrabajo() {
		this.finalizado = true;
	}

	
	public void setHorasEmpleadas(int horasEmpleadas) {
		this.horasEmpleadas += horasEmpleadas;
	}


	// obtener el estado
	public String getEstado() {
		
		return finalizado ? "Terminado" : "En Proceso";

	}

	@Override
	public String toString() {
		return    " --------------------------------------\n"
				+ "| Trabajo ID: " + identificador +"\n"
				+ "| Descripcion: " + descripcion +"\n"
				+ "| Precio: " + obtenerCoste() +"\n"
                + "| Estado: "+ getEstado()+ "\n"
				+ " --------------------------------------\n";
	}
}