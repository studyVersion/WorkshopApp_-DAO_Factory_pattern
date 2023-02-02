package daoImplementacion_obejctDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import dao.RevisionDAO;
import daoAbstract_implementacion.RevisionDaoImpl;
import modelos.Revision;
import modelos.Trabajo;

public class RevisionDaoImpl_objectDB extends RevisionDaoImpl implements RevisionDAO {

	private EntityManager em = null;
	private EntityManagerFactory emf = null;

	public void conectar() throws IOException {
		Properties dbProperties = new Properties();
		InputStream input = new FileInputStream("dbconfig.properties");
		dbProperties.load(input);
		String url = dbProperties.getProperty("objectDB.db");
		emf = Persistence.createEntityManagerFactory(url);
		em = emf.createEntityManager();

	}

	public void cerrarConexion() {

		if (emf != null && emf.isOpen()) {
			emf.close();
		}
		if (em != null && em.isOpen()) {
			em.close();
		}

	}

	@Override
	public void insertRevision(Revision revision) {
		try {

			conectar();
			em.getTransaction().begin();
			em.persist(revision);
			em.getTransaction().commit();
//			Query q1 = em.createQuery("SELECT COUNT(revision) FROM Revision revision");
//			System.out.println("Total Points: " + q1.getSingleResult());
			cerrarConexion();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public Revision getRevision(int id) {
		Revision rev = null;
		try {
			conectar();
			rev = em.find(Revision.class, id);
			cerrarConexion();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return rev;
	}

	@Override
	public void updateRevision(Revision revision) {
		try {
			conectar();

			em.getTransaction().begin();
			em.merge(revision);
			em.getTransaction().commit();
			cerrarConexion();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Revision muestrarRevision(int id) {

		Revision rev = getRevision(id);

		return rev;
	}

	@Override
	public int getUltimoID() {
		int id = 0;

		try {
			conectar();

			TypedQuery<Trabajo> query = em.createQuery("SELECT trabajo FROM Trabajo trabajo", Trabajo.class);
			List<Trabajo> results = query.getResultList();

			for (Trabajo tr : results) {
				if (tr.getIdentificador() > id) {
					id = tr.getIdentificador();
				}
			}

			cerrarConexion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id + 1;
	}

}
