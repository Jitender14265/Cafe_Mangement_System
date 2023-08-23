package CafeShop.Mangement.System.Login;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import CafeShop.Mangement.System.DatabseHandler.databasehandler;
import CafeShop.Mangement.System.Main.Data;
import javafx.fxml.Initializable;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

	@FXML
	private Button CreateButton;

	@FXML
	private AnchorPane CreateForm;

	@FXML
	private Label CreateLabel;

	@FXML
	private Button LoginButton;

	@FXML
	private AnchorPane LoginForm;

	@FXML
	private Label LoginLabel;

	@FXML
	private PasswordField LoginPassword;

	@FXML
	private TextField LoginUserame;

	@FXML
	private ComboBox<?> QuestionCombobox;

	@FXML
	private TextField RegisterAnswer;

	@FXML
	private Label RegisterLabel;

	@FXML
	private PasswordField RegisterPassword;

	@FXML
	private TextField RegisterUsername;

	@FXML
	private AnchorPane SideForm;

	@FXML
	private Button SignupButton;

	@FXML
	private Hyperlink forgetpassword;

	@FXML
	private Button AlreadyButton;

	@FXML
	private Button Back;

	@FXML
	private Button ChangePass;

	@FXML
	private PasswordField ConfirmNewPass;

	@FXML
	private AnchorPane Forgetpass;

	@FXML
	private AnchorPane NewPass;

	@FXML
	private PasswordField NewPassword;

	@FXML
	private Button Proceed;

	@FXML
	private ComboBox<String> fp_Question;

	@FXML
	private TextField fp_qAnswer;

	@FXML
	private TextField fp_Username;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Alert alert;

	public void loginbutton() {
		if (LoginUserame.getText().isEmpty() || LoginPassword.getText().isEmpty()) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All input field");
			alert.showAndWait();
		} else {
			String selectdata = "SELECT username,password FROM CAFE WHERE username =? and password=?";
			connect = databasehandler.connectDB();
			try {
				prepare = connect.prepareStatement(selectdata);
				prepare.setString(1, LoginUserame.getText());
				prepare.setString(2, LoginPassword.getText());
				result = prepare.executeQuery();

				if (result.next()) {

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESS");
					alert.setHeaderText(null);
					alert.setContentText("LOGIN SUCCESS");
					alert.showAndWait();
					Data.userName = LoginUserame.getText();
					Parent rootParent = FXMLLoader
							.load(getClass().getResource("/CafeShop/Mangement/System/Main/Main.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(rootParent, 1150, 600);
					stage.setTitle("Cafe Shop Mangement");
					stage.setScene(scene);
					stage.show();

					LoginButton.getScene().getWindow().hide();

				} else {

					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Incorrect username/Password");
					alert.showAndWait();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void forgetswitch() {
		LoginForm.setVisible(false);
		Forgetpass.setVisible(true);
		forgotpassQuestion();
	}

	public void forgotpassQuestion() {

		List<String> listQ = new ArrayList<String>();
		for (String data : questionLiStrings) {
			listQ.add(data);
		}
		ObservableList listData = FXCollections.observableArrayList(listQ);
		fp_Question.setItems(listData);
	}

	public void proceedbtn() {

		if (fp_qAnswer.getText().isEmpty() || fp_Username.getText().isEmpty()
				|| fp_Question.getSelectionModel().getSelectedItem() == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please enter in All the Field");
			alert.showAndWait();
		} else {
			String slectdata = "SELECT USERNAME,QUESTION,ANSWER FROM CAFE WHERE USERNAME=? AND QUESTION=? AND ANSWER=?";
			try {
				connect = databasehandler.connectDB();
				prepare = connect.prepareStatement(slectdata);
				prepare.setString(1, fp_Username.getText());
				prepare.setString(2, fp_Question.getSelectionModel().getSelectedItem());
				prepare.setString(3, fp_qAnswer.getText());
				result = prepare.executeQuery();

				if (result.next()) {

					NewPass.setVisible(true);
					Forgetpass.setVisible(false);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Invalid Username/Answer");
					alert.showAndWait();

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void changepass() {

		if (NewPassword.getText().isEmpty() || ConfirmNewPass.getText().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All The Field");
			alert.showAndWait();

		} else {

			if (NewPassword.getText().equals(ConfirmNewPass.getText())) {
				String getdate = "SELECT DATE FROM CAFE WHERE USERNAME = '" + fp_Username.getText() + "'";
				connect = databasehandler.connectDB();

				try {
					prepare = connect.prepareStatement(getdate);
					result = prepare.executeQuery();
					String date = "";
					if (result.next()) {
						date = result.getString("date");

					}
					String updateString = "UPDATE CAFE SET PASSWORD= '" + NewPassword.getText() + "' ,Question = '"
							+ fp_Question.getSelectionModel().getSelectedItem() + "', Answer = '" + fp_qAnswer.getText()
							+ "' ,date= '" + date + "'WHERE USERNAME='" + fp_Username.getText() + "'";
					prepare = connect.prepareStatement(updateString);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESS");
					alert.setHeaderText(null);
					alert.setContentText("Password update successfully");
					alert.showAndWait();

					LoginForm.setVisible(true);
					NewPass.setVisible(false);

					NewPassword.setText("");
					ConfirmNewPass.setText("");
					fp_qAnswer.setText("");
					fp_Username.setText("");
					fp_Question.getSelectionModel().clearSelection();

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Password Not Match");
				alert.showAndWait();

			}

		}

	}

	public void backtoLogin() {
		LoginForm.setVisible(true);
		Forgetpass.setVisible(false);

	}

	public void backtoforgetpass() {
		LoginForm.setVisible(false);
		Forgetpass.setVisible(true);
		NewPass.setVisible(false);

	}

	public void regBtn() {
		if (RegisterUsername.getText().isEmpty() || RegisterPassword.getText().isEmpty()
				|| QuestionCombobox.getSelectionModel().getSelectedItem() == null
				|| RegisterAnswer.getText().isEmpty()) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All input field");
			alert.showAndWait();

		} else {
			String regData = "INSERT INTO CAFE(username,password,Question,Answer,date)" + "VALUES (?,?,?,?,?)";
			connect = databasehandler.connectDB();

			try {

				String checkuser = "SELECT USERNAME FROM CAFE WHERE USERNAME = '" + RegisterUsername.getText() + "'";
				prepare = connect.prepareStatement(checkuser);
				result = prepare.executeQuery();
				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText(RegisterUsername.getText() + "  User Already Exist");
					alert.showAndWait();

				} else if (RegisterPassword.getText().length() < 8) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Password must More Than 8 Character");
					alert.showAndWait();

				} else {
					prepare = connect.prepareStatement(regData);
					prepare.setString(1, RegisterUsername.getText());
					prepare.setString(2, RegisterPassword.getText());
					prepare.setString(3, (String) QuestionCombobox.getSelectionModel().getSelectedItem());
					prepare.setString(4, RegisterAnswer.getText());

					java.util.Date date = new java.util.Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					prepare.setString(5, String.valueOf(sqlDate));

					prepare.executeUpdate();
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCcESS");
					alert.setHeaderText(null);
					alert.setContentText("Account Has been Created");
					alert.showAndWait();

					RegisterUsername.setText("");
					RegisterPassword.setText("");
					RegisterAnswer.setText("");
					QuestionCombobox.getSelectionModel().clearSelection();

					TranslateTransition slider = new TranslateTransition();

					slider.setNode(SideForm);
					slider.setToX(0);

					slider.setDuration(Duration.seconds(.5));

					CreateForm.setVisible(false);
					slider.setOnFinished((ActionEvent e) -> {
						LoginForm.setVisible(true);
						AlreadyButton.setVisible(false);
						CreateButton.setVisible(true);

					});
					slider.play();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String[] questionLiStrings = { "What is your Favourite Color", "What is your favorite food",
			"Waht is your bhirth date" };

	@FXML
	private void reqlQuestionList() {
		List<String> listQ = new ArrayList<String>();
		for (String data : questionLiStrings) {
			listQ.add(data);
		}
		ObservableList listData = FXCollections.observableArrayList(listQ);
		QuestionCombobox.setItems(listData);
	}

	@FXML
	private void switchForm(ActionEvent event) {

		TranslateTransition slider = new TranslateTransition();

		if (event.getSource() == CreateButton) {

			slider.setNode(SideForm);
			slider.setToX(300);
			slider.setDuration(Duration.seconds(.5));
			LoginForm.setVisible(false);
			Forgetpass.setVisible(false);
			NewPass.setVisible(false);
			slider.setOnFinished((ActionEvent e) -> {
				RegisterUsername.setText("");
				RegisterPassword.setText("");
				RegisterAnswer.setText("");
				CreateForm.setVisible(true);
				AlreadyButton.setVisible(true);
				CreateButton.setVisible(false);
				reqlQuestionList();
			});
			slider.play();

		} else if (event.getSource() == AlreadyButton) {
			slider.setNode(SideForm);
			slider.setToX(0);

			slider.setDuration(Duration.seconds(.5));

			CreateForm.setVisible(false);
			slider.setOnFinished((ActionEvent e) -> {
				LoginUserame.setText("");
				LoginPassword.setText("");
				LoginForm.setVisible(true);
				AlreadyButton.setVisible(false);
				CreateButton.setVisible(true);

			});
			slider.play();

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reqlQuestionList();
	}

}
