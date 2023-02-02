package dao;

import modelos.*;

public interface ReparacionDAO {
	
	public void insertReparacion(Reparacion reparacion) ;
	public int getUltimoID();
	public Reparacion getReparacion(int id);
	public void updateReparacion(Reparacion reparacion);
//	public void updateCostePieasas(Reparacion reparacion);
//	public void updateEstado(Reparacion reparacion);
    public Reparacion  muestrarReparacion(int id);

}
