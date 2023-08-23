package CafeShop.Mangement.System.Main;

import java.io.File;
import java.net.URL;
import java.nio.channels.UnresolvedAddressException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import CafeShop.Mangement.System.DatabseHandler.CustomerData;
import CafeShop.Mangement.System.DatabseHandler.ProductData;
import CafeShop.Mangement.System.DatabseHandler.databasehandler;
import CafeShop.Mangement.System.Menu.card_Contrller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private Button Add_btn;

	@FXML
	private AnchorPane Chart_pane;

	@FXML
	private Button Clear_btn;

	@FXML
	private Label Custmer_no;

	@FXML
	private Button Customersbtn;

	@FXML
	private Label Daily_income;

	@FXML
	private Button Dashboardbtn;

	@FXML
	private Button Delete_btn;

	@FXML
	private TextField Id_field;

	@FXML
	private Button Import_btn;

	@FXML
	private AreaChart<?, ?> IncomeChart_pane;

	@FXML
	private AnchorPane Income_Pane;

	@FXML
	private Button Inventorybtn;

	@FXML
	private AnchorPane Main_Pain;

	@FXML
	private Button Menubtn;

	@FXML
	private BarChart<?, ?> MonthlyChart_pane;

	@FXML
	private TextField Name_field;

	@FXML
	private TextField Price_field;

	@FXML
	private AnchorPane Inventry_ProductPane;

	@FXML
	private Label Product_no;

	@FXML
	private Button Signoutbtn;

	@FXML
	private ComboBox<String> Status_combo;

	@FXML
	private TextField Stock_field;

	@FXML
	private Label Total_income;

	@FXML
	private ComboBox<String> Type_combo;

	@FXML
	private Button Update_btn;

	@FXML
	private AnchorPane image_pane;

	@FXML
	private AnchorPane Inventory_Mainpane;

	@FXML
	private AnchorPane Side_Pane;

	@FXML
	private Label nameLabel;

	@FXML
	private TableColumn<ProductData, String> p_Id;

	@FXML
	private TableColumn<ProductData, String> p_Name;

	@FXML
	private TableColumn<ProductData, String> p_Stock;

	@FXML
	private TableColumn<ProductData, String> p_Type;

	@FXML
	private TableColumn<ProductData, String> p_date;

	@FXML
	private TableColumn<ProductData, Double> p_price;

	@FXML
	private TableColumn<ProductData, String> p_status;

	@FXML
	private TableColumn<ProductData, String> sNumber;

	@FXML
	private TableView<ProductData> productTableview;

	@FXML
	private ImageView importImage;

	private Image image;

	@FXML
	private AnchorPane scroll_anchor_pane;

	@FXML
	private ScrollPane scroll_pane;

	// -------------------------------Menu--------------------------------

	@FXML
	private TextField Menu_Amount_field;

	@FXML
	private AnchorPane Menu_MainPane;

	@FXML
	private AnchorPane Menu_Payment_pane;

	@FXML
	private TableColumn<ProductData, Double> Menu_Price_col;

	@FXML
	private TableView<ProductData> Menu_Product_table;

	@FXML
	private Button Menu_Receipt_btn;

	@FXML
	private Button Menu_Remove_btn;

	@FXML
	private Label Menu_Total;

	@FXML
	private Label Menu_change;

	@FXML
	private TableColumn<ProductData, String> Menu_name_col;

	@FXML
	private Button Menu_pay_btn;

	@FXML
	private TableColumn<ProductData, Integer> Menu_quantity_col;

	@FXML
	private ScrollPane Menu_scrollPane;

	@FXML
	private GridPane Grid_menu;

	@FXML
	private TableView<CustomerData> Customer_Table;

	@FXML
	private TableColumn<CustomerData, String> Cust_Customer_Cashier_col;

	@FXML
	private TableColumn<CustomerData, String> Cust_Customer_Date_col;

	@FXML
	private TableColumn<CustomerData, String> Cust_Customer_Total_col;

	@FXML
	private TableColumn<CustomerData, String> Cust_Customer_id_col;

	@FXML
	private Label Customer_label;

	@FXML
	private AnchorPane Customer_pane;

	private Alert alert;

	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private Statement statement;

	private ObservableList<ProductData> CardList = FXCollections.observableArrayList();

	// --------------- Dash Board ----------------------------------

	public void DashboardNumberOfCustomer() {
		String select = "Select count(id) From Receipt";
		connection = databasehandler.connectDB();
		try {
			int noC = 0;
			preparedStatement = connection.prepareStatement(select);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				noC = resultSet.getInt("Count(id)");

			}
			Custmer_no.setText(String.valueOf(noC));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// --------------------------- Dash Board Total Income ------------------

	public void DashBoardDailyIncome() {
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Double DailylIncome = 0.0;
		String selectString = "Select Sum(Total) From Receipt Where Date = '" + sqlDate + "'";
		connection = databasehandler.connectDB();
		try {

			preparedStatement = connection.prepareStatement(selectString);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				DailylIncome = resultSet.getDouble("SUM(Total)");
			}

			String formattedNumber = String.format("%.2f", DailylIncome);

			Daily_income.setText("$" + formattedNumber);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ------------------------ DashBoard Total Income ----------------------

	public void DashboardTotalIncome() {
		String selectString = "Select Sum(total) From Receipt";
		Double totalDouble = 0.0;
		connection = databasehandler.connectDB();
		try {
			preparedStatement = connection.prepareStatement(selectString);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				totalDouble = resultSet.getDouble("SUM(total)");
			}

			System.out.println("total Double" + totalDouble);
			String formattedNumber = String.format("%.2f", totalDouble);

			Total_income.setText("$" + formattedNumber);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ------------------- DashBoard Cart No Of Item ----------------

	public void DashboardNumberOfItem() {

		IncomeChart_pane.getData().clear();

		String selectString = "Select Count(Quantity) From Customer";
		Integer NoOfItemDouble = 0;
		connection = databasehandler.connectDB();
		try {
			preparedStatement = connection.prepareStatement(selectString);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				NoOfItemDouble = resultSet.getInt("Count(Quantity)");
			}
			Product_no.setText(String.valueOf(NoOfItemDouble));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ----------------- Dash Board income Chart -------------------------

	public void DashBoardIncomeChart() {
		IncomeChart_pane.getData().clear();
		String sqlString = "Select date ,Sum(Total) From Receipt Group By date Order By timestamp(date)";
		connection = databasehandler.connectDB();

		XYChart.Series Chart = new XYChart.Series();

		try {
			preparedStatement = connection.prepareStatement(sqlString);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2)));
			}

			IncomeChart_pane.getData().add(Chart);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ------------------------ Dash Board Customer Chart -----------------------

	public void DashBoardCustomerChart() {
		MonthlyChart_pane.getData().clear();
		String sqlString = "Select date ,count(ID) From Receipt Group By date Order By timestamp(date)";
		connection = databasehandler.connectDB();

		XYChart.Series Chart = new XYChart.Series();

		try {
			preparedStatement = connection.prepareStatement(sqlString);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
			}

			MonthlyChart_pane.getData().add(Chart);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ---------------------------------------------------------------------------------------

	public ObservableList<ProductData> inventoryList() {

		ObservableList<ProductData> listData = FXCollections.observableArrayList();
		String sql = "SELECT*FROM PRODUCT";
		connection = databasehandler.connectDB();
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			ProductData proData;
			while (resultSet.next()) {

				proData = new ProductData(resultSet.getString("pro_id"), resultSet.getString("pro_name"),
						resultSet.getString("stock"), resultSet.getString("status"), resultSet.getString("type"),
						resultSet.getString("image"), resultSet.getDouble("price"), resultSet.getDate("date"),
						resultSet.getInt("No_Of_Item"));

				listData.add(proData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;

	}

	// --------------------------------Show Data On
	// Table--------------------------------------

	private ObservableList<ProductData> invenoryListDatas;

	public void showInventoryListData() {
		invenoryListDatas = inventoryList();
		sNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
		p_Id.setCellValueFactory(new PropertyValueFactory<>("prod_Id"));
		p_Name.setCellValueFactory(new PropertyValueFactory<>("prod_Name"));
		p_Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		p_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		p_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
		p_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		p_date.setCellValueFactory(new PropertyValueFactory<>("date"));

		productTableview.setItems(invenoryListDatas);

	}

//------------------------------------------ Type Of Food-------------------------/

	private String liStrings[] = { "Meal", "Drink" };

	public void Inventry_ListType() {
		List<String> list = new ArrayList<String>();
		for (String data : liStrings) {
			list.add(data);
		}
		ObservableList<String> type = FXCollections.observableArrayList(list);

		Type_combo.setItems(type);

	}

	// ----------------------------Status Show-------------------------------------

	private String StatusList[] = { "Available", "Unavailabe" };

	public void Inventry_StatusType() {
		List<String> list = new ArrayList<String>();
		for (String data : StatusList) {
			list.add(data);
		}
		ObservableList<String> Status = FXCollections.observableArrayList(list);

		Status_combo.setItems(Status);

	}

	// -------------------------------- Import
	// Image-------------------------------------

	public void importImagebtn() {
		FileChooser file = new FileChooser();
		file.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

		File newfile = file.showOpenDialog(Main_Pain.getScene().getWindow());

		if (newfile != null) {
			Data.path = newfile.getAbsolutePath();
			image = new Image(newfile.toURI().toString(), 142, 159, false, true);

			importImage.setImage(image);
		}

	}

	// ----------------------------Add button-------------------------------

	public void AddButton() {

		if (Id_field.getText().isEmpty() || Name_field.getText().isEmpty() || Stock_field.getText().isEmpty()
				|| Price_field.getText().isEmpty() || Type_combo.getSelectionModel().getSelectedItem() == null
				|| Status_combo.getSelectionModel().getSelectedItem() == null || Data.path == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All The Field");
			alert.showAndWait();

		} else {

			String checkproID = "SELECT PRO_ID FROM PRODUCT WHERE PRO_ID ='" + Id_field.getText() + "'";

			connection = databasehandler.connectDB();

			try {

				statement = connection.createStatement();
				resultSet = statement.executeQuery(checkproID);

				if (resultSet.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("'" + Id_field.getText() + "' Product ID Is Aready exist");
					alert.showAndWait();

				} else {

					String insertData = "INSERT INTO PRODUCT (pro_id, pro_name, stock, price, status, Date, image, type)"
							+ "VALUES(?,?,?,?,?,?,?,?)";

					preparedStatement = connection.prepareStatement(insertData);
					preparedStatement.setString(1, Id_field.getText());
					preparedStatement.setString(2, Name_field.getText());
					preparedStatement.setInt(3, Integer.parseInt(Stock_field.getText()));
					preparedStatement.setDouble(4, Double.parseDouble(Price_field.getText()));
					preparedStatement.setString(5, (String) Status_combo.getSelectionModel().getSelectedItem());

					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					preparedStatement.setString(6, String.valueOf(sqlDate));

					String path = Data.path;
					path = path.replace("\\", "\\\\");
					preparedStatement.setString(7, path);

					preparedStatement.setString(8, (String) Type_combo.getSelectionModel().getSelectedItem());
					preparedStatement.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESSFULL");
					alert.setHeaderText(null);
					alert.setContentText("ADDED SUCCESSFULLY");
					alert.showAndWait();

					showInventoryListData();
					ClearBtn();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// ------------------------- update button -----------------------

	public void Updatebtn() {
		if (Id_field.getText().isEmpty() || Name_field.getText().isEmpty() || Stock_field.getText().isEmpty()
				|| Price_field.getText().isEmpty() || Type_combo.getSelectionModel().getSelectedItem() == null
				|| Status_combo.getSelectionModel().getSelectedItem() == null || Data.id == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All The Field");
			alert.showAndWait();

		} else {
			String path = Data.path;
			path = path.replace("\\", "\\\\");

			String updateString = "UPDATE PRODUCT SET " + "PRO_ID = '" + Id_field.getText() + "' ,PRO_NAME = '"
					+ Name_field.getText() + "' ,STOCK = ' " + Stock_field.getText() + "' , PRICE = '"
					+ Price_field.getText() + "' ,STATUS = '" + Status_combo.getSelectionModel().getSelectedItem()
					+ "' ,type = '" + Type_combo.getSelectionModel().getSelectedItem() + "',date = '" + Data.date
					+ "',image ='" + path + "' WHERE No_Of_Item='" + Data.id + "'";

			connection = databasehandler.connectDB();
			try {

				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conform");
				alert.setHeaderText(null);
				alert.setContentText("Are You Really Want to Update Product ID" + Id_field.getText());
				Optional<ButtonType> optional = alert.showAndWait();

				if (optional.get().equals(ButtonType.OK)) {
					preparedStatement = connection.prepareStatement(updateString);
					preparedStatement.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText(null);
					alert.setContentText("'" + Id_field.getText() + "'  Product Update Successfully");
					alert.showAndWait();
					showInventoryListData();
					ClearBtn();
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Canceled");
					alert.setHeaderText(null);
					alert.setContentText("'" + Id_field.getText() + "'  Product Cannot be Update");
					alert.showAndWait();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// --------------------------------Delete Button----------------------

	public void DeleteBtn() {
		if (Data.id == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ConForm");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to DELETE Product ID: " + Id_field.getText() + "?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
				String deleteData = "DELETE FROM product WHERE No_Of_Item = " + Data.id;
				try {
					preparedStatement = connection.prepareStatement(deleteData);
					preparedStatement.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Successfull");
					alert.setHeaderText(null);
					alert.setContentText("successfully Deleted!");
					alert.showAndWait();

					showInventoryListData();

					ClearBtn();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Cancelled");
				alert.showAndWait();
			}
		}
	}
	// -----------------Claear Button ------------------------------

	public void ClearBtn() {
		Id_field.setText("");
		Name_field.setText("");
		Price_field.setText("");
		Stock_field.setText("");
		Type_combo.getSelectionModel().clearSelection();
		Status_combo.getSelectionModel().clearSelection();
		Data.path = "";
		Data.id = 0;
		importImage.setImage(null);

	}

	// -------------------Select Data-------------------
	public void SelectData() {
		ProductData productData = productTableview.getSelectionModel().getSelectedItem();
		int num = productTableview.getSelectionModel().getSelectedIndex();
		if ((num - 1) < -1) {
			return;
		}
		Id_field.setText(productData.getProd_Id());
		Name_field.setText(productData.getProd_Name());
		Stock_field.setText(String.valueOf(productData.getStock()));
		Price_field.setText(String.valueOf(productData.getPrice()));

		Data.date = String.valueOf(productData.getDate());
		Data.id = productData.getId();
		try {
			Data.path = productData.getImage();
			String path = "File:" + productData.getImage();

			image = new Image(path, 142, 159, false, true);
			importImage.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// -------------------------------------- Menu Get Data-----------------

	public ObservableList<ProductData> menuGetData() {

		String sql = "select * from Product";

		connection = databasehandler.connectDB();
		ObservableList<ProductData> listData = FXCollections.observableArrayList();

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ProductData productData;

			while (resultSet.next()) {
				productData = new ProductData(resultSet.getInt("No_Of_Item"), resultSet.getString("pro_id"),
						resultSet.getString("pro_name"), resultSet.getString("type"), resultSet.getDouble("price"),
						resultSet.getString("image"), resultSet.getDate("date"), resultSet.getInt("stock"));
				listData.add(productData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return listData;

	}

	// ------------------- Menu Display Card ------------------

	public void menuDisplayCard() {

		CardList.clear();
		CardList.addAll(menuGetData());

		int row = 0;
		int column = 0;

		Grid_menu.getChildren().clear();
		Grid_menu.getRowConstraints().clear();
		Grid_menu.getColumnConstraints().clear();

		for (int i = 0; i < CardList.size(); i++) {

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/CafeShop/Mangement/System/Menu/ProductCart.fxml"));
				AnchorPane pane = loader.load();
				card_Contrller cart = loader.getController();
				cart.SetData(CardList.get(i));

				if (column == 3) {
					column = 0;
					row += 1;

				}
				Grid_menu.add(pane, column++, row);

				GridPane.setMargin(pane, new Insets(10));

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	// ------------------------------------ menu get item display
	// ------------------------

	public ObservableList<ProductData> getMenuOrderData() {
		CustomerID();

		String sql = "select * from customer where customer_id = " + cID;

		connection = databasehandler.connectDB();

		ObservableList<ProductData> listData = FXCollections.observableArrayList();

		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			ProductData productData;

			while (resultSet.next()) {
				productData = new ProductData(resultSet.getInt("id"), resultSet.getString("pro_id"),
						resultSet.getString("pro_Name"), resultSet.getString("type"), resultSet.getDouble("price"),
						resultSet.getString("image"), resultSet.getDate("date"), resultSet.getInt("quantity"));
				listData.add(productData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return listData;

	}

	// ----------------------------- Display Order ------------------------

	private ObservableList<ProductData> orderList;

	public void menuDisplayOrderData() {

		orderList = getMenuOrderData();
		Menu_name_col.setCellValueFactory(new PropertyValueFactory<>("prod_Name"));
		Menu_quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		Menu_Price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

		Menu_Product_table.setItems(orderList);

	}

//---------------------------------get total -------------------------	
	private Double totalP;

	public void menu_calTotal() {
		CustomerID();
		String total = "select sum(price) from customer where customer_id = " + cID;
		connection = databasehandler.connectDB();

		try {

			preparedStatement = connection.prepareStatement(total);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				totalP = resultSet.getDouble("sum(price)");
			}

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}

	}

	/////// ---------------------------Display total -------------------------

	public void menuDisplayTotal() {

		menu_calTotal();
		Menu_Total.setText("$" + totalP);

	}

	private double amount;
	private double change;

	public void menuAmountandChange() {
		menu_calTotal();
		if (Menu_Amount_field.getText().isEmpty() || totalP == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Invalid :3");
			alert.showAndWait();
		} else {

			amount = Double.parseDouble(Menu_Amount_field.getText());

			if (amount < totalP) {

				Menu_Amount_field.setText("");

			} else {

				change = (amount - totalP);
				Menu_change.setText("$" + change);

			}
		}
	}

	// ----------------------- Menu Pay button ---------------------------------

	public void menuPayBtn() {

		if (totalP == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please choose your order first!");
			alert.showAndWait();

		} else {

			menu_calTotal();

			String insertPay = "INSERT INTO receipt (customer_id, total, date, em_name) " + "VALUES(?,?,?,?)";

			connection = databasehandler.connectDB();

			try {

				if (amount == 0) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Messaged");
					alert.setHeaderText(null);
					alert.setContentText("Something wrong :3");
					alert.showAndWait();
				} else {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Message");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {
						CustomerID();
						menu_calTotal();
						preparedStatement = connection.prepareStatement(insertPay);
						preparedStatement.setString(1, String.valueOf(cID));
						preparedStatement.setString(2, String.valueOf(totalP));

						Date date = new Date();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());

						preparedStatement.setString(3, String.valueOf(sqlDate));
						preparedStatement.setString(4, Data.userName);

						preparedStatement.executeUpdate();

						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Infomation Message");
						alert.setHeaderText(null);
						alert.setContentText("Successful.");
						alert.showAndWait();

						menuDisplayOrderData();
						menuDisplayCard();
					

					} else {
						alert = new Alert(AlertType.WARNING);
						alert.setTitle("Infomation Message");
						alert.setHeaderText(null);
						alert.setContentText("Cancelled.");
						alert.showAndWait();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// ---------------------- menu Select Itemss ---------------------------

	private int getid;
	private int getstock;
	private String proidString;

	public void menuselecteditem() {
		ProductData productData = Menu_Product_table.getSelectionModel().getSelectedItem();
		int index = Menu_Product_table.getSelectionModel().getSelectedIndex();

		if ((index - 1) < -1) {
			return;
		}
		getid = productData.getId();
		getstock = productData.getQuantity();
	}

	// ---------------------------- REMOVE Button --------------------------

	public void menu_Remove_btn() {
		if (getid == 0) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please Select the item ");
			alert.showAndWait();
		} else {

			String delete = "Delete From Customer Where id = " + getid;

			connection = databasehandler.connectDB();

			try {

				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("COnfrom");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure want to delete the item ");
				Optional<ButtonType> optional = alert.showAndWait();

				if (optional.get().equals(ButtonType.OK)) {
					preparedStatement = connection.prepareStatement(delete);
					preparedStatement.executeUpdate();

					// --------------------------------------
					
					

				}
				menuDisplayOrderData();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	private void clearTableData() {
		CardList.clear();
		orderList.clear();
	}

	// ------------------------------- Receipt Button ---------------------

	/*
	 * public void menu_ReceiptButton() { if
	 * (totalP==0||Menu_Amount_field.getText().isEmpty()) {
	 * 
	 * alert = new Alert(AlertType.ERROR); alert.setTitle("Error");
	 * alert.setHeaderText(null);
	 * alert.setContentText("Please Fill The Amount Field Or  Select The Items ");
	 * alert.showAndWait(); } { CustomerID(); HashMap map=new HashMap();
	 * map.put("getReceipt", (cID-1));
	 * 
	 * try { JasperDesign jDesign= JRXmlLoader.load(
	 * "/CafeShop.Mangement.System/src/CafeShop/Mangement/System/Main/CafeShopBill.jrxml"
	 * ); JasperReport report= JasperCompileManager.compileReport(jDesign);
	 * JasperPrint print=JasperFillManager.fillReport(report, map,connection);
	 * JasperViewer.viewReport(print,false);
	 * 
	 * } catch (JRException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * } }
	 */

	// ---------------------- Menu Reset -----------------------

	public void menuRestart() {
		totalP = 0.0;
		change = 0.0;
		amount = 0.0;
		Menu_Total.setText("$ 0.0");
		Menu_Amount_field.setText("");
		Menu_change.setText("$ 0.0");

	}

//------------------------  Customer  ID  -----------------------------
	private int cID=0;

	public void CustomerID() {
		String sqlString = "Select Max(Customer_id) from customer";
		connection = databasehandler.connectDB();

		try {
			preparedStatement = connection.prepareStatement(sqlString);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				cID = resultSet.getInt("Max(customer_id)");
			}

			String checkCID = "Select Max(customer_id) from receipt";
			preparedStatement = connection.prepareStatement(checkCID);
			resultSet = preparedStatement.executeQuery();
			int checkID = 0;
			if (resultSet.next()) {
				checkID = resultSet.getInt("Max(customer_id)");

			}
			if (cID == 0) {
				cID += 1;

			} else if (cID == checkID) {
				cID += 1;

			}
			Data.cID = cID;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ------------------------------

	public ObservableList<CustomerData> customersDataList() {

		ObservableList<CustomerData> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM receipt";
		connection = databasehandler.connectDB();

		try {

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			CustomerData cData;

			while (resultSet.next()) {
				cData = new CustomerData(resultSet.getInt("id"), resultSet.getInt("customer_id"),
						resultSet.getDouble("total"), resultSet.getDate("date"), resultSet.getString("em_name"));

				listData.add(cData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	private ObservableList<CustomerData> customersListData;

	public void customersShowData() {
		customersListData = customersDataList();

		Cust_Customer_id_col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		Cust_Customer_Total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
		Cust_Customer_Date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
		Cust_Customer_Cashier_col.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

		Customer_Table.setItems(customersListData);
	}

//-----------------------------------------Switch Form  -----------------------//

	public void SwitchFormButton(ActionEvent event) {

		if (event.getSource() == Dashboardbtn) {
			Income_Pane.setVisible(true);
			Inventry_ProductPane.setVisible(false);
			Menu_MainPane.setVisible(false);
			Customer_pane.setVisible(false);

			DashboardNumberOfItem();
			DashBoardDailyIncome();
			DashboardTotalIncome();
			DashboardNumberOfCustomer();
			DashBoardIncomeChart();
			DashBoardCustomerChart();

		} else if (event.getSource() == Inventorybtn) {
			Income_Pane.setVisible(false);
			Inventry_ProductPane.setVisible(true);
			Menu_MainPane.setVisible(false);
			Customer_pane.setVisible(false);
			Inventry_ListType();
			Inventry_StatusType();
			showInventoryListData();

		} else if (event.getSource() == Menubtn) {
			Income_Pane.setVisible(false);
			Inventry_ProductPane.setVisible(false);
			Menu_MainPane.setVisible(true);
			Customer_pane.setVisible(false);

			menuDisplayCard();
			menuDisplayOrderData();
			menuDisplayTotal();

		} else if (event.getSource() == Customersbtn) {
			Income_Pane.setVisible(false);
			Inventry_ProductPane.setVisible(false);
			Menu_MainPane.setVisible(false);
			Customer_pane.setVisible(true);

			customersShowData();
		}

	}

	public void logout() {
		try {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Conformation");
			alert.setHeaderText(null);
			alert.setContentText("Are you Really Want to Sign Out");
			Optional<ButtonType> Button = alert.showAndWait();

			if (Button.get().equals(ButtonType.OK)) {
				Parent rootParent = FXMLLoader
						.load(getClass().getResource("/CafeShop/Mangement/System/Login/LoginPage.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(rootParent);
				stage.setTitle("Login To Cafe Mangement");
				stage.setScene(scene);
				stage.show();

				Signoutbtn.getScene().getWindow().hide();

			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void DisplayUsername() {
		String user = Data.userName;
		user = user.substring(0, 1).toUpperCase() + user.substring(1);
		nameLabel.setText(user);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		DisplayUsername();

		DashboardNumberOfItem();
		DashBoardDailyIncome();
		DashboardTotalIncome();
		DashboardNumberOfCustomer();
		DashBoardIncomeChart();
		DashBoardCustomerChart();

		Inventry_ListType();
		Inventry_StatusType();
		showInventoryListData();

		customersShowData();
		CustomerID();

		menuDisplayCard();
		menuDisplayOrderData();
		menuDisplayTotal();
		menu_calTotal();
	}

}
