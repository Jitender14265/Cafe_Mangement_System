package CafeShop.Mangement.System.Menu;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import CafeShop.Mangement.System.DatabseHandler.ProductData;
import CafeShop.Mangement.System.DatabseHandler.databasehandler;
import CafeShop.Mangement.System.Main.Data;
import CafeShop.Mangement.System.Main.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class card_Contrller implements Initializable {

	@FXML
	private Button Cprod_addBtn;

	@FXML
	private ImageView Cprod_imageView;

	@FXML
	private Label Cprod_name;

	@FXML
	private Label Cprod_price;

	@FXML
	private Spinner<Integer> Cprod_spinner;

	@FXML
	private AnchorPane card_form;

	private Alert alert;
	private Image image;
	private SpinnerValueFactory<Integer> spin;

	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;

	private ProductData productData;

	private double pr;
	private double total;
	private String Type;
	private String pro_date;
	private String prod_id;
	private String prod_imgae;

	private int qty;

	
	public void SetData(ProductData productData) {
		this.productData = productData;
		Cprod_name.setText(productData.getProd_Name());
		Cprod_price.setText("$" + String.valueOf(productData.getPrice()));
		Data.path = productData.getImage();
		String path = "File:" + productData.getImage();
		image = new Image(path, 190, 94, false, true);
		Cprod_imageView.setImage(image);
		prod_imgae = Data.path;
		prod_imgae = prod_imgae.replace("\\", "\\\\");
		// prod_imgae = productData.getImage();

		pr = productData.getPrice();
		Type = productData.getType();
		pro_date = String.valueOf(productData.getDate());
		prod_id = productData.getProd_Id();

	}

	public void setQuantity() {
		spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		Cprod_spinner.setValueFactory(spin);

	}

	public void Addbtn() {
		MainController mainController = new MainController();
		mainController.CustomerID();
		qty = Cprod_spinner.getValue();

		String check = "";
		String checkvalue = "Select status from Product Where Pro_id = '" + prod_id + "'";
		System.out.println("proid"+prod_id);

		connection = databasehandler.connectDB();

		try {
			int checkstcok = 0;

			String checkString = "Select stock from product where pro_id= '" + prod_id + "'";
			preparedStatement = connection.prepareStatement(checkString);
			resultSet = preparedStatement.executeQuery();
			
			System.out.println("--------------------check stock--------------");
			if (resultSet.next()) {
				checkstcok = resultSet.getInt("stock");
			}

			if (checkstcok == 0) {

				String upDateStatus = "Update product set pro_name = '" + Cprod_name.getText() + "', type = '" + Type
						+ "', stock = '" + 0 + "', price = '" + pr + "' , status = 'Unavailable', image = '"
						+ prod_imgae + "', date = '" + pro_date + "' where pro_id = '" + prod_id + "'";

				preparedStatement = connection.prepareStatement(upDateStatus);
				preparedStatement.executeUpdate();

			}

			preparedStatement = connection.prepareStatement(checkvalue);
			resultSet = preparedStatement.executeQuery();

		

			if (resultSet.next()) {
				check = resultSet.getString("status");

			}
			if (qty == 0) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Selection :");
				alert.showAndWait();

				

			} else if (!check.equals("Available")) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("Unavailable :");
				alert.showAndWait();
				

			} else {

				if (checkstcok < qty) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("Out of Stock");
					alert.showAndWait();
					

				} else {
					String insertdata = "INSERT INTO CUSTOMER "
							+ " (Customer_id,pro_name,quantity,price,date,emp_name,pro_id,image,type) "
							+ "Values(?,?,?,?,?,?,?,?,?)";
					System.out.println("------11----------------");
					preparedStatement = connection.prepareStatement(insertdata);
					preparedStatement.setString(1, String.valueOf(Data.cID));
					preparedStatement.setString(2, Cprod_name.getText());
					preparedStatement.setString(3, String.valueOf(qty));
					System.out.println("CID  " + Data.cID);
					total = (qty * pr);
					preparedStatement.setString(4, String.valueOf(total));

					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					preparedStatement.setString(5, String.valueOf(sqlDate));

					preparedStatement.setString(6, Data.userName);
					preparedStatement.setString(7, prod_id);
					preparedStatement.setString(8, prod_imgae);
					preparedStatement.setString(9, Type);
					System.out.println("---------------------22--------------------");
					preparedStatement.executeUpdate();
					int upStock = (checkstcok - qty);

					String upDateStock = "Update product set pro_name = '" + Cprod_name.getText() + "', type = '" + Type
							+ "', stock = '" + upStock + "', price = '" + pr + "' , status = '" + check + "', image = '"
							+ prod_imgae + "', date = '" + pro_date + "' where pro_id = '" + prod_id + "'";

					preparedStatement = connection.prepareStatement(upDateStock);
					preparedStatement.executeUpdate();
					System.out.println("--------------------44---------------------");

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESS");
					alert.setHeaderText(null);
					alert.setContentText("SuccessFully Added");
					alert.showAndWait();

					
					mainController.menu_calTotal();
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MainController mainController = new MainController();
		mainController.CustomerID();
		setQuantity();
	}

}
