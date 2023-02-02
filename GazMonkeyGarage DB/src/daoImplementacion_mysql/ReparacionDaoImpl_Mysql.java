package daoImplementacion_mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import dao.ReparacionDAO;
import daoAbstract_implementacion.ReparacionDaoImpl;
import modelos.Reparacion;
import modelos.ReparacionChapaPintura;
import modelos.ReparacionMecanica;


public class ReparacionDaoImpl_Mysql extends ReparacionDaoImpl implements ReparacionDAO {

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
	public void insertReparacion(Reparacion reparacion) {

		try {
			conectar();

			String query = "INSERT INTO reparacion   VALUES (?,?,?,?,?,?,?)";
			PreparedStatement st = conexion.prepareStatement(query);
			
			st.setInt(1, getUltimoID());
			if (reparacion instanceof ReparacionChapaPintura) {
				st.setString(2, "RCP");
			} else {
				st.setString(2, "RM");
			}
			st.setString(3, reparacion.getDescripcion());
			st.setInt(4, reparacion.getHorasEmpleadas());
			st.setDouble(5, reparacion.getCosteMaterial());
			st.setDouble(6, reparacion.obtenerCoste());
			st.setString(7, reparacion.getEstado());

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
	public Reparacion getReparacion(int id) {
		Reparacion reparacion = null;
		try {
			conectar();

			String query = "SELECT * FROM reparacion WHERE id_trabajo = ?";
			PreparedStatement st = conexion.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				// si es una reparacion mecanica
				if (rs.getString(2).equals("RM")) {
					reparacion = new ReparacionMecanica(rs.getString(3));
					reparacion.setIdentificador(rs.getInt(1));
					reparacion.setHorasEmpleadas(rs.getInt(4));
					reparacion.setCosteMaterial(rs.getDouble(5));
					reparacion.setPrecio(rs.getDouble(6));
					// si es una reparacion chapa
				} else if (rs.getString(2).equals("RCP")) {
					reparacion = new ReparacionChapaPintura(rs.getString(3));
					reparacion.setIdentificador(rs.getInt(1));
					reparacion.setHorasEmpleadas(rs.getInt(4));
					reparacion.setCosteMaterial(rs.getDouble(5));
					reparacion.setPrecio(rs.getDouble(6));
				}
				if (rs.getString(7).equals("Terminado")) {
					reparacion.finalizarTrabajo();
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
		return reparacion;
	}

	@Override
	public void updateReparacion(Reparacion reparacion) {

		try {
			conectar();

			String query = "UPDATE reparacion SET horas = ? , coste_material = ? , precio_total = ? ,"
				       	 + "estado = ? WHERE id_trabajo  = ?";
			PreparedStatement st = conexion.prepareStatement(query);

			st.setInt(1, reparacion.getHorasEmpleadas());
			st.setDouble(2, reparacion.getCosteMaterial());
			st.setDouble(3, reparacion.obtenerCoste());
			st.setString(4, reparacion.getEstado());
			st.setInt(5, reparacion.getIdentificador());

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
	public Reparacion muestrarReparacion(int id) {
		Reparacion rep= getReparacion(id);	
		return rep;
	}

	
}