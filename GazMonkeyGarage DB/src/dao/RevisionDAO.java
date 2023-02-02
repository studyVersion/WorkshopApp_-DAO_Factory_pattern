package dao;


import modelos.*;

public interface RevisionDAO {
     
	public void insertRevision(Revision revision) ;
	public int getUltimoID();
	public Revision getRevision(int id);
	public void updateRevision(Revision revision);
//	public void updateEstado(Revision revision);
    public Revision muestrarRevision(int id);
    

    
    
}
