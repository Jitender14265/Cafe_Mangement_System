package CafeShop.Mangement.System.DatabseHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public final class databasehandler {

	public static Connection connectDB() {

		try {
			String url = "jdbc:mysql://localhost:3306/CAFE";
			String user = "root";
			String pass = "Jitender@6828";

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url, user, pass);
			return connect;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}