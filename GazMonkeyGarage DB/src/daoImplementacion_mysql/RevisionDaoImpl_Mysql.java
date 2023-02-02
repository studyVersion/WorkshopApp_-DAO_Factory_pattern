package daoImplementacion_mysql;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.RevisionDAO;
import daoAbstract_implementacion.RevisionDaoImpl;
import modelos.Revision;

public class RevisionDaoImpl_Mysql extends RevisionDaoImpl implements RevisionDAO {

	private Connection conexion = null;

	public void conectar() throws SQLException, IOException {
		Properties dbProperties = new Properties();
		InputStream input = new FileInputStream("dbconfig.properties");
		dbProperties.load(input);

		// get the properties values

		String url = dbProperties.getProperty("jdbc.url");
		String username = dbProperties.getProperty("jdbc.username");
		String password = dbProperties.getProperty("jdbc.password");

		// create a connection to the database

		conexion = DriverManager.getConnection(url, username, password);

		if (conexion == null) {

			System.out.println(" NO SE HA PODIDO REALIZAR LA CONEXION !!");
		}
	}

	public void cerrarConexion() throws SQLException {
		conexion.close();
	}

	@Override
	public void insertRevision(Revision revision) {

		try {
			conectar();

			String query = "INSERT INTO revision VALUES (?,?,?,?,?)";
			PreparedStatement st = conexion.prepareStatement(query);

			st.setInt(1, getUltimoID());
			st.setString(2, revision.getDescripcion());
			st.setInt(3, revision.getHorasEmpleadas());
			st.setDouble(4, revision.obtenerCoste());
			st.setString(5, revision.getEstado());

			st.executeUpdate();

			cerrarConexion();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int getUltimoID() {
		int id = 0;

		try {
			conectar();

			String query = "SELECT  sum(raws)  FROM ( SELECT count(*) as raws  FROM revision"
					+ " UNION all SELECT count(*) as raws  FROM reparacion  ) as sumID;";

			PreparedStatement st = conexion.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				id = rs.getInt(1);
			}

			cerrarConexion();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id + 1;
	}

	@Override
	public Revision getRevision(int id) {
		Revision revision = null;
		try {
			conectar();

			String query = "SELECT * FROM revision WHERE id_trabajo = ?";
			PreparedStatement st = conexion.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				revision = new Revision(rs.getString(2));
				revision.setIdentificador(rs.getInt(1));
				revision.setHorasEmpleadas(rs.getInt(3));
				revision.setPrecio(rs.getDouble(4));
				if (rs.getString(5).equals("Terminado")) {
					revision.finalizarTrabajo();
				}
			}
			cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return revision;
	}

	@Override
	public void updateRevision(Revision revision) {

		try {
			conectar();

			String query = "UPDATE revision SET horas = ? , precio_total = ? , estado = ?  WHERE id_trabajo  = ?";
			PreparedStatement st = conexion.prepareStatement(query);

			st.setInt(1, revision.getHorasEmpleadas());
			st.setDouble(2, revision.obtenerCoste());
			st.setString(3, revision.getEstado());
			st.setInt(4, revision.getIdentificador());

			st.executeUpdate();

			cerrarConexion();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
