package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label; 
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@SuppressWarnings("unchecked")
public class Main extends Application {
	String[][] login = { { "Anas", "anas" }, { "Salah", "salah" }, { "Basem", "basem" } };
	Scene welcomeSceneEng;
	ArrayList<NonRentCar> nonRentVehicles = new ArrayList<>();
	ArrayList<RentCar> rentVehicles = new ArrayList<>();
	ArrayList<Customer> customers = new ArrayList<>();
	ArrayList<Employee> employee = new ArrayList<>();
	ArrayList<RentalBill> payments = new ArrayList<>();

	private static Connection con;
	private Alert error = new Alert(AlertType.ERROR);
	private Alert success = new Alert(AlertType.INFORMATION);
	String userLogin;

	@Override
	public void start(Stage primaryStage) {
		try {
			connectDataBase(primaryStage);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Anas and Salah Car Rental System");
			primaryStage.getIcons().add(new Image("car-rental.png"));

		} catch (Exception e) {
			error.setContentText(e.getMessage());
			error.show();
		}
	}

	private void connectDataBase(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		Label welcome = new Label("				");
		welcome.setStyle("-fx-font-size: 50;");
		welcome.setTextFill(Color.web("silver"));

		VBox v1 = new VBox(40, welcome);
		v1.setAlignment(Pos.CENTER);

		Label user = new Label("Data Base Name:");
		user.setPadding(new Insets(7));
		Label pass = new Label("PASSWORD :");
		pass.setPadding(new Insets(7));
		TextField usert = new TextField();
		PasswordField passt = new PasswordField();
		IconedTextFieled(user, usert);
		IconedTextFieled(pass, passt);

		HBox h1 = new HBox(user, usert);
		h1.setAlignment(Pos.CENTER);
		HBox h2 = new HBox(pass, passt);
		h2.setAlignment(Pos.CENTER);

		Button logIN = new Button("lOG IN");
		logIN.setEffect(new DropShadow());

		icons(logIN);
		butoonEffect(logIN);

		Label wrongPass = new Label();

		VBox v = new VBox(30, wrongPass, h1, h2, logIN);
		v.setAlignment(Pos.CENTER);

		HBox hh = new HBox(200, v1, v);

		hh.setAlignment(Pos.CENTER);
		pane.setCenter(hh);
		logIN.setOnAction(e -> {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/" + usert.getText() + "?autoReconnect=true&useSSL=false", "root",
						passt.getText());
				loginPage(primaryStage);

			} catch (SQLException ee) {
				error.setContentText("Can't connect to database");
				error.show();
			}
		});

		pane.setCenter(hh);

		welcomeSceneEng = new Scene(pane, 1535, 800);
		primaryStage.setScene(welcomeSceneEng);
		primaryStage.show();

	}

	private ResultSet appyQueryOnDataBase(String string) {

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(string);
			return rs;
		} catch (SQLException e) {
			error.setContentText(string);
			error.show();
		}

		return null;

	}

	private boolean applyOnDataBase(String string) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			boolean rs = stmt.execute(string);
			return rs;
		} catch (SQLException e) {
			error.setContentText(string);
			error.show();
		}
		return false;

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void loginPage(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		Label welcome = new Label("				");
		welcome.setStyle("-fx-font-size: 50;");
		welcome.setTextFill(Color.web("silver"));

		VBox v1 = new VBox(40, welcome);
		v1.setAlignment(Pos.CENTER);

		Label user = new Label("USER NAME");
		user.setPadding(new Insets(7));
		Label pass = new Label("PASSWORD ");
		pass.setPadding(new Insets(7));
		TextField usert = new TextField();
		PasswordField passt = new PasswordField();
		IconedTextFieled(user, usert);
		IconedTextFieled(pass, passt);

		HBox h1 = new HBox(user, usert);
		h1.setAlignment(Pos.CENTER);
		HBox h2 = new HBox(pass, passt);
		h2.setAlignment(Pos.CENTER);

		Button logIN = new Button("lOG IN");
		logIN.setEffect(new DropShadow());

		icons(logIN);
		butoonEffect(logIN);

		Label wrongPass = new Label();

		VBox v = new VBox(30, wrongPass, h1, h2, logIN);
		v.setAlignment(Pos.CENTER);

		HBox hh = new HBox(200, v1, v);

		hh.setAlignment(Pos.CENTER);
		pane.setCenter(hh);
		logIN.setOnAction(e -> {
			boolean flag = false;
			for (int j = 0; j < login.length; j++) {
				if (usert.getText().equals(login[j][0]) && passt.getText().equals(login[j][1])) {
					flag = true;
					userLogin = usert.getText();
					Action(primaryStage);
					wrongPass.setText("");
					usert.clear();
					passt.clear();
				}
			}
			if (!flag) {
				wrongPass.setText("! Wrong Username or Password !");
				wrongPass.setStyle("-fx-font-size: 15;\n" + "-fx-text-fill: silver;");
				usert.clear();
				passt.clear();
			}
		});

		pane.setCenter(hh);

		welcomeSceneEng = new Scene(pane, 1535, 800);
		primaryStage.setScene(welcomeSceneEng);
		primaryStage.show();
	}

	private void Action(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		ImageView a = new ImageView(new Image("racing-car.png"));
		a.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		a.setFitHeight(60);
		a.setFitWidth(70);

		TableView<RentCar> rentTable = tableRentCar();
		TableView<RentalBill> PaymentTable = tableBill();
		TableView<Customer> customerTable = tableCustomer();
		TableView<NonRentCar> nonRentTable = tableNonRentCar();

		Label l = new Label("   Renting Car   ");
		l.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l.setFont(new Font("Times New Roman", 15));

		Label l2 = new Label("0");
		l2.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l2.setFont(new Font("Times New Roman", 15));

		VBox h = new VBox(15, l, a, l2);
		h.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		h.setPadding(new Insets(10));
		h.setAlignment(Pos.CENTER);
		h.setPrefSize(150, 150);
		icons(h);
		butoonEffect(h);

		ImageView a2 = new ImageView(new Image("racing-car.png"));
		a2.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		a2.setFitHeight(60);
		a2.setFitWidth(70);
		Label l3 = new Label("Non-Renting Car");
		l3.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l3.setFont(new Font("Times New Roman", 15));

		Label l4 = new Label("0");
		l4.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l4.setFont(new Font("Times New Roman", 15));

		ResultSet rs = appyQueryOnDataBase(
				"select count(*) from vehicles c where exists (select 1 from rent r where r.car_id = c.car_id);");
		try {
			rs.next();
			l2.setText(rs.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		ResultSet res = appyQueryOnDataBase(
				"select count(*) from vehicles c where not exists (select 1 from rent r where r.car_id = c.car_id);");
		try {
			res.next();
			l4.setText(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		VBox h2 = new VBox(15, l3, a2, l4);
		h2.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		h2.setPadding(new Insets(10));
		h2.setAlignment(Pos.CENTER);
		h2.setPrefSize(150, 150);
		icons(h2);
		butoonEffect(h2);

		ImageView a3 = new ImageView(new Image("flying-money.png"));
		a3.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		a3.setFitHeight(70);
		a3.setFitWidth(70);
		Label l5 = new Label("  Total Earnings ");
		l5.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l5.setFont(new Font("Times New Roman", 15));

		Label l6 = new Label("0");
		l6.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l6.setFont(new Font("Times New Roman", 15));

		ResultSet soso = appyQueryOnDataBase("select sum(price) from payment;");
		try {
			soso.next();
			if (soso.getString(1) != null)
				l6.setText(soso.getString(1));
			else
				l6.setText("0");

		} catch (SQLException e2) {
			error.setContentText(e2.getMessage());
			error.show();
		}

		VBox h3 = new VBox(15, l5, a3, l6);
		h3.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		h3.setPadding(new Insets(10));
		h3.setAlignment(Pos.CENTER);
		h3.setPrefSize(150, 150);
		icons(h3);
		butoonEffect(h3);

		ImageView a4 = new ImageView(new Image("cus.png"));
		a4.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		a4.setFitHeight(60);
		a4.setFitWidth(70);
		Label l7 = new Label("  Number of Customer ");
		l7.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l7.setFont(new Font("Times New Roman", 15));

		Label l8 = new Label("0");
		l8.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		l8.setFont(new Font("Times New Roman", 15));

		ResultSet resu = appyQueryOnDataBase("select count(*) from customers ;");
		try {
			resu.next();
			l8.setText(resu.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		VBox h5 = new VBox(15, l7, a4, l8);
		h5.setEffect(new DropShadow(5, Color.web("#d8d9e0")));
		h5.setPadding(new Insets(10));
		h5.setAlignment(Pos.CENTER);
		h5.setPrefSize(180, 150);
		icons(h5);
		butoonEffect(h5);

		HBox h4 = new HBox(35, h, h2, h3, h5);
		h4.setAlignment(Pos.CENTER);
		h4.setPadding(new Insets(10));
		h4.setMaxSize(900, 500);
		BorderPane pane2 = new BorderPane();

		VBox v = new VBox(20, h4);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(5));
		pane2.setCenter(v);

		h.setOnMouseClicked(e -> {
			ObservableList<RentCar> rentCarsList = tableRentCar().getItems();
			rentTable.setItems(rentCarsList);
			rentTable.setVisible(true);
			if (v.getChildren().size() > 1)
				v.getChildren().remove(1);
			v.getChildren().add(rentTable);
		});

		h2.setOnMouseClicked(e -> {
			ObservableList<NonRentCar> NonrentCarsList = tableNonRentCar().getItems();
			nonRentTable.setItems(NonrentCarsList);
			nonRentTable.setVisible(true);
			if (v.getChildren().size() > 1)
				v.getChildren().remove(1);
			v.getChildren().add(nonRentTable);
		});

		h3.setOnMouseClicked(e -> {
			ObservableList<RentalBill> BillList = tableBill().getItems();
			PaymentTable.setItems(BillList);
			PaymentTable.setVisible(true);
			if (v.getChildren().size() > 1)
				v.getChildren().remove(1);
			v.getChildren().add(PaymentTable);
		});

		h5.setOnMouseClicked(e -> {
			ObservableList<Customer> cus = tableCustomer().getItems();
			customerTable.setItems(cus);
			customerTable.setVisible(true);
			if (v.getChildren().size() > 1)
				v.getChildren().remove(1);
			v.getChildren().add(customerTable);

		});

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);

		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@SuppressWarnings("deprecation")
	private TableView<RentalBill> tableBill() {
		TableView<RentalBill> table = new TableView<RentalBill>();

		table.setEditable(false);

		TableColumn<RentalBill, Integer> CarId = new TableColumn<RentalBill, Integer>("Car ID");
		CarId.setMinWidth(200);
		CarId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCar_id()).asObject());
		CarId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentalBill, Integer> EmpId = new TableColumn<RentalBill, Integer>("Employee ID");
		EmpId.setMinWidth(200);
		EmpId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmp_id()).asObject());
		EmpId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentalBill, Integer> CusId = new TableColumn<RentalBill, Integer>("Customer ID");
		CusId.setMinWidth(200);
		CusId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCus_id()).asObject());
		CusId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentalBill, Integer> payid = new TableColumn<RentalBill, Integer>("Payment ID");
		payid.setMinWidth(200);
		payid.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getPay_id()).asObject());
		payid.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentalBill, String> payDate = new TableColumn<RentalBill, String>("Payment Date");
		payDate.setMinWidth(200);
		payDate.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPay_date() + ""));
		payDate.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentalBill, Double> price = new TableColumn<RentalBill, Double>("Price");
		price.setMinWidth(200);
		price.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getPrice()).asObject());
		price.setStyle("-fx-alignment: CENTER;");

		payments.clear();

		ResultSet rs = appyQueryOnDataBase(
				"select p.CAR_ID, v.EMP_ID,p.cus_id,p.pay_id,p.PAY_DATE,p.price from payment p, vehicles v where p.CAR_ID = v.CAR_ID order by pay_id;");

		try {
			if (rs != null)
				while (rs.next()) {
					String[] date = rs.getString(5).split("-");
					payments.add(new RentalBill(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
							Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4)),
							Double.parseDouble(rs.getString(6)), new Date(Integer.parseInt(date[0]) - 1900,
									Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]))));

				}
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		ObservableList<RentalBill> data = FXCollections.observableArrayList(payments);

		table.setItems(data);
		table.setMaxWidth(1250);
		table.setMinHeight(180);
		table.getColumns().addAll(CarId, EmpId, CusId, payid, payDate, price);
		return table;
	}

	private void IconedTextFieled(Node l, Node t) {
		l.setStyle("-fx-border-color: #d8d9e0;" + "-fx-font-size: 14;\n" + "-fx-border-width: 1;"
				+ "-fx-border-radius: 50;" + "-fx-font-weight: Bold;\n" + "-fx-background-color:#d8d9e0;"
				+ "-fx-background-radius: 50 0 0 50");

		t.setStyle("-fx-border-radius: 0 50 50 0;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n"
				+ "-fx-border-width:  3.5;" + "-fx-background-radius: 0 50 50 0");
	}

	private VBox menue(Stage primaryStage) {
		ImageView c = new ImageView(new Image("racing-car.png"));
		c.setFitHeight(50);
		c.setFitWidth(50);
		Button carButton = new Button("Car Buttons\nPage", c);
		carButton.setFont(new Font("Times New Roman", 13));
		carButton.setMinWidth(170);
		carButton.setMinHeight(100);
		butoonEffect(carButton);

		icons(carButton);
		carButton.setEffect(new DropShadow());
		carButton.setOnAction(e -> {
			carButtonAction(primaryStage);
		});
		ImageView cu = new ImageView(new Image("cus.png"));
		cu.setFitHeight(50);
		cu.setFitWidth(50);
		Button customerButton = new Button("Customer \nButtons Page", cu);
		customerButton.setFont(new Font("Times New Roman", 13));
		customerButton.setMinWidth(170);
		customerButton.setMinHeight(100);
		butoonEffect(customerButton);

		icons(customerButton);
		customerButton.setEffect(new DropShadow());
		customerButton.setOnAction(e -> {
			customerButtonAction(primaryStage);
		});

		ImageView em = new ImageView(new Image("emp.png"));
		em.setFitHeight(50);
		em.setFitWidth(50);
		Button employeeButton = new Button("Employee \nButtons Page", em);
		employeeButton.setFont(new Font("Times New Roman", 13));
		employeeButton.setMinWidth(170);
		employeeButton.setMinHeight(100);
		butoonEffect(employeeButton);

		icons(employeeButton);
		employeeButton.setEffect(new DropShadow());
		employeeButton.setOnAction(e -> {
			employeeButtonAction(primaryStage);
		});

		ImageView rn = new ImageView(new Image("rencar.png"));
		rn.setFitHeight(45);
		rn.setFitWidth(55);
		Button rentButton = new Button("Rent Buttons\nPage", rn);
		rentButton.setFont(new Font("Times New Roman", 13));
		rentButton.setMinWidth(170);
		rentButton.setMinHeight(100);
		butoonEffect(rentButton);

		icons(rentButton);
		rentButton.setEffect(new DropShadow());
		rentButton.setOnAction(e -> {
			rentButtonAction(primaryStage);
		});

		ImageView m = new ImageView(new Image("logout (1).png"));
		m.setFitHeight(60);
		m.setFitWidth(60);
		Button logout = new Button("Logout", m);
		logout.setFont(new Font("Times New Roman", 13));
		logout.setMinWidth(170);
		logout.setMinHeight(100);
		butoonEffect(logout);

		icons(logout);
		logout.setEffect(new DropShadow());
		logout.setOnAction(e -> {
			loginPage(primaryStage);
		});

		ImageView b = new ImageView(new Image("house.png"));
		b.setFitHeight(50);
		b.setFitWidth(50);
		Button back = new Button("Dashborde", b);
		back.setMinHeight(100);
		back.setMinWidth(180);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());
		back.setOnAction(e -> {
			Action(primaryStage);
		});

		VBox buttons;
		if (userLogin.equals("Basem"))
			buttons = new VBox(35, back, carButton, customerButton, employeeButton, rentButton, logout);
		else
			buttons = new VBox(35, back, carButton, customerButton, rentButton, logout);
		buttons.setAlignment(Pos.CENTER);
		buttons.setStyle("-fx-border-color: #d8d9e0;" + "-fx-background-color: transparent;");

		return buttons;

	}

	private void rentButtonAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		ImageView a = new ImageView(new Image("deal.png"));
		a.setFitHeight(110);
		a.setFitWidth(110);
		Button addCar = new Button("Rent Car", a);
		addCar.setFont(new Font("Times New Roman", 17));
		addCar.setPrefSize(300, 200);
		butoonEffect(addCar);

		icons(addCar);
		addCar.setEffect(new DropShadow());
		addCar.setOnAction(e -> {
			rentCarAction(primaryStage);
		});

		ImageView r = new ImageView(new Image("car-key.png"));
		r.setFitHeight(127);
		r.setFitWidth(140);
		Button removeCar = new Button("Return Car", r);
		removeCar.setFont(new Font("Times New Roman", 17));
		icons(removeCar);
		removeCar.setEffect(new DropShadow());
		removeCar.setPrefSize(300, 200);
		butoonEffect(removeCar);

		removeCar.setOnAction(e -> {
			returnRentCarAction(primaryStage);
		});

		HBox h = new HBox(60, addCar, removeCar);
		h.setAlignment(Pos.CENTER);

		pane2.setCenter(h);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void employeeButtonAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		ImageView a = new ImageView(new Image("addemp.png"));
		a.setFitHeight(150);
		a.setFitWidth(150);
		Button addEmployee = new Button("Add Employee", a);
		addEmployee.setFont(new Font("Times New Roman", 19));
		icons(addEmployee);
		addEmployee.setEffect(new DropShadow());
		addEmployee.setPrefSize(350, 200);
		butoonEffect(addEmployee);

		addEmployee.setOnAction(e -> {
			addEmloyeeAction(primaryStage);
		});

		ImageView r = new ImageView(new Image("rememp.png"));
		r.setFitHeight(150);
		r.setFitWidth(150);
		Button deleteEmployee = new Button("Delete Employee ", r);
		deleteEmployee.setFont(new Font("Times New Roman", 19));
		icons(deleteEmployee);
		deleteEmployee.setEffect(new DropShadow());
		deleteEmployee.setPrefSize(350, 200);
		butoonEffect(deleteEmployee);

		deleteEmployee.setOnAction(e -> {
			deleteEmployeeAction(primaryStage);
		});

		ImageView ed = new ImageView(new Image("editemp.png"));
		ed.setFitHeight(150);
		ed.setFitWidth(150);
		Button editEmployee = new Button("Edit Employee", ed);
		editEmployee.setFont(new Font("Times New Roman", 19));
		icons(editEmployee);
		editEmployee.setEffect(new DropShadow());
		editEmployee.setPrefSize(350, 200);
		butoonEffect(editEmployee);

		editEmployee.setOnAction(e -> {
			editEmloyeeAction(primaryStage);
		});
		ImageView f = new ImageView(new Image("Serchemp.png"));
		f.setFitHeight(150);
		f.setFitWidth(150);
		Button serchEmployee = new Button("Search Employee", f);
		serchEmployee.setFont(new Font("Times New Roman", 19));
		icons(serchEmployee);
		serchEmployee.setEffect(new DropShadow());
		serchEmployee.setPrefSize(350, 200);
		butoonEffect(serchEmployee);

		serchEmployee.setOnAction(e -> {
			serchEmployeeAction(primaryStage);
		});

		ImageView d = new ImageView(new Image("printemp.png"));
		d.setFitHeight(150);
		d.setFitWidth(150);
		Button displayEmployee = new Button("Display Employee", d);
		displayEmployee.setFont(new Font("Times New Roman", 19));
		icons(displayEmployee);
		displayEmployee.setEffect(new DropShadow());
		displayEmployee.setPrefSize(350, 200);
		butoonEffect(displayEmployee);

		displayEmployee.setOnAction(e -> {
			displayEmployeeAction(primaryStage);
		});

		HBox h2 = new HBox(50, serchEmployee, displayEmployee);
		h2.setAlignment(Pos.CENTER);

		HBox h = new HBox(60, addEmployee, deleteEmployee, editEmployee);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, h, h2);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void customerButtonAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		ImageView a = new ImageView(new Image("addcus.png"));
		a.setFitHeight(140);
		a.setFitWidth(140);
		Button addCustomer = new Button("Add Customer", a);
		addCustomer.setFont(new Font("Times New Roman", 17));
		icons(addCustomer);
		addCustomer.setEffect(new DropShadow());
		addCustomer.setPrefSize(300, 200);
		butoonEffect(addCustomer);

		addCustomer.setOnAction(e -> {
			addCustomerAction(primaryStage);
		});

		ImageView r = new ImageView(new Image("remcus.png"));
		r.setFitHeight(140);
		r.setFitWidth(140);
		Button deleteCustomer = new Button("Delete Customer ", r);
		deleteCustomer.setFont(new Font("Times New Roman", 17));
		icons(deleteCustomer);
		deleteCustomer.setEffect(new DropShadow());
		deleteCustomer.setPrefSize(300, 200);
		butoonEffect(deleteCustomer);

		deleteCustomer.setOnAction(e -> {
			deleteCustomer(primaryStage);
		});
		ImageView ed = new ImageView(new Image("editcus.png"));
		ed.setFitHeight(140);
		ed.setFitWidth(140);
		Button editCustomer = new Button("Edit Customer", ed);
		editCustomer.setFont(new Font("Times New Roman", 17));
		icons(editCustomer);
		editCustomer.setEffect(new DropShadow());
		editCustomer.setPrefSize(300, 200);
		butoonEffect(editCustomer);

		editCustomer.setOnAction(e -> {
			editCustomerAction(primaryStage);
		});
		ImageView f = new ImageView(new Image("serchcus.png"));
		f.setFitHeight(140);
		f.setFitWidth(140);
		Button serchCustomer = new Button("Search Customer", f);
		serchCustomer.setFont(new Font("Times New Roman", 17));
		icons(serchCustomer);
		serchCustomer.setEffect(new DropShadow());
		serchCustomer.setPrefSize(300, 200);
		butoonEffect(serchCustomer);

		serchCustomer.setOnAction(e -> {
			serchCustomerAction(primaryStage);
		});

		ImageView d = new ImageView(new Image("printcus.png"));
		d.setFitHeight(140);
		d.setFitWidth(140);
		Button displayCustomer = new Button("Display Customer", d);
		displayCustomer.setFont(new Font("Times New Roman", 17));
		icons(displayCustomer);
		displayCustomer.setEffect(new DropShadow());
		displayCustomer.setPrefSize(300, 200);
		butoonEffect(displayCustomer);

		displayCustomer.setOnAction(e -> {
			displayCustomerAction(primaryStage);
		});

		HBox h2 = new HBox(50, serchCustomer, displayCustomer);
		h2.setAlignment(Pos.CENTER);

		HBox h = new HBox(60, addCustomer, deleteCustomer, editCustomer);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, h, h2);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void carButtonAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		ImageView a = new ImageView(new Image("addcar1.png"));
		a.setFitHeight(132);
		a.setFitWidth(140);
		Button addCar = new Button("Add Car", a);
		addCar.setFont(new Font("Times New Roman", 17));
		addCar.setPrefSize(300, 200);
		butoonEffect(addCar);

		icons(addCar);
		addCar.setEffect(new DropShadow());
		addCar.setOnAction(e -> {
			addCarAction(primaryStage);
		});

		ImageView r = new ImageView(new Image("remcar.png"));
		r.setFitHeight(127);
		r.setFitWidth(140);
		Button removeCar = new Button("Remove Car", r);
		removeCar.setFont(new Font("Times New Roman", 17));
		icons(removeCar);
		removeCar.setEffect(new DropShadow());
		removeCar.setPrefSize(300, 200);
		butoonEffect(removeCar);

		removeCar.setOnAction(e -> {
			deleteCarAction(primaryStage);
		});

		ImageView ed = new ImageView(new Image("editcar.png"));
		ed.setFitHeight(127);
		ed.setFitWidth(140);
		Button editCarIfno = new Button("Edit Car Info", ed);
		editCarIfno.setFont(new Font("Times New Roman", 17));
		icons(editCarIfno);
		editCarIfno.setEffect(new DropShadow());
		editCarIfno.setPrefSize(300, 200);
		butoonEffect(editCarIfno);

		editCarIfno.setOnAction(e -> {
			editCarIfnoAction(primaryStage);
		});

		ImageView s = new ImageView(new Image("search2png.png"));
		s.setFitHeight(140);
		s.setFitWidth(140);
		Button SearchCars = new Button("Search Car", s);
		SearchCars.setFont(new Font("Times New Roman", 17));
		icons(SearchCars);
		SearchCars.setEffect(new DropShadow());
		SearchCars.setPrefSize(300, 200);
		butoonEffect(SearchCars);

		SearchCars.setOnAction(e -> {
			SearchCarsAction(primaryStage);
		});

		ImageView d = new ImageView(new Image("disCar.png"));
		d.setFitHeight(127);
		d.setFitWidth(140);
		Button displayCars = new Button("Display Cars", d);
		displayCars.setFont(new Font("Times New Roman", 17));
		icons(displayCars);
		displayCars.setEffect(new DropShadow());
		displayCars.setPrefSize(300, 200);
		butoonEffect(displayCars);

		displayCars.setOnAction(e -> {
			displayCarsAction(primaryStage);
		});

		HBox h2 = new HBox(50, SearchCars, displayCars);
		h2.setAlignment(Pos.CENTER);

		HBox h = new HBox(60, addCar, removeCar, editCarIfno);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, h, h2);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@SuppressWarnings("deprecation")
	private void displayEmployeeAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Display Employee");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		TableView<Employee> table = new TableView<Employee>();

		table.setEditable(false);

		TableColumn<Employee, Integer> empId = new TableColumn<Employee, Integer>("Employee ID");
		empId.setMinWidth(80);
		empId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpID()).asObject());
		empId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> empName = new TableColumn<Employee, String>("Employee Name");
		empName.setMinWidth(120);
		empName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
		empName.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> address = new TableColumn<Employee, String>("Address");
		address.setMinWidth(150);
		address.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAddress()));
		address.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> dateOFBirth = new TableColumn<Employee, String>("Date Of Birth");
		dateOFBirth.setMinWidth(170);
		dateOFBirth.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateOFBirth() + ""));
		dateOFBirth.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> startWork = new TableColumn<Employee, String>("Start work date");
		startWork.setMinWidth(170);
		startWork.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStartWork() + ""));
		startWork.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> baseSalary = new TableColumn<Employee, Integer>("Base Salary");
		baseSalary.setMinWidth(150);
		baseSalary.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getBaseSalary()).asObject());
		baseSalary.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Double> comissionRate = new TableColumn<Employee, Double>("Comission Rate");
		comissionRate.setMinWidth(130);
		comissionRate.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getComissionRate()).asObject());
		comissionRate.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> yearlySales = new TableColumn<Employee, Integer>("Yearly Sales");
		yearlySales.setMinWidth(130);
		yearlySales.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getYearlySales()).asObject());
		yearlySales.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> cusPhnum = new TableColumn<Employee, Integer>("Customer Phone Numbers");
		cusPhnum.setMinWidth(160);
		cusPhnum.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpPnum()).asObject());
		cusPhnum.setStyle("-fx-alignment: CENTER;");

		employee.clear();
		ResultSet rs = appyQueryOnDataBase("select * from RENTING_EMP;");
		try {
			while (rs.next()) {
				String[] birthDate = rs.getString(5).split("-");
				String[] startDate = rs.getString(6).split("-");
				employee.add(new Employee(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
						Integer.parseInt(rs.getString(4)),
						new Date(Integer.parseInt(birthDate[0]) - 1900, Integer.parseInt(birthDate[1]) - 1,
								Integer.parseInt(birthDate[2])),
						new Date(Integer.parseInt(startDate[0]) - 1900, Integer.parseInt(startDate[1]) - 1,
								Integer.parseInt(startDate[2])),
						(int) Double.parseDouble(rs.getString(7)), Double.parseDouble(rs.getString(8)),
						(int) Double.parseDouble(rs.getString(9))));
			}
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		ObservableList<Employee> data = FXCollections.observableArrayList(employee);

		table.setItems(data);
		table.setMaxWidth(1300);
		table.setMinHeight(500);
		table.getColumns().addAll(empId, empName, address, cusPhnum, dateOFBirth, startWork, baseSalary, comissionRate,
				yearlySales);

		ImageView b = new ImageView(new Image("backemp.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back to\nemployee \npage", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			employeeButtonAction(primaryStage);
		});

		HBox h = new HBox(50, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(25, table, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@SuppressWarnings("deprecation")
	private void serchEmployeeAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Search Employee");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label empName = new Label("Employee Name:");
		empName.setPadding(new Insets(7));
		empName.setMinWidth(130);
		TextField empNamet = new TextField();
		empNamet.setMinWidth(200);
		IconedTextFieled(empName, empNamet);
		HBox h1 = new HBox(empName, empNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(h1);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(10));

		TableView<Employee> table = new TableView<Employee>();

		table.setEditable(false);

		TableColumn<Employee, Integer> empId = new TableColumn<Employee, Integer>("Employee ID");
		empId.setMinWidth(80);
		empId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpID()).asObject());
		empId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> empNames = new TableColumn<Employee, String>("Employee Name");
		empNames.setMinWidth(120);
		empNames.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
		empNames.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> address = new TableColumn<Employee, String>("Address");
		address.setMinWidth(150);
		address.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAddress()));
		address.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> dateOFBirth = new TableColumn<Employee, String>("Date Of Birth");
		dateOFBirth.setMinWidth(170);
		dateOFBirth.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateOFBirth() + ""));
		dateOFBirth.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, String> startWork = new TableColumn<Employee, String>("Start work date");
		startWork.setMinWidth(170);
		startWork.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStartWork() + ""));
		startWork.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> baseSalary = new TableColumn<Employee, Integer>("Base Salary");
		baseSalary.setMinWidth(150);
		baseSalary.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getBaseSalary()).asObject());
		baseSalary.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Double> comissionRate = new TableColumn<Employee, Double>("Comission Rate");
		comissionRate.setMinWidth(130);
		comissionRate.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getComissionRate()).asObject());
		comissionRate.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> yearlySales = new TableColumn<Employee, Integer>("Yearly Sales");
		yearlySales.setMinWidth(130);
		yearlySales.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getYearlySales()).asObject());
		yearlySales.setStyle("-fx-alignment: CENTER;");

		TableColumn<Employee, Integer> cusPhnum = new TableColumn<Employee, Integer>("Customer Phone Numbers");
		cusPhnum.setMinWidth(160);
		cusPhnum.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpPnum()).asObject());
		cusPhnum.setStyle("-fx-alignment: CENTER;");

		ObservableList<Employee> data = FXCollections.observableArrayList();

		table.setItems(data);
		table.setMaxWidth(1300);
		table.setMinHeight(450);
		table.getColumns().addAll(empId, empNames, address, cusPhnum, dateOFBirth, startWork, baseSalary, comissionRate,
				yearlySales);

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		empNamet.setOnKeyTyped(e -> {
			data.clear();
			ResultSet rs = appyQueryOnDataBase(
					"select * from RENTING_EMP e where e.NAME Like '" + empNamet.getText() + "%';");
			try {
				while (rs.next()) {
					String[] birthDate = rs.getString(5).split("-");
					String[] startDate = rs.getString(6).split("-");
					data.add(new Employee(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
							Integer.parseInt(rs.getString(4)),
							new Date(Integer.parseInt(birthDate[0]) - 1900, Integer.parseInt(birthDate[1]) - 1,
									Integer.parseInt(birthDate[2])),
							new Date(Integer.parseInt(startDate[0]) - 1900, Integer.parseInt(startDate[1]) - 1,
									Integer.parseInt(startDate[2])),
							(int) Double.parseDouble(rs.getString(7)), Double.parseDouble(rs.getString(8)),
							(int) Double.parseDouble(rs.getString(9))));
				}
			} catch (NumberFormatException | SQLException e1) {

				error.setContentText(e1.getMessage());
				error.show();
			}
		});

		ImageView b = new ImageView(new Image("backemp.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			employeeButtonAction(primaryStage);
		});

		HBox h = new HBox(60, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(30, mix, table, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void displayCustomerAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Display Customers");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		TableView<Customer> table = new TableView<Customer>();

		table.setEditable(false);

		TableColumn<Customer, Integer> CusId = new TableColumn<Customer, Integer>("Customer ID");
		CusId.setMinWidth(150);
		CusId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCusId()).asObject());
		CusId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> cusName = new TableColumn<Customer, String>("Customer Name");
		cusName.setMinWidth(160);
		cusName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCusName()));
		cusName.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> address = new TableColumn<Customer, String>("Address");
		address.setMinWidth(160);
		address.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAddress()));
		address.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> carrier = new TableColumn<Customer, String>("Carrier");
		carrier.setMinWidth(100);
		carrier.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarrier()));
		carrier.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> budget = new TableColumn<Customer, Integer>("Budget");
		budget.setMinWidth(200);
		budget.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getBudget()).asObject());
		budget.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> empId = new TableColumn<Customer, Integer>("Employee ID");
		empId.setMinWidth(160);
		empId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpId()).asObject());
		empId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> cusPnum = new TableColumn<Customer, Integer>("Customer Phone Numbers");
		cusPnum.setMinWidth(180);
		cusPnum.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCusPnum()).asObject());
		cusPnum.setStyle("-fx-alignment: CENTER;");

		customers.clear();
		ResultSet rs = appyQueryOnDataBase("select * from customers;");

		try {
			while (rs.next()) {
				customers.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
						Integer.parseInt(rs.getString(4)), rs.getString(5), Integer.parseInt(rs.getString(6)),
						Integer.parseInt(rs.getString(7))));
			}
		} catch (NumberFormatException | SQLException e1) {

			error.setContentText(e1.getMessage());
			error.show();
		}
		ObservableList<Customer> data = FXCollections.observableArrayList(customers);

		table.setItems(data);
		table.setMaxWidth(1150);
		table.setMinHeight(500);
		table.getColumns().addAll(CusId, cusName, address, budget, carrier, empId, cusPnum);

		ImageView b = new ImageView(new Image("backcus.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back to\n customer\n page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			customerButtonAction(primaryStage);
		});

		HBox h = new HBox(50, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(25, table, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void serchCustomerAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Search Customer");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label cusName = new Label("Customer Name: ");
		cusName.setPadding(new Insets(7));
		cusName.setMinWidth(130);
		TextField cusNamet = new TextField();
		cusNamet.setMinWidth(200);
		IconedTextFieled(cusName, cusNamet);
		HBox h1 = new HBox(cusName, cusNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee name :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		TextField empIdt = new TextField();
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		HBox v = new HBox(15, h1, h7);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(20));

		TableView<Customer> table = tableCustomer();
		table.getItems().clear();
		table.setMaxWidth(1300);
		table.setMinHeight(400);

		VBox mix = new VBox(15, v, table);
		mix.setAlignment(Pos.CENTER);

		cusNamet.setOnKeyTyped(e -> {
			table.getItems().clear();
			if (!empIdt.getText().isBlank() && cusNamet.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.emp_id in(select emp_id from RENTING_EMP where name like '"
								+ empIdt.getText() + "%');");

				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			} else if (!cusNamet.getText().isBlank() && empIdt.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.CUS_NAME Like '" + cusNamet.getText() + "%';");
				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			}

			if (!cusNamet.getText().isBlank() && !empIdt.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.emp_id in(select emp_id from RENTING_EMP where name like '"
								+ empIdt.getText() + "%') and c.CUS_NAME Like '" + cusNamet.getText() + "%' ;");
				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			}

		});

		empIdt.setOnKeyTyped(e -> {
			table.getItems().clear();
			if (!empIdt.getText().isBlank() && cusNamet.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.emp_id in(select emp_id from RENTING_EMP where name like '"
								+ empIdt.getText() + "%');");

				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			} else if (!cusNamet.getText().isBlank() && empIdt.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.CUS_NAME Like '" + cusNamet.getText() + "%';");
				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			}

			if (!cusNamet.getText().isBlank() && !empIdt.getText().isBlank()) {
				ResultSet rs = appyQueryOnDataBase(
						"select * from customers c where c.emp_id in(select emp_id from RENTING_EMP where name like '"
								+ empIdt.getText() + "%') and c.CUS_NAME Like '" + cusNamet.getText() + "%' ;");
				try {
					while (rs.next()) {
						table.getItems()
								.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
										Integer.parseInt(rs.getString(4)), rs.getString(5),
										Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7))));
					}
				} catch (NumberFormatException | SQLException e1) {

					error.setContentText(e1.getMessage());
					error.show();
				}
			}

		});

		ImageView b = new ImageView(new Image("backcus.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			customerButtonAction(primaryStage);
		});

		HBox control = new HBox(20, back);
		control.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(20, mix, control);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void SearchCarsAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Search Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label options = new Label("Options: ");
		options.setPadding(new Insets(7));
		options.setMinWidth(180);
		TextField optionst = new TextField();
		optionst.setMinWidth(100);
		IconedTextFieled(options, optionst);
		HBox h3 = new HBox(options, optionst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label carBrand = new Label("Car Brand :         ");
		carBrand.setPadding(new Insets(7));
		carBrand.setMinWidth(180);
		TextField carBrandt = new TextField();
		carBrandt.setMinWidth(100);
		IconedTextFieled(carBrand, carBrandt);
		HBox h4 = new HBox(carBrand, carBrandt);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carModel = new Label("Car Model :        ");
		carModel.setPadding(new Insets(7));
		carModel.setMinWidth(180);
		TextField carModelt = new TextField();
		carModelt.setMinWidth(100);
		IconedTextFieled(carModel, carModelt);
		HBox h5 = new HBox(carModel, carModelt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label carColor = new Label("Car Color :   ");
		carColor.setPadding(new Insets(7));
		carColor.setMinWidth(180);
		TextField carColort = new TextField();
		carColort.setMinWidth(100);
		IconedTextFieled(carColor, carColort);
		HBox h9 = new HBox(carColor, carColort);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label transmissionType = new Label("Transmission Type : ");
		transmissionType.setPadding(new Insets(7));
		transmissionType.setMinWidth(180);

		RadioButton gear = new RadioButton("Gear");
		gear.setPadding(new Insets(5));
		RadioButton Automatic = new RadioButton("Automatic");
		Automatic.setPadding(new Insets(5));
		ToggleGroup tranG = new ToggleGroup();
		gear.setToggleGroup(tranG);
		Automatic.setToggleGroup(tranG);
		gear.setFont(new Font("Times New Roman", 14));
		Automatic.setFont(new Font("Times New Roman", 14));
		HBox tBox = new HBox(50, gear, Automatic);
		tBox.setMinWidth(100);
		tBox.setAlignment(Pos.CENTER);

		IconedTextFieled(transmissionType, tBox);
		HBox h8 = new HBox(transmissionType, tBox);
		h8.setAlignment(Pos.CENTER_LEFT);

		Label numOfPassengers = new Label("Number of Passengers : ");
		numOfPassengers.setPadding(new Insets(7));
		numOfPassengers.setMinWidth(180);
		ComboBox<String> numOfPassengerst = new ComboBox<String>();
		numOfPassengerst.getItems().addAll("2", "4", "5", "7", "8");
		numOfPassengerst.setMinWidth(200);
		IconedTextFieled(numOfPassengers, numOfPassengerst);
		HBox h6 = new HBox(numOfPassengers, numOfPassengerst);
		h6.setAlignment(Pos.CENTER_LEFT);

		Label numOfDoors = new Label("Num Of Doors : ");
		numOfDoors.setPadding(new Insets(7));
		numOfDoors.setMinWidth(180);
		ComboBox<String> numOfDoorst = new ComboBox<String>();
		numOfDoorst.getItems().addAll("2", "4");
		numOfDoorst.setMinWidth(200);
		IconedTextFieled(numOfDoors, numOfDoorst);
		HBox h10 = new HBox(numOfDoors, numOfDoorst);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label petrolType = new Label("Petrol Type : ");
		petrolType.setPadding(new Insets(7));
		petrolType.setMinWidth(180);
		RadioButton disel = new RadioButton("Disel");
		disel.setPadding(new Insets(5));
		RadioButton petrol = new RadioButton("Petrol");
		petrol.setPadding(new Insets(5));
		ToggleGroup petrolG = new ToggleGroup();
		disel.setToggleGroup(petrolG);
		petrol.setToggleGroup(petrolG);
		disel.setFont(new Font("Times New Roman", 14));
		petrol.setFont(new Font("Times New Roman", 14));
		HBox pBox = new HBox(50, disel, petrol);
		pBox.setMinWidth(100);
		pBox.setAlignment(Pos.CENTER);

		IconedTextFieled(petrolType, pBox);
		HBox h11 = new HBox(petrolType, pBox);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label rentPrice = new Label("Max rent Price : ");
		rentPrice.setPadding(new Insets(7));
		rentPrice.setMinWidth(180);
		TextField rentPricet = new TextField();
		rentPricet.setMinWidth(100);
		IconedTextFieled(rentPrice, rentPricet);
		HBox h13 = new HBox(rentPrice, rentPricet);
		h13.setAlignment(Pos.CENTER_LEFT);

		Label year = new Label("Year : ");
		year.setPadding(new Insets(7));
		year.setMinWidth(180);
		TextField yeart = new TextField();
		yeart.setMinWidth(100);
		IconedTextFieled(year, yeart);
		HBox h14 = new HBox(year, yeart);
		h14.setAlignment(Pos.CENTER_LEFT);

		HBox h15 = new HBox(15, h4, h5, h9);
		h15.setAlignment(Pos.CENTER);

		HBox h16 = new HBox(15, h14, h13, h3);
		h16.setAlignment(Pos.CENTER);

		HBox h18 = new HBox(15, h6, h10);
		h18.setAlignment(Pos.CENTER);

		HBox h19 = new HBox(15, h11, h8);
		h19.setAlignment(Pos.CENTER);

		TableView<NonRentCar> nonRentTable = tableNonRentCar();
		nonRentTable.setMaxHeight(300);

		VBox mix = new VBox(10, h15, h16, h18, h19);
		mix.setAlignment(Pos.CENTER);

		VBox mix2 = new VBox(20, mix, nonRentTable);
		mix2.setAlignment(Pos.CENTER);

		ImageView b = new ImageView(new Image("back.png"));
		b.setFitHeight(67);
		b.setFitWidth(80);
		Button back = new Button("Back to\n car page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			carButtonAction(primaryStage);
		});

		carBrandt.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		Automatic.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		gear.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		petrol.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		disel.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		carColort.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		carColort.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		carModelt.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		numOfDoorst.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		numOfPassengerst.setOnAction(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		optionst.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		rentPricet.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		yeart.setOnKeyTyped(e -> {
			if (((RadioButton) (petrolG.getSelectedToggle())) != null
					&& ((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(),
						((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (tranG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", ((RadioButton) (tranG.getSelectedToggle())).getText(), nonRentTable);
			else if (((RadioButton) (petrolG.getSelectedToggle())) != null)
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						((RadioButton) (petrolG.getSelectedToggle())).getText(), "", nonRentTable);
			else
				searchCarEvent(carBrandt.getText(), carModelt.getText(), carColort.getText(), yeart.getText(),
						rentPricet.getText(), optionst.getText(), numOfPassengerst.getValue(), numOfDoorst.getValue(),
						"", "", nonRentTable);
		});

		HBox h = new HBox(50, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(20, mix2, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void searchCarEvent(String brand, String model, String color, String year, String rentPrice, String options,
			String numOfPass, String numOfDoor, String petrolType, String transtype,
			TableView<NonRentCar> nonRentTable) {
		if (numOfDoor == null)
			numOfDoor = "";
		if (numOfPass == null)
			numOfPass = "";

		if (petrolType.length() > 0)
			petrolType = petrolType.charAt(0) + "";

		if (transtype.length() > 0)
			transtype = transtype.charAt(0) + "";

		if (rentPrice.length() == 0)
			rentPrice = "100000000";

		nonRentVehicles.clear();
		ResultSet rs = appyQueryOnDataBase("select c.* from vehicles c where options like '" + options
				+ "%' and TRANSMISSION_TYPE like '" + transtype + "%' and NUM_OF_PASS like '" + numOfPass
				+ "%' and PETROL_TYPE like '" + petrolType + "%' and CAR_MODEL like '" + model
				+ "%' and CAR_COLOR like '" + color + "%' and NUM_OF_DOORS like '" + numOfDoor
				+ "%'  and CAR_BRAND like '" + brand + "%' and RENT_PRICE < " + rentPrice + " and year like '" + year
				+ "%' and c.car_id not in (select car_id from rent);");

		try {
			if (rs != null)
				while (rs.next()) {

					nonRentVehicles.add(new NonRentCar(Integer.parseInt(rs.getString(1)), rs.getString(2),
							Integer.parseInt(rs.getString(3)), (rs.getString(4)).charAt(0),
							Integer.parseInt(rs.getString(5)), (rs.getString(6)).charAt(0), rs.getString(7),
							rs.getString(8), Integer.parseInt(rs.getString(9)), rs.getString(10),
							Integer.parseInt(rs.getString(11)), Integer.parseInt(rs.getString(12)),
							Integer.parseInt(rs.getString(13))));

				}

			ObservableList<NonRentCar> data = FXCollections.observableArrayList(nonRentVehicles);

			nonRentTable.setItems(data);
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
	}

	private void displayCarsAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("List of Cars");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		ImageView b = new ImageView(new Image("back.png"));
		b.setFitHeight(67);
		b.setFitWidth(80);
		Button back = new Button("Back to\n car page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			carButtonAction(primaryStage);
		});

		HBox h = new HBox(50, back);
		h.setAlignment(Pos.CENTER);

		TableView<RentCar> rentTable = tableRentCar();
		TableView<NonRentCar> nonRentTable = tableNonRentCar();

		Label rentTablet = new Label("Rent Cars");
		rentTablet
				.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");
		VBox v = new VBox(5, rentTablet, rentTable);
		v.setAlignment(Pos.CENTER);

		Label nonRentTablet = new Label("Non Rent Cars");
		nonRentTablet
				.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");

		VBox v2 = new VBox(5, nonRentTablet, nonRentTable);
		v2.setAlignment(Pos.CENTER);

		rentTable.setMaxHeight(160);
		nonRentTable.setMaxHeight(160);

		VBox v1 = new VBox(25, rentTable, nonRentTable, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void deleteCustomer(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Remove Customer");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label cusName = new Label("Customer Name: ");
		cusName.setPadding(new Insets(7));
		cusName.setMinWidth(130);
		TextField cusNamet = new TextField();
		cusNamet.setEditable(false);
		cusNamet.setMinWidth(200);
		IconedTextFieled(cusName, cusNamet);
		HBox h1 = new HBox(cusName, cusNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label CusId = new Label("Customer ID:     ");
		CusId.setMinWidth(130);
		CusId.setPadding(new Insets(7));
		ComboBox<String> CusIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select cus_id from customers;");
		try {

			while (res.next())
				CusIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		CusIdt.setMinWidth(200);
		IconedTextFieled(CusId, CusIdt);
		HBox h2 = new HBox(CusId, CusIdt);
		h2.setAlignment(Pos.CENTER_LEFT);

		Label CusAdress = new Label("Customer Adress: ");
		CusAdress.setPadding(new Insets(7));
		CusAdress.setMinWidth(130);
		TextField CusAdresst = new TextField();
		CusAdresst.setEditable(false);
		CusAdresst.setMinWidth(200);
		IconedTextFieled(CusAdress, CusAdresst);
		HBox h3 = new HBox(CusAdress, CusAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label budget = new Label("Budget :         ");
		budget.setPadding(new Insets(7));
		budget.setMinWidth(130);
		TextField budgett = new TextField();
		budgett.setEditable(false);
		budgett.setMinWidth(200);
		IconedTextFieled(budget, budgett);
		HBox h4 = new HBox(budget, budgett);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carere = new Label("Carrier :        ");
		carere.setPadding(new Insets(7));
		carere.setMinWidth(130);
		TextField careret = new TextField();
		careret.setEditable(false);
		careret.setMinWidth(200);
		IconedTextFieled(carere, careret);
		HBox h5 = new HBox(carere, careret);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setEditable(false);
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		TextField empIdt = new TextField();
		empIdt.setEditable(false);
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h2, h1, h3, h4, h5, h9, h7);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView r = new ImageView(new Image("remcus.png"));
		r.setFitHeight(80);
		r.setFitWidth(80);
		Button remove = new Button("Remove Customer", r);
		remove.setPrefSize(250, 100);
		icons(remove);
		butoonEffect(remove);
		remove.setEffect(new DropShadow());

		remove.setOnAction(e -> {
			try {
				applyOnDataBase("delete from customers where cus_id =" + CusIdt.getValue() + ";");

			} catch (SQLException e1) {

				error.setContentText(e1.getMessage());
				error.show();
			}
		});

		CusIdt.setOnAction(e -> {
			try {
				ResultSet rs = appyQueryOnDataBase("select * from customers where cus_ID =" + CusIdt.getValue() + ";");
				rs.next();

				cusNamet.setText(rs.getString(1));
				CusAdresst.setText(rs.getString(2));
				budgett.setText(rs.getString(4));
				careret.setText(rs.getString(5));
				phNumt.setText(rs.getString(6));
				empIdt.setText(rs.getString(7));

				remove.setDisable(false);

			} catch (Exception e2) {
				error.setContentText("Error in select !!");
				error.show();
			}
		});

		ImageView b = new ImageView(new Image("backcus.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			customerButtonAction(primaryStage);
		});

		HBox h = new HBox(60, remove, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@SuppressWarnings("deprecation")
	private void returnRentCarAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Return Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		ComboBox<String> carids = new ComboBox<>();
		carids.setMinWidth(350);
		carids.setStyle("-fx-font-size: 14;");
		carids.setPromptText("Choice Car ");
		carids.getItems().addAll();

		Label car = new Label("Car: ");
		car.setPadding(new Insets(7));
		car.setMinWidth(130);
		IconedTextFieled(car, carids);
		HBox h7 = new HBox(car, carids);
		h7.setAlignment(Pos.CENTER);
		ResultSet res = appyQueryOnDataBase(
				"select concat(CAR_BRAND,' ' ,CAR_MODEL,', Price: ',RENT_PRICE,', ID: ',CAR_ID) from VEHICLES c where exists (select 1 from rent r where r.car_id = c.car_id);");
		try {
			while (res.next())
				carids.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		Label priceToPay = new Label("Amount to pay: ");
		priceToPay.setPadding(new Insets(7));
		priceToPay.setMinWidth(130);
		TextField priceToPayt = new TextField();
		priceToPayt.setEditable(false);
		priceToPayt.setMinWidth(350);
		priceToPayt.setStyle("-fx-font-size: 14;");
		IconedTextFieled(priceToPay, priceToPayt);
		HBox h8 = new HBox(priceToPay, priceToPayt);
		h8.setAlignment(Pos.CENTER);

		Label delayDate = new Label("Return Date :");
		delayDate.setPadding(new Insets(7));
		delayDate.setMinWidth(130);
		DatePicker delayDatet = new DatePicker(LocalDate.now());
		delayDatet.setEditable(false);
		delayDatet.setMinWidth(350);
		IconedTextFieled(delayDate, delayDatet);
		HBox h4 = new HBox(delayDate, delayDatet);
		h4.setAlignment(Pos.CENTER);

		Label price = new Label("Amount paid: ");
		price.setPadding(new Insets(7));
		price.setMinWidth(130);
		TextField pricet = new TextField();
		pricet.setDisable(true);
		pricet.setMinWidth(350);
		pricet.setStyle("-fx-font-size: 14;");
		IconedTextFieled(price, pricet);
		HBox h9 = new HBox(price, pricet);
		h9.setAlignment(Pos.CENTER);

		carids.setOnAction(e -> {
			try {
				if (carids.getValue() != null) {
					String[] carTokens = carids.getValue().split("[:,]");
					String carId = carTokens[4].trim();
					ResultSet resu = appyQueryOnDataBase("select price from rent where car_id = " + carId + ";");
					resu.next();
					priceToPayt.setText(resu.getString(1));
					pricet.setDisable(false);
				}
			} catch (Exception e1) {
				error.setContentText(e1.getMessage());
				error.show();
			}

		});

		delayDatet.setOnAction(e -> {
			String[] carTokens = carids.getValue().split("[:,]");
			String carId = carTokens[4].trim();
			ResultSet resu = appyQueryOnDataBase(
					"select r.rent_date, r.return_date,v.RENT_PRICE from rent r, VEHICLES v where r.car_id = " + carId
							+ " and v.car_id = r.car_id;");
			try {
				resu.next();
				String[] date = resu.getString(1).split("-");
				Date rentDate = new Date(Integer.parseInt(date[0]) - 1900, Integer.parseInt(date[1]) - 1,
						Integer.parseInt(date[2]));
				long rent = rentDate.getTime();
				long rentDay = rent / 86400000;
				long delayDay = delayDatet.getValue().toEpochDay();
				Long result = delayDay - rentDay;

				String[] returnD = resu.getString(2).split("-");

				Date returnDate = new Date(Integer.parseInt(returnD[0]) - 1900, Integer.parseInt(returnD[1]) - 1,
						Integer.parseInt(returnD[2]));
				long returnM = returnDate.getTime();
				long returnDay = returnM / 86400000;

				Long must = returnDay - rentDay;

				if (result > must)
					priceToPayt.setText((result * Integer.parseInt(resu.getString(3))) + "");

			} catch (SQLException e1) {
				error.setContentText(e1.getMessage());
				error.show();
			}
		});

		Label rentTable = new Label("Rent Cars");
		rentTable.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");
		TableView<RentCar> rentCars = tableRentCar();
		VBox v = new VBox(5, rentTable, rentCars);
		v.setAlignment(Pos.CENTER);

		Label nonRentTable = new Label("Non Rent Cars");
		nonRentTable
				.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");
		TableView<NonRentCar> NonrentCars = tableNonRentCar();
		VBox v1 = new VBox(5, nonRentTable, NonrentCars);
		v1.setAlignment(Pos.CENTER);

		VBox v2 = new VBox(5, h7, h4, h8, h9);
		v2.setAlignment(Pos.CENTER);

		VBox v3 = new VBox(20, v2, v, v1);
		v3.setAlignment(Pos.CENTER);
		ImageView r = new ImageView(new Image("rencar.png"));
		r.setFitHeight(80);
		r.setFitWidth(80);
		Button cancelRent = new Button("Return Car", r);
		cancelRent.setPrefSize(250, 100);
		icons(cancelRent);
		butoonEffect(cancelRent);
		cancelRent.setEffect(new DropShadow());

		cancelRent.setOnAction(ee -> {
			try {
				double toPay = Double.parseDouble(priceToPayt.getText());
				double paid = Double.parseDouble(pricet.getText());

				String[] carTokens = carids.getValue().split("[:,]");
				String carId = carTokens[4].trim();

				ResultSet s = appyQueryOnDataBase("select cus_id from rent where car_id = " + carId + ";");
				s.next();

				if (paid - toPay >= 0) {
					applyOnDataBase("delete from rent where car_id = " + carId + ";");
					applyOnDataBase("insert into payment(CAR_ID ,cus_id ,PAY_DATE ,price) value (" + carId + ", "
							+ s.getString(1) + ",'" + LocalDate.now() + "'," + paid + ");");
					pricet.setText("");
					priceToPayt.setText("");

					carids.getItems().clear();

					ResultSet resu = appyQueryOnDataBase(
							"select concat(CAR_BRAND,' ' ,CAR_MODEL,', Price: ',RENT_PRICE,', ID: ',CAR_ID) from VEHICLES c where exists (select 1 from rent r where r.car_id = c.car_id);");
					try {
						while (resu.next())
							carids.getItems().add(resu.getString(1));
					} catch (SQLException e1) {
						error.setContentText(e1.getMessage());
						error.show();
					}

				} else {
					pricet.setText("");
					error.setContentText("Customer must pay full amount");
					error.show();
				}

			} catch (SQLException e1) {
				error.setContentText(e1.getMessage());
				error.show();
			}

			ObservableList<RentCar> rentCarsList = tableRentCar().getItems();
			ObservableList<NonRentCar> NonrentCarsList = tableNonRentCar().getItems();
			rentCars.setItems(rentCarsList);
			NonrentCars.setItems(NonrentCarsList);
		});

		ImageView b = new ImageView(new Image("left-arrow.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			rentButtonAction(primaryStage);
		});

		HBox hbu = new HBox(20, cancelRent, back);
		hbu.setAlignment(Pos.CENTER);
		hbu.setPadding(new Insets(10));

		pane2.setBottom(hbu);
		BorderPane.setAlignment(back, Pos.CENTER);
		pane2.setPadding(new Insets(10));

		pane2.setCenter(v3);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void rentCarAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Rent Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		ComboBox<String> carids = new ComboBox<>();
		carids.setMinWidth(350);
		carids.setStyle("-fx-font-size: 14;");
		carids.setPromptText("Choice Car");
		carids.getItems().addAll();

		Label car = new Label("Car Name : ");
		car.setPadding(new Insets(7));
		car.setMinWidth(130);
		IconedTextFieled(car, carids);
		HBox h7 = new HBox(car, carids);
		h7.setAlignment(Pos.CENTER);
		ResultSet res = appyQueryOnDataBase(
				"select concat(CAR_BRAND,' ' ,CAR_MODEL,', Price: ',RENT_PRICE,', ID: ',CAR_ID) "
						+ "from VEHICLES c where not exists (select 1 from rent r where r.car_id = c.car_id);");
		try {

			while (res.next())
				carids.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		ComboBox<String> cusid = new ComboBox<>();
		cusid.setMinWidth(350);
		cusid.setStyle("-fx-font-size: 14;");
		cusid.setPromptText("Choice Customer");
		cusid.getItems().addAll();
		ResultSet res1 = appyQueryOnDataBase(
				"select concat(CUS_NAME, ', Phone: ', CUS_PNUM,', ID: ', CUS_ID) from CUSTOMERS ");
		try {

			while (res1.next())
				cusid.getItems().add(res1.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		Label cus = new Label("Customer ID : ");
		cus.setPadding(new Insets(7));
		cus.setMinWidth(130);
		IconedTextFieled(cus, cusid);
		HBox h8 = new HBox(cus, cusid);
		h8.setAlignment(Pos.CENTER);

		Label rentTable = new Label("Rent Cars");
		rentTable.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");
		TableView<RentCar> rentCars = tableRentCar();
		VBox v = new VBox(5, rentTable, rentCars);
		v.setAlignment(Pos.CENTER);

		Label nonRentTable = new Label("Non Rent Cars");
		nonRentTable
				.setStyle("-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n");
		TableView<NonRentCar> NonrentCars = tableNonRentCar();
		VBox v1 = new VBox(5, nonRentTable, NonrentCars);
		v1.setAlignment(Pos.CENTER);

		Label rentDate = new Label("Date of rent :");
		rentDate.setPadding(new Insets(7));
		rentDate.setMinWidth(130);
		DatePicker rentDatet = new DatePicker(LocalDate.now());
		rentDatet.setEditable(false);
		rentDatet.setMinWidth(200);
		IconedTextFieled(rentDate, rentDatet);
		HBox h4 = new HBox(rentDate, rentDatet);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label returnedDate = new Label("Date returned :");
		returnedDate.setPadding(new Insets(7));
		returnedDate.setMinWidth(130);
		DatePicker returnedDatet = new DatePicker();
		returnedDatet.setEditable(false);
		returnedDatet.setMinWidth(200);
		IconedTextFieled(returnedDate, returnedDatet);
		HBox h5 = new HBox(returnedDate, returnedDatet);
		h5.setAlignment(Pos.CENTER_LEFT);

		VBox v5 = new VBox(10, h4, h5);
		v5.setAlignment(Pos.CENTER);

		VBox v2 = new VBox(10, h7, h8);
		v2.setAlignment(Pos.CENTER);

		HBox mix = new HBox(15, v2, v5);
		mix.setAlignment(Pos.CENTER);

		VBox v3 = new VBox(20, mix, v, v1);
		v3.setAlignment(Pos.CENTER);
		ImageView r = new ImageView(new Image("deal.png"));
		r.setFitHeight(80);
		r.setFitWidth(80);
		Button rent = new Button("Rent Car", r);
		rent.setPrefSize(250, 100);
		icons(rent);
		butoonEffect(rent);
		rent.setEffect(new DropShadow());

		rent.setOnAction(ee -> {
			try {
				String[] carTokens = carids.getValue().split("[:,]");
				double price = Double.parseDouble(carTokens[2].trim());

				String carId = carTokens[4].trim();
				long rentDay = rentDatet.getValue().toEpochDay();
				long returnDay = returnedDatet.getValue().toEpochDay();
				Long result = returnDay - rentDay;

				ResultSet roro = appyQueryOnDataBase("select emp_id from VEHICLES where car_id = " + carId + ";");
				roro.next();
				String empId = roro.getString(1);

				String[] cusTtokens = cusid.getValue().split("[:,]");
				String cusId = cusTtokens[4].trim();

				applyOnDataBase("insert into rent(CAR_ID,CUS_ID,emp_id,rent_date,return_date,price) value (" + carId
						+ " ," + cusId + " ," + empId + " ,'" + rentDatet.getValue() + "' ,'" + returnedDatet.getValue()
						+ "'," + (price * result) + ");");

				cusid.getItems().clear();
				carids.getItems().clear();
				returnedDatet.setValue(null);

				ResultSet res11 = appyQueryOnDataBase(
						"select concat(CUS_NAME, ', Phone: ', CUS_PNUM,', ID: ', CUS_ID) from CUSTOMERS ");
				try {

					while (res11.next())
						cusid.getItems().add(res11.getString(1));
				} catch (SQLException e1) {
					error.setContentText(e1.getMessage());
					error.show();
				}

				ResultSet res2 = appyQueryOnDataBase(
						"select concat(CAR_BRAND,' ' ,CAR_MODEL,', Price: ',RENT_PRICE,', ID: ',CAR_ID) "
								+ "from VEHICLES c where not exists (select 1 from rent r where r.car_id = c.car_id);");
				try {

					while (res2.next())
						carids.getItems().add(res2.getString(1));
				} catch (SQLException e1) {
					error.setContentText(e1.getMessage());
					error.show();
				}

				success.setContentText("Rent Car Successfuly!!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in rent !!");
				error.show();
			}
			ObservableList<RentCar> rentCarsList = tableRentCar().getItems();
			ObservableList<NonRentCar> NonrentCarsList = tableNonRentCar().getItems();
			rentCars.setItems(rentCarsList);
			NonrentCars.setItems(NonrentCarsList);
		});

		ImageView b = new ImageView(new Image("left-arrow.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			rentButtonAction(primaryStage);
		});

		HBox hbu = new HBox(20, rent, back);
		hbu.setAlignment(Pos.CENTER);
		hbu.setPadding(new Insets(10));

		pane2.setBottom(hbu);
		BorderPane.setAlignment(back, Pos.CENTER);
		pane2.setPadding(new Insets(10));

		pane2.setCenter(v3);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private TableView<NonRentCar> tableNonRentCar() {
		TableView<NonRentCar> table = new TableView<NonRentCar>();

		table.setEditable(false);

		TableColumn<NonRentCar, Integer> CarId = new TableColumn<NonRentCar, Integer>("Car ID");
		CarId.setMinWidth(60);
		CarId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCarId()).asObject());
		CarId.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> EmpId = new TableColumn<NonRentCar, Integer>("Employee ID");
		EmpId.setMinWidth(60);
		EmpId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpId()).asObject());
		EmpId.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> CarBrand = new TableColumn<NonRentCar, String>("Car Brand");
		CarBrand.setMinWidth(80);
		CarBrand.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarBrand()));
		CarBrand.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> CarModel = new TableColumn<NonRentCar, String>("Car Model");
		CarModel.setMinWidth(80);
		CarModel.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarModel()));
		CarModel.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> Options = new TableColumn<NonRentCar, String>("Options");
		Options.setMinWidth(60);
		Options.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getOptions()));
		Options.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> numOfPass = new TableColumn<NonRentCar, Integer>("Number of Passengers");
		numOfPass.setMinWidth(140);
		numOfPass.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumOfPassengers()).asObject());
		numOfPass.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> numOfdoor = new TableColumn<NonRentCar, Integer>("Number of Doors");
		numOfdoor.setMinWidth(140);
		numOfdoor.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumOfDoors()).asObject());
		numOfdoor.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> transType = new TableColumn<NonRentCar, String>("Transmission Type");
		transType.setMinWidth(140);
		transType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTransmissionType() + ""));
		transType.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> color = new TableColumn<NonRentCar, String>("Car Color");
		color.setMinWidth(60);
		color.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarColor()));
		color.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, String> petrolType = new TableColumn<NonRentCar, String>("Petrol Type");
		petrolType.setMinWidth(50);
		petrolType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPetrolType() + ""));
		petrolType.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> priceWithoutcustomer = new TableColumn<NonRentCar, Integer>(
				"Price Without Customs");
		priceWithoutcustomer.setMinWidth(150);
		priceWithoutcustomer
				.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getPriceWithoutCustoms()).asObject());
		priceWithoutcustomer.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> rentPrice = new TableColumn<NonRentCar, Integer>("Rent Price");
		rentPrice.setMinWidth(80);
		rentPrice.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getRentPrice()).asObject());
		rentPrice.setStyle("-fx-alignment: CENTER;");

		TableColumn<NonRentCar, Integer> year = new TableColumn<NonRentCar, Integer>("Year");
		year.setMinWidth(50);
		year.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getYear()).asObject());
		year.setStyle("-fx-alignment: CENTER;");

		nonRentVehicles.clear();
		ResultSet rs = appyQueryOnDataBase(
				"select c.* from vehicles c where not exists (select 1 from rent r where r.car_id = c.car_id);");

		try {
			if (rs != null)
				while (rs.next()) {

					nonRentVehicles.add(new NonRentCar(Integer.parseInt(rs.getString(1)), rs.getString(2),
							Integer.parseInt(rs.getString(3)), (rs.getString(4)).charAt(0),
							Integer.parseInt(rs.getString(5)), (rs.getString(6)).charAt(0), rs.getString(7),
							rs.getString(8), Integer.parseInt(rs.getString(9)), rs.getString(10),
							Integer.parseInt(rs.getString(11)), Integer.parseInt(rs.getString(12)),
							Integer.parseInt(rs.getString(13))));

				}
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		ObservableList<NonRentCar> data = FXCollections.observableArrayList(nonRentVehicles);

		table.setItems(data);
		table.setMaxWidth(1250);
		table.setMinHeight(180);
		table.getColumns().addAll(CarId, CarBrand, CarModel, Options, numOfdoor, numOfPass, transType, color,
				petrolType, priceWithoutcustomer, rentPrice, year, EmpId);
		return table;
	}

	@SuppressWarnings("deprecation")
	private TableView<RentCar> tableRentCar() {
		TableView<RentCar> table = new TableView<RentCar>();

		table.setEditable(false);

		TableColumn<RentCar, Integer> CarId = new TableColumn<RentCar, Integer>("Car ID");
		CarId.setPrefWidth(60);
		CarId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCarId()).asObject());
		CarId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> CusId = new TableColumn<RentCar, Integer>("Customer ID");
		CusId.setPrefWidth(80);
		CusId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCusId()).asObject());
		CusId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> EmpId = new TableColumn<RentCar, Integer>("Employee ID");
		EmpId.setPrefWidth(80);
		EmpId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpId()).asObject());
		EmpId.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> CarBrand = new TableColumn<RentCar, String>("Car Brand");
		CarBrand.setPrefWidth(70);
		CarBrand.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarBrand()));
		CarBrand.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> CarModel = new TableColumn<RentCar, String>("Car Model");
		CarModel.setPrefWidth(80);
		CarModel.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarModel()));
		CarModel.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> Options = new TableColumn<RentCar, String>("Options");
		Options.setPrefWidth(80);
		Options.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getOptions()));
		Options.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> numOfPass = new TableColumn<RentCar, Integer>("Number of\nPassengers");
		numOfPass.setPrefWidth(100);
		numOfPass.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumOfPassengers()).asObject());
		numOfPass.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> numOfdoor = new TableColumn<RentCar, Integer>("Number of\n   Doors");
		numOfdoor.setPrefWidth(90);
		numOfdoor.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumOfDoors()).asObject());
		numOfdoor.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> transType = new TableColumn<RentCar, String>("Transmission\n	Type");
		transType.setPrefWidth(105);
		transType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTransmissionType() + ""));
		transType.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> color = new TableColumn<RentCar, String>("Car Color");
		color.setPrefWidth(70);
		color.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarColor()));
		color.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> petrolType = new TableColumn<RentCar, String>("Petrol Type");
		petrolType.setPrefWidth(80);
		petrolType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPetrolType() + ""));
		petrolType.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> priceWithoutcustomer = new TableColumn<RentCar, Integer>(
				"Price Without\n    Customs");
		priceWithoutcustomer.setPrefWidth(110);
		priceWithoutcustomer
				.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getPriceWithoutCustoms()).asObject());
		priceWithoutcustomer.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> rentPrice = new TableColumn<RentCar, Integer>("Rent Price");
		rentPrice.setPrefWidth(80);
		rentPrice.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getRentPrice()).asObject());
		rentPrice.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, Integer> year = new TableColumn<RentCar, Integer>("Year");
		year.setPrefWidth(50);
		year.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getYear()).asObject());
		year.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> rentDate = new TableColumn<RentCar, String>("Rent date");
		rentDate.setPrefWidth(80);
		rentDate.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRentDate() + ""));
		rentDate.setStyle("-fx-alignment: CENTER;");

		TableColumn<RentCar, String> returnDate = new TableColumn<RentCar, String>("Return Date");
		returnDate.setPrefWidth(80);
		returnDate.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getReturnDate() + ""));
		returnDate.setStyle("-fx-alignment: CENTER;");

		rentVehicles.clear();
		ResultSet rs = appyQueryOnDataBase(
				"select c.*,r.cus_ID,r.rent_date,return_date from vehicles c,rent r where r.car_id = c.car_id;");

		try {
			if (rs != null)
				while (rs.next()) {
					String[] rentDateTokens = rs.getString(15).split("-");
					String[] returnDateTokens = rs.getString(16).split("-");

					rentVehicles.add(new RentCar(Integer.parseInt(rs.getString(1)), rs.getString(2),
							Integer.parseInt(rs.getString(3)), (rs.getString(4)).charAt(0),
							Integer.parseInt(rs.getString(5)), (rs.getString(6)).charAt(0), rs.getString(7),
							rs.getString(8), Integer.parseInt(rs.getString(9)), rs.getString(10),
							Integer.parseInt(rs.getString(11)), Integer.parseInt(rs.getString(12)),
							Integer.parseInt(rs.getString(13)), Integer.parseInt(rs.getString(14)),
							new Date(Integer.parseInt(rentDateTokens[0]) - 1900,
									Integer.parseInt(rentDateTokens[1]) - 1, Integer.parseInt(rentDateTokens[2])),
							new Date(Integer.parseInt(returnDateTokens[0]) - 1900,
									Integer.parseInt(returnDateTokens[1]) - 1, Integer.parseInt(returnDateTokens[2]))));

				}
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}

		ObservableList<RentCar> data = FXCollections.observableArrayList(rentVehicles);

		table.setItems(data);
		table.setMaxWidth(1300);
		table.setMinHeight(180);
		table.getColumns().addAll(CarId, CarBrand, CarModel, Options, numOfdoor, numOfPass, transType, color,
				petrolType, priceWithoutcustomer, rentPrice, year, EmpId, CusId, rentDate, returnDate);
		return table;
	}

	private void deleteEmployeeAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Remove Employee");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label empId = new Label("Employee Id : ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select emp_id from RENTING_EMP;");
		try {
			while (res.next())
				empIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		Label empName = new Label("Employee Name:");
		empName.setPadding(new Insets(7));
		empName.setMinWidth(130);
		TextField empNamet = new TextField();
		empNamet.setEditable(false);
		empNamet.setMinWidth(200);
		IconedTextFieled(empName, empNamet);
		HBox h1 = new HBox(empName, empNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empAdress = new Label("Employee Adress:");
		empAdress.setPadding(new Insets(7));
		empAdress.setMinWidth(130);
		TextField empAdresst = new TextField();
		empAdresst.setEditable(false);
		empAdresst.setMinWidth(200);
		IconedTextFieled(empAdress, empAdresst);
		HBox h3 = new HBox(empAdress, empAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setEditable(false);
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label dateOFBirth = new Label("Date of Birth :");
		dateOFBirth.setPadding(new Insets(7));
		dateOFBirth.setMinWidth(130);
		TextField dateOFBirtht = new TextField();
		dateOFBirtht.setEditable(false);
		dateOFBirtht.setMinWidth(200);
		IconedTextFieled(dateOFBirth, dateOFBirtht);
		HBox h4 = new HBox(dateOFBirth, dateOFBirtht);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label startWork = new Label("Start Work Date :");
		startWork.setPadding(new Insets(7));
		startWork.setMinWidth(130);
		TextField startWorkt = new TextField();
		startWorkt.setEditable(false);
		startWorkt.setMinWidth(200);
		IconedTextFieled(startWork, startWorkt);
		HBox h5 = new HBox(startWork, startWorkt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label baseSalary = new Label("Base Salary :");
		baseSalary.setPadding(new Insets(7));
		baseSalary.setMinWidth(130);
		TextField baseSalaryt = new TextField();
		baseSalaryt.setEditable(false);
		baseSalaryt.setMinWidth(200);
		IconedTextFieled(baseSalary, baseSalaryt);
		HBox h10 = new HBox(baseSalary, baseSalaryt);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label comissionRate = new Label("Comission Rate :");
		comissionRate.setPadding(new Insets(7));
		comissionRate.setMinWidth(130);
		TextField comissionRatet = new TextField();
		comissionRatet.setEditable(false);
		comissionRatet.setMinWidth(200);
		IconedTextFieled(comissionRate, comissionRatet);
		HBox h11 = new HBox(comissionRate, comissionRatet);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label yearlySales = new Label("Yearly Sales : ");
		yearlySales.setPadding(new Insets(7));
		yearlySales.setMinWidth(130);
		TextField yearlySalest = new TextField();
		yearlySalest.setEditable(false);
		yearlySalest.setMinWidth(200);
		IconedTextFieled(yearlySales, yearlySalest);
		HBox h12 = new HBox(yearlySales, yearlySalest);
		h12.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h7, h1, h3, h9, h4, h5, h10, h11, h12);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView r = new ImageView(new Image("rememp.png"));
		r.setFitHeight(80);
		r.setFitWidth(80);
		Button remove = new Button("Remove Employee", r);
		remove.setPrefSize(250, 100);
		icons(remove);
		butoonEffect(remove);
		remove.setEffect(new DropShadow());

		remove.setOnAction(e -> {
			try {
				applyOnDataBase("delete from renting_emp where EMP_ID =" + empIdt.getValue() + ";");

				success.setContentText("Delete Successfuly!!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in delete !!");
				error.show();
			}
		});
		ImageView b = new ImageView(new Image("backemp.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			employeeButtonAction(primaryStage);
		});

		empIdt.setOnAction(e -> {
			try {
				ResultSet rs = appyQueryOnDataBase(
						"select * from RENTING_EMP where emp_ID =" + empIdt.getValue() + ";");
				rs.next();

				empNamet.setText(rs.getString(2));
				empAdresst.setText(rs.getString(3));
				phNumt.setText(rs.getString(4));
				dateOFBirtht.setText(rs.getString(5));
				startWorkt.setText(rs.getString(6));
				baseSalaryt.setText(rs.getString(7));
				comissionRatet.setText(rs.getString(8));
				yearlySalest.setText(rs.getString(9));

				remove.setDisable(false);

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		});

		HBox h = new HBox(60, remove, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void editEmloyeeAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Edit Employee");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label empId = new Label("Employee Id : ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select emp_id from RENTING_EMP;");
		try {
			while (res.next())
				empIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		Label empName = new Label("Employee Name:");
		empName.setPadding(new Insets(7));
		empName.setMinWidth(130);
		TextField empNamet = new TextField();
		empNamet.setMinWidth(200);
		IconedTextFieled(empName, empNamet);
		HBox h1 = new HBox(empName, empNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empAdress = new Label("Employee Adress:");
		empAdress.setPadding(new Insets(7));
		empAdress.setMinWidth(130);
		TextField empAdresst = new TextField();
		empAdresst.setMinWidth(200);
		IconedTextFieled(empAdress, empAdresst);
		HBox h3 = new HBox(empAdress, empAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label dateOFBirth = new Label("Date of Birth :");
		dateOFBirth.setPadding(new Insets(7));
		dateOFBirth.setMinWidth(130);
		DatePicker dateOFBirtht = new DatePicker();
		dateOFBirtht.setEditable(false);
		dateOFBirtht.setMinWidth(200);
		IconedTextFieled(dateOFBirth, dateOFBirtht);
		HBox h4 = new HBox(dateOFBirth, dateOFBirtht);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label startWork = new Label("Start Work Date :");
		startWork.setPadding(new Insets(7));
		startWork.setMinWidth(130);
		DatePicker startWorkt = new DatePicker();
		startWorkt.setEditable(false);
		startWorkt.setMinWidth(200);
		IconedTextFieled(startWork, startWorkt);
		HBox h5 = new HBox(startWork, startWorkt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label baseSalary = new Label("Base Salary :");
		baseSalary.setPadding(new Insets(7));
		baseSalary.setMinWidth(130);
		TextField baseSalaryt = new TextField();
		baseSalaryt.setMinWidth(200);
		IconedTextFieled(baseSalary, baseSalaryt);
		HBox h10 = new HBox(baseSalary, baseSalaryt);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label comissionRate = new Label("Comission Rate :");
		comissionRate.setPadding(new Insets(7));
		comissionRate.setMinWidth(130);
		TextField comissionRatet = new TextField();
		comissionRatet.setMinWidth(200);
		IconedTextFieled(comissionRate, comissionRatet);
		HBox h11 = new HBox(comissionRate, comissionRatet);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label yearlySales = new Label("Yearly Sales : ");
		yearlySales.setPadding(new Insets(7));
		yearlySales.setMinWidth(130);
		TextField yearlySalest = new TextField();
		yearlySalest.setMinWidth(200);
		IconedTextFieled(yearlySales, yearlySalest);
		HBox h12 = new HBox(yearlySales, yearlySalest);
		h12.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h7, h1, h3, h9, h4, h5, h10, h11, h12);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("editemp.png"));
		a.setFitHeight(80);
		a.setFitWidth(80);
		Button edit = new Button("Edit Employee", a);
		edit.setDisable(true);
		edit.setPrefSize(250, 100);
		icons(edit);
		butoonEffect(edit);
		edit.setEffect(new DropShadow());

		edit.setOnAction(e -> {
			try {
				LocalDate value = dateOFBirtht.getValue();
				LocalDate value1 = startWorkt.getValue();

				applyOnDataBase("UPDATE renting_emp" + " SET YEARLY_SALES = " + yearlySalest.getText()
						+ ",START_DATE = '" + value1 + "',NAME = '" + empNamet.getText() + "',EMP_PNUM = "
						+ phNumt.getText() + ",DATE_OF_BIRTH = '" + value + "',COMMISSION_RATE = "
						+ comissionRatet.getText() + ",BASE_SALARY = " + baseSalaryt.getText() + ",ADDRESS = '"
						+ empAdresst.getText() + "' WHERE EMP_ID = " + empIdt.getValue() + ";");
				success.setContentText("Success Update !!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in update !!");
				error.show();
			}
		});

		empIdt.setOnAction(e -> {
			try {
				ResultSet rs = appyQueryOnDataBase(
						"select * from RENTING_EMP where emp_ID =" + empIdt.getValue() + ";");
				rs.next();

				empNamet.setText(rs.getString(2));
				empAdresst.setText(rs.getString(3));
				phNumt.setText(rs.getString(4));
				dateOFBirtht.setValue(LOCAL_DATE(rs.getString(5)));
				startWorkt.setValue(LOCAL_DATE(rs.getString(6)));
				baseSalaryt.setText(rs.getString(7));
				comissionRatet.setText(rs.getString(8));
				yearlySalest.setText(rs.getString(9));

				edit.setDisable(false);

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		});

		ImageView b = new ImageView(new Image("backemp.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			employeeButtonAction(primaryStage);
		});

		HBox h = new HBox(60, edit, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addEmloyeeAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Add Employee");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label empName = new Label("Employee Name:");
		empName.setPadding(new Insets(7));
		empName.setMinWidth(130);
		TextField empNamet = new TextField();
		empNamet.setMinWidth(200);
		IconedTextFieled(empName, empNamet);
		HBox h1 = new HBox(empName, empNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empAdress = new Label("Employee Adress:");
		empAdress.setPadding(new Insets(7));
		empAdress.setMinWidth(130);
		TextField empAdresst = new TextField();
		empAdresst.setMinWidth(200);
		IconedTextFieled(empAdress, empAdresst);
		HBox h3 = new HBox(empAdress, empAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label dateOFBirth = new Label("Date of Birth :");
		dateOFBirth.setPadding(new Insets(7));
		dateOFBirth.setMinWidth(130);
		DatePicker dateOFBirtht = new DatePicker();
		dateOFBirtht.setEditable(false);
		dateOFBirtht.setMinWidth(200);
		IconedTextFieled(dateOFBirth, dateOFBirtht);
		HBox h4 = new HBox(dateOFBirth, dateOFBirtht);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label startWork = new Label("Start Work Date :");
		startWork.setPadding(new Insets(7));
		startWork.setMinWidth(130);
		DatePicker startWorkt = new DatePicker();
		startWorkt.setEditable(false);
		startWorkt.setMinWidth(200);
		IconedTextFieled(startWork, startWorkt);
		HBox h5 = new HBox(startWork, startWorkt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label baseSalary = new Label("Base Salary :");
		baseSalary.setPadding(new Insets(7));
		baseSalary.setMinWidth(130);
		TextField baseSalaryt = new TextField();
		baseSalaryt.setMinWidth(200);
		IconedTextFieled(baseSalary, baseSalaryt);
		HBox h10 = new HBox(baseSalary, baseSalaryt);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label comissionRate = new Label("Comission Rate :");
		comissionRate.setPadding(new Insets(7));
		comissionRate.setMinWidth(130);
		TextField comissionRatet = new TextField();
		comissionRatet.setMinWidth(200);
		IconedTextFieled(comissionRate, comissionRatet);
		HBox h11 = new HBox(comissionRate, comissionRatet);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label yearlySales = new Label("Yearly Sales : ");
		yearlySales.setPadding(new Insets(7));
		yearlySales.setMinWidth(130);
		TextField yearlySalest = new TextField();
		yearlySalest.setMinWidth(200);
		IconedTextFieled(yearlySales, yearlySalest);
		HBox h12 = new HBox(yearlySales, yearlySalest);
		h12.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h1, h3, h9, h4, h5, h10, h11, h12);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("addemp.png"));
		a.setFitHeight(80);
		a.setFitWidth(80);
		Button add = new Button("Add Employee", a);
		add.setPrefSize(250, 100);
		icons(add);
		butoonEffect(add);
		add.setEffect(new DropShadow());

		add.setOnAction(e -> {
			try {
				LocalDate value1 = startWorkt.getValue();
				LocalDate value = dateOFBirtht.getValue();

				applyOnDataBase(
						"insert into renting_emp(name,address,emp_pnum,DATE_OF_BIRTH,START_DATE,BASE_SALARY,COMMISSION_RATE,YEARLY_SALES) value ('"
								+ empNamet.getText() + "', '" + empAdresst.getText() + "'," + phNumt.getText() + ",'"
								+ value + "','" + value1 + "'," + baseSalaryt.getText() + "," + comissionRatet.getText()
								+ "," + yearlySalest.getText() + ");");

				success.setContentText("Add Successfuly!!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in add !!");
				error.show();
			}
		});

		ImageView b = new ImageView(new Image("backemp.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			employeeButtonAction(primaryStage);
		});

		HBox h = new HBox(60, add, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void editCustomerAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Edit Customer");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label cusName = new Label("Customer Name: ");
		cusName.setPadding(new Insets(7));
		cusName.setMinWidth(130);
		TextField cusNamet = new TextField();
		cusNamet.setMinWidth(200);
		IconedTextFieled(cusName, cusNamet);
		HBox h1 = new HBox(cusName, cusNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label CusId = new Label("Customer ID:     ");
		CusId.setMinWidth(130);
		CusId.setPadding(new Insets(7));
		ComboBox<String> CusIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select cus_id from customers;");
		try {
			while (res.next())
				CusIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		CusIdt.setMinWidth(200);
		IconedTextFieled(CusId, CusIdt);
		HBox h2 = new HBox(CusId, CusIdt);
		h2.setAlignment(Pos.CENTER_LEFT);

		Label CusAdress = new Label("Customer Adress: ");
		CusAdress.setPadding(new Insets(7));
		CusAdress.setMinWidth(130);
		TextField CusAdresst = new TextField();
		CusAdresst.setMinWidth(200);
		IconedTextFieled(CusAdress, CusAdresst);
		HBox h3 = new HBox(CusAdress, CusAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label budget = new Label("Budget :         ");
		budget.setPadding(new Insets(7));
		budget.setMinWidth(130);
		TextField budgett = new TextField();
		budgett.setMinWidth(200);
		IconedTextFieled(budget, budgett);
		HBox h4 = new HBox(budget, budgett);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carere = new Label("Carrier :        ");
		carere.setPadding(new Insets(7));
		carere.setMinWidth(130);
		TextField careret = new TextField();
		careret.setMinWidth(200);
		IconedTextFieled(carere, careret);
		HBox h5 = new HBox(carere, careret);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet resu = appyQueryOnDataBase("select concat(emp_id,', ',name) from RENTING_EMP;");
		try {
			while (resu.next())
				empIdt.getItems().add(resu.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);

		h7.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h2, h1, h3, h4, h5, h9, h7);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView e = new ImageView(new Image("editcus.png"));
		e.setFitHeight(80);
		e.setFitWidth(80);
		Button edit = new Button("Edit Customer", e);
		edit.setDisable(true);
		edit.setPrefSize(250, 100);
		icons(edit);
		butoonEffect(edit);
		edit.setEffect(new DropShadow());

		edit.setOnAction(ee -> {
			try {
				applyOnDataBase("UPDATE customers" + " SET " + "CUS_NAME ='" + cusNamet.getText() + "',ADDRESS ='"
						+ CusAdresst.getText() + "',CUS_ID =" + CusIdt.getValue() + ",BUDGET =" + budgett.getText()
						+ ",CARRIER ='" + careret.getText() + "	',CUS_PNUM =" + phNumt.getText() + ",EMP_Id ="
						+ empIdt.getValue().substring(0, empIdt.getValue().indexOf(',')) + " WHERE CUS_ID = "
						+ CusIdt.getValue() + ";");
				success.setContentText("Success Update !!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in update !!");
				error.show();
			}
		});

		CusIdt.setOnAction(e1 -> {
			try {
				ResultSet rs = appyQueryOnDataBase("select * from customers where cus_ID =" + CusIdt.getValue() + ";");
				rs.next();

				cusNamet.setText(rs.getString(1));
				CusAdresst.setText(rs.getString(2));
				budgett.setText(rs.getString(4));
				careret.setText(rs.getString(5));
				phNumt.setText(rs.getString(6));
				empIdt.setValue(rs.getString(7) + ",");

				edit.setDisable(false);

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		});

		ImageView b = new ImageView(new Image("backcus.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(ee -> {
			customerButtonAction(primaryStage);
		});

		HBox h = new HBox(60, edit, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addCustomerAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Add Customer");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label cusName = new Label("Customer Name: ");
		cusName.setPadding(new Insets(7));
		cusName.setMinWidth(130);
		TextField cusNamet = new TextField();
		cusNamet.setMinWidth(200);
		IconedTextFieled(cusName, cusNamet);
		HBox h1 = new HBox(cusName, cusNamet);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label CusAdress = new Label("Customer Adress: ");
		CusAdress.setPadding(new Insets(7));
		CusAdress.setMinWidth(130);
		TextField CusAdresst = new TextField();
		CusAdresst.setMinWidth(200);
		IconedTextFieled(CusAdress, CusAdresst);
		HBox h3 = new HBox(CusAdress, CusAdresst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label budget = new Label("Budget :         ");
		budget.setPadding(new Insets(7));
		budget.setMinWidth(130);
		TextField budgett = new TextField();
		budgett.setMinWidth(200);
		IconedTextFieled(budget, budgett);
		HBox h4 = new HBox(budget, budgett);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carere = new Label("Carrier :        ");
		carere.setPadding(new Insets(7));
		carere.setMinWidth(130);
		TextField careret = new TextField();
		careret.setMinWidth(200);
		IconedTextFieled(carere, careret);
		HBox h5 = new HBox(carere, careret);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label phNum = new Label("Phone Number :   ");
		phNum.setPadding(new Insets(7));
		phNum.setMinWidth(130);
		TextField phNumt = new TextField();
		phNumt.setMinWidth(200);
		IconedTextFieled(phNum, phNumt);
		HBox h9 = new HBox(phNum, phNumt);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(130);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select concat(emp_id,', ',name) from RENTING_EMP;");
		try {
			while (res.next())
				empIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(200);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(15, h1, h3, h4, h5, h9, h7);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		HBox mix = new HBox(60, v);
		mix.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("addcus.png"));
		a.setFitHeight(80);
		a.setFitWidth(80);
		Button add = new Button("Add Customer", a);
		add.setPrefSize(250, 100);
		icons(add);
		butoonEffect(add);
		add.setEffect(new DropShadow());

		add.setOnAction(e -> {
			try {
				applyOnDataBase("insert into customers(CUS_NAME,ADDRESS,BUDGET,CARRIER,CUS_PNUM,EMP_ID) values ('"
						+ cusNamet.getText() + "','" + CusAdresst.getText() + "', " + budgett.getText() + ",'"
						+ careret.getText() + "'," + phNumt.getText() + ","
						+ empIdt.getValue().substring(0, empIdt.getValue().indexOf(',')) + ");");

				success.setContentText("Add Successfuly!!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in add !!");
				error.show();
			}
		});
		ImageView b = new ImageView(new Image("backcus.png"));
		b.setFitHeight(80);
		b.setFitWidth(80);
		Button back = new Button("Back", b);
		back.setPrefSize(250, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			customerButtonAction(primaryStage);
		});

		HBox control = new HBox(20, add, back);
		control.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(60, mix, control);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void editCarIfnoAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Edit Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label carId = new Label("Car ID: ");
		carId.setPadding(new Insets(7));
		carId.setMinWidth(180);
		ComboBox<String> carIdt = new ComboBox<>();
		ResultSet resu = appyQueryOnDataBase("select car_id from VEHICLES v;");
		try {

			while (resu.next())
				carIdt.getItems().add(resu.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		carIdt.setMinWidth(250);
		IconedTextFieled(carId, carIdt);
		HBox h1 = new HBox(carId, carIdt);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(180);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select concat(emp_id,', ',name) from RENTING_EMP;");
		try {
			while (res.next())
				empIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(250);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		Label options = new Label("Options: ");
		options.setPadding(new Insets(7));
		options.setMinWidth(180);
		TextField optionst = new TextField();
		optionst.setMinWidth(250);
		IconedTextFieled(options, optionst);
		HBox h3 = new HBox(options, optionst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label carBrand = new Label("Car Brand :         ");
		carBrand.setPadding(new Insets(7));
		carBrand.setMinWidth(180);
		TextField carBrandt = new TextField();
		carBrandt.setMinWidth(250);
		IconedTextFieled(carBrand, carBrandt);
		HBox h4 = new HBox(carBrand, carBrandt);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carModel = new Label("Car Model :        ");
		carModel.setPadding(new Insets(7));
		carModel.setMinWidth(180);
		TextField carModelt = new TextField();
		carModelt.setMinWidth(250);
		IconedTextFieled(carModel, carModelt);
		HBox h5 = new HBox(carModel, carModelt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label carColor = new Label("Car Color :   ");
		carColor.setPadding(new Insets(7));
		carColor.setMinWidth(180);
		TextField carColort = new TextField();
		carColort.setMinWidth(250);
		IconedTextFieled(carColor, carColort);
		HBox h9 = new HBox(carColor, carColort);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label transmissionType = new Label("Transmission Type : ");
		transmissionType.setPadding(new Insets(7));
		transmissionType.setMinWidth(180);

		RadioButton gear = new RadioButton("Gear");
		RadioButton Automatic = new RadioButton("Automatic");
		ToggleGroup tg = new ToggleGroup();
		gear.setToggleGroup(tg);
		Automatic.setToggleGroup(tg);
		gear.setFont(new Font("Times New Roman", 14));
		Automatic.setFont(new Font("Times New Roman", 14));
		HBox tBox = new HBox(50, gear, Automatic);
		tBox.setMinWidth(250);
		tBox.setAlignment(Pos.CENTER);

		IconedTextFieled(transmissionType, tBox);
		HBox h8 = new HBox(transmissionType, tBox);
		h8.setAlignment(Pos.CENTER_LEFT);

		Label numOfPassengers = new Label("Number of Passengers : ");
		numOfPassengers.setPadding(new Insets(7));
		numOfPassengers.setMinWidth(180);
		TextField numOfPassengerst = new TextField();
		numOfPassengerst.setMinWidth(250);
		IconedTextFieled(numOfPassengers, numOfPassengerst);
		HBox h6 = new HBox(numOfPassengers, numOfPassengerst);
		h6.setAlignment(Pos.CENTER_LEFT);

		Label numOfDoors = new Label("Num Of Doors : ");
		numOfDoors.setPadding(new Insets(7));
		numOfDoors.setMinWidth(180);
		TextField numOfDoorst = new TextField();
		numOfDoorst.setMinWidth(250);
		IconedTextFieled(numOfDoors, numOfDoorst);
		HBox h10 = new HBox(numOfDoors, numOfDoorst);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label petrolType = new Label("Petrol Type : ");
		petrolType.setPadding(new Insets(7));
		petrolType.setMinWidth(180);
		RadioButton disel = new RadioButton("Disel");
		RadioButton petrol = new RadioButton("Petrol");
		ToggleGroup tg1 = new ToggleGroup();
		disel.setToggleGroup(tg1);
		petrol.setToggleGroup(tg1);
		disel.setFont(new Font("Times New Roman", 14));
		petrol.setFont(new Font("Times New Roman", 14));
		HBox pBox = new HBox(50, disel, petrol);
		pBox.setMinWidth(250);
		pBox.setAlignment(Pos.CENTER);

		IconedTextFieled(petrolType, pBox);
		HBox h11 = new HBox(petrolType, pBox);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label priceWithoutCustoms = new Label("Price Without Customs : ");
		priceWithoutCustoms.setPadding(new Insets(7));
		priceWithoutCustoms.setMinWidth(180);
		TextField priceWithoutCustomst = new TextField();
		priceWithoutCustomst.setMinWidth(250);
		IconedTextFieled(priceWithoutCustoms, priceWithoutCustomst);
		HBox h12 = new HBox(priceWithoutCustoms, priceWithoutCustomst);
		h12.setAlignment(Pos.CENTER_LEFT);

		Label rentPrice = new Label("Rent Price : ");
		rentPrice.setPadding(new Insets(7));
		rentPrice.setMinWidth(180);
		TextField rentPricet = new TextField();
		rentPricet.setMinWidth(250);
		IconedTextFieled(rentPrice, rentPricet);
		HBox h13 = new HBox(rentPrice, rentPricet);
		h13.setAlignment(Pos.CENTER_LEFT);

		Label year = new Label("Year : ");
		year.setPadding(new Insets(7));
		year.setMinWidth(180);
		TextField yeart = new TextField();
		yeart.setMinWidth(250);
		IconedTextFieled(year, yeart);
		HBox h14 = new HBox(year, yeart);
		h14.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(25, h1, h3, h4, h5, h6, h7);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		VBox v2 = new VBox(25, h8, h9, h10, h11, h12, h13, h14);
		v2.setAlignment(Pos.CENTER_LEFT);
		v2.setPadding(new Insets(30));

		HBox tow = new HBox(15, v, v2);
		tow.setAlignment(Pos.CENTER_LEFT);
		tow.setPadding(new Insets(30));

		HBox mix = new HBox(60, tow);
		mix.setAlignment(Pos.CENTER);

		ImageView r = new ImageView(new Image("editcar.png"));
		r.setFitHeight(67);
		r.setFitWidth(80);
		Button edit = new Button("Edit Car", r);
		edit.setPrefSize(200, 100);
		icons(edit);
		butoonEffect(edit);
		edit.setEffect(new DropShadow());
		edit.setDisable(true);

		edit.setOnAction(e -> {
			try {

				applyOnDataBase("update VEHICLES set OPTIONS ='" + optionst.getText() + "'   ,PRICE_WIHTOUT_CUSTOMS ="
						+ priceWithoutCustomst.getText() + "    ,TRANSMISSION_TYPE ='"
						+ ((RadioButton) (tg.getSelectedToggle())).getText().charAt(0) + "'  ,NUM_OF_PASS ="
						+ numOfPassengerst.getText() + "   ,PETROL_TYPE ='"
						+ ((RadioButton) (tg1.getSelectedToggle())).getText().charAt(0) + "'  ,CAR_MODEL ='"
						+ carModelt.getText() + "'   ,CAR_COLOR ='" + carColort.getText() + "'   ,NUM_OF_DOORS ="
						+ numOfDoorst.getText() + "   ,CAR_BRAND ='" + carBrandt.getText() + "'   ,RENT_PRICE ="
						+ rentPricet.getText() + "   ,EMP_ID ="
						+ empIdt.getValue().substring(0, empIdt.getValue().indexOf(',')) + "   ,year ="
						+ yeart.getText() + " where CAR_ID = " + carIdt.getValue());

				edit.setDisable(false);
				success.setContentText("Success Update !!");
				success.show();
			} catch (Exception e2) {
				error.setContentText("Error in update !!");
				error.show();
			}
		});

		ImageView s = new ImageView(new Image("serchCar.png"));
		s.setFitHeight(70);
		s.setFitWidth(80);

		carIdt.setOnAction(e -> {
			try {
				ResultSet rs = appyQueryOnDataBase("select * from VEHICLES where car_ID =" + carIdt.getValue() + ";");
				rs.next();
				optionst.setText(rs.getString(2));
				priceWithoutCustomst.setText(rs.getString(3));
				char c = rs.getString(4).charAt(0);
				if (c == 'g' || c == 'G')
					gear.setSelected(true);
				else
					Automatic.setSelected(true);
				numOfPassengerst.setText(rs.getString(5));
				char c1 = rs.getString(6).charAt(0);
				if (c1 == 'd' || c1 == 'D')
					disel.setSelected(true);
				else
					petrol.setSelected(true);
				carModelt.setText(rs.getString(7));
				carColort.setText(rs.getString(8));
				numOfDoorst.setText(rs.getString(9));
				carBrandt.setText(rs.getString(10));
				rentPricet.setText(rs.getString(11));
				empIdt.setValue(rs.getString(12) + ",");
				yeart.setText(rs.getString(13));

				edit.setDisable(false);

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		});

		ImageView b = new ImageView(new Image("back.png"));
		b.setFitHeight(67);
		b.setFitWidth(80);
		Button back = new Button("Back to\n car page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			carButtonAction(primaryStage);
		});

		HBox h = new HBox(50, edit, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(50, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void deleteCarAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Remove Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label carId = new Label("Car ID: ");
		carId.setPadding(new Insets(7));
		carId.setMinWidth(180);
		ComboBox<String> carIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select car_id from VEHICLES v;");
		try {

			while (res.next())
				carIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		carIdt.setMinWidth(250);
		IconedTextFieled(carId, carIdt);
		HBox h1 = new HBox(carId, carIdt);
		h1.setAlignment(Pos.CENTER_LEFT);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(180);
		TextField empIdt = new TextField();
		empIdt.setEditable(false);
		empIdt.setMinWidth(250);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		Label options = new Label("Options: ");
		options.setPadding(new Insets(7));
		options.setMinWidth(180);
		TextField optionst = new TextField();
		optionst.setEditable(false);
		optionst.setMinWidth(250);
		IconedTextFieled(options, optionst);
		HBox h3 = new HBox(options, optionst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label carBrand = new Label("Car Brand :         ");
		carBrand.setPadding(new Insets(7));
		carBrand.setMinWidth(180);
		TextField carBrandt = new TextField();
		carBrandt.setEditable(false);
		carBrandt.setMinWidth(250);
		IconedTextFieled(carBrand, carBrandt);
		HBox h4 = new HBox(carBrand, carBrandt);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carModel = new Label("Car Model :        ");
		carModel.setPadding(new Insets(7));
		carModel.setMinWidth(180);
		TextField carModelt = new TextField();
		carModelt.setEditable(false);
		carModelt.setMinWidth(250);
		IconedTextFieled(carModel, carModelt);
		HBox h5 = new HBox(carModel, carModelt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label carColor = new Label("Car Color :   ");
		carColor.setPadding(new Insets(7));
		carColor.setMinWidth(180);
		TextField carColort = new TextField();
		carColort.setEditable(false);
		carColort.setMinWidth(250);
		IconedTextFieled(carColor, carColort);
		HBox h9 = new HBox(carColor, carColort);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label transmissionType = new Label("Transmission Type : ");
		transmissionType.setPadding(new Insets(7));
		transmissionType.setMinWidth(180);
		TextField transmissionTypet = new TextField();
		transmissionTypet.setEditable(false);
		transmissionTypet.setMinWidth(250);
		IconedTextFieled(transmissionType, transmissionTypet);
		HBox h8 = new HBox(transmissionType, transmissionTypet);
		h8.setAlignment(Pos.CENTER_LEFT);

		Label numOfPassengers = new Label("Number of Passengers : ");
		numOfPassengers.setPadding(new Insets(7));
		numOfPassengers.setMinWidth(180);
		TextField numOfPassengerst = new TextField();
		numOfPassengerst.setEditable(false);
		numOfPassengerst.setMinWidth(250);
		IconedTextFieled(numOfPassengers, numOfPassengerst);
		HBox h6 = new HBox(numOfPassengers, numOfPassengerst);
		h6.setAlignment(Pos.CENTER_LEFT);

		Label numOfDoors = new Label("Num Of Doors : ");
		numOfDoors.setPadding(new Insets(7));
		numOfDoors.setMinWidth(180);
		TextField numOfDoorst = new TextField();
		numOfDoorst.setEditable(false);
		numOfDoorst.setMinWidth(250);
		IconedTextFieled(numOfDoors, numOfDoorst);
		HBox h10 = new HBox(numOfDoors, numOfDoorst);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label petrolType = new Label("Petrol Type : ");
		petrolType.setPadding(new Insets(7));
		petrolType.setMinWidth(180);
		TextField petrolTypet = new TextField();
		petrolTypet.setEditable(false);
		petrolTypet.setMinWidth(250);
		IconedTextFieled(petrolType, petrolTypet);
		HBox h11 = new HBox(petrolType, petrolTypet);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label priceWithoutCustoms = new Label("Price Without Customs : ");
		priceWithoutCustoms.setPadding(new Insets(7));
		priceWithoutCustoms.setMinWidth(180);
		TextField priceWithoutCustomst = new TextField();
		priceWithoutCustomst.setEditable(false);
		priceWithoutCustomst.setMinWidth(250);
		IconedTextFieled(priceWithoutCustoms, priceWithoutCustomst);
		HBox h12 = new HBox(priceWithoutCustoms, priceWithoutCustomst);
		h12.setAlignment(Pos.CENTER_LEFT);

		Label rentPrice = new Label("Rent Price : ");
		rentPrice.setPadding(new Insets(7));
		rentPrice.setMinWidth(180);
		TextField rentPricet = new TextField();
		rentPricet.setEditable(false);
		rentPricet.setMinWidth(250);
		IconedTextFieled(rentPrice, rentPricet);
		HBox h13 = new HBox(rentPrice, rentPricet);
		h13.setAlignment(Pos.CENTER_LEFT);

		Label year = new Label("Year : ");
		year.setPadding(new Insets(7));
		year.setMinWidth(180);
		TextField yeart = new TextField();
		yeart.setEditable(false);
		yeart.setMinWidth(250);
		IconedTextFieled(year, yeart);
		HBox h14 = new HBox(year, yeart);
		h14.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(25, h1, h3, h4, h5, h6, h7);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		VBox v2 = new VBox(25, h8, h9, h10, h11, h12, h13, h14);
		v2.setAlignment(Pos.CENTER_LEFT);
		v2.setPadding(new Insets(30));

		HBox tow = new HBox(15, v, v2);
		tow.setAlignment(Pos.CENTER_LEFT);
		tow.setPadding(new Insets(30));

		HBox mix = new HBox(60, tow);
		mix.setAlignment(Pos.CENTER);

		ImageView r = new ImageView(new Image("removeCar.png"));
		r.setFitHeight(67);
		r.setFitWidth(80);
		Button remove = new Button("Remove Car", r);
		remove.setPrefSize(200, 100);
		icons(remove);
		butoonEffect(remove);
		remove.setEffect(new DropShadow());

		remove.setOnAction(e -> {
			try {
				applyOnDataBase("DELETE FROM VEHICLES WHERE CAR_ID = " + carIdt.getValue() + ";");

			} catch (SQLException e1) {

				error.setContentText(e1.getMessage());
				error.show();
			}
		});

		carIdt.setOnAction(e -> {
			try {
				ResultSet rs = appyQueryOnDataBase("select * from VEHICLES where car_ID =" + carIdt.getValue() + ";");
				rs.next();
				optionst.setText(rs.getString(2));
				priceWithoutCustomst.setText(rs.getString(3));
				transmissionTypet.setText(rs.getString(4));
				numOfPassengerst.setText(rs.getString(5));
				petrolTypet.setText(rs.getString(6));
				carModelt.setText(rs.getString(7));
				carColort.setText(rs.getString(8));
				numOfDoorst.setText(rs.getString(9));
				carBrandt.setText(rs.getString(10));
				rentPricet.setText(rs.getString(11));
				empIdt.setText(rs.getString(12));
				yeart.setText(rs.getString(13));

				remove.setDisable(false);

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		});

		ImageView b = new ImageView(new Image("back.png"));
		b.setFitHeight(67);
		b.setFitWidth(80);
		Button back = new Button("Back to\n car page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			carButtonAction(primaryStage);
		});

		HBox h = new HBox(50, remove, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(50, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addCarAction(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Image image = new Image("bmw_delarship1.png");
		ImageView background = new ImageView(image);
		background.setFitWidth(1535);
		background.setFitHeight(800);
		pane.getChildren().add(background);

		BorderPane pane2 = new BorderPane();

		Label title = new Label("Add Car");
		styleTitle(title);
		title.setPadding(new Insets(10));
		pane2.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label empId = new Label("Employee Id :    ");
		empId.setPadding(new Insets(7));
		empId.setMinWidth(180);
		ComboBox<String> empIdt = new ComboBox<>();
		ResultSet res = appyQueryOnDataBase("select concat(emp_id,', ',name) from RENTING_EMP;");
		try {

			while (res.next())
				empIdt.getItems().add(res.getString(1));
		} catch (SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		empIdt.setMinWidth(250);
		IconedTextFieled(empId, empIdt);
		HBox h7 = new HBox(empId, empIdt);
		h7.setAlignment(Pos.CENTER_LEFT);

		Label options = new Label("Options: ");
		options.setPadding(new Insets(7));
		options.setMinWidth(180);
		TextField optionst = new TextField();
		optionst.setMinWidth(250);
		IconedTextFieled(options, optionst);
		HBox h3 = new HBox(options, optionst);
		h3.setAlignment(Pos.CENTER_LEFT);

		Label carBrand = new Label("Car Brand :         ");
		carBrand.setPadding(new Insets(7));
		carBrand.setMinWidth(180);
		TextField carBrandt = new TextField();
		carBrandt.setMinWidth(250);
		IconedTextFieled(carBrand, carBrandt);
		HBox h4 = new HBox(carBrand, carBrandt);
		h4.setAlignment(Pos.CENTER_LEFT);

		Label carModel = new Label("Car Model :        ");
		carModel.setPadding(new Insets(7));
		carModel.setMinWidth(180);
		TextField carModelt = new TextField();
		carModelt.setMinWidth(250);
		IconedTextFieled(carModel, carModelt);
		HBox h5 = new HBox(carModel, carModelt);
		h5.setAlignment(Pos.CENTER_LEFT);

		Label carColor = new Label("Car Color :   ");
		carColor.setPadding(new Insets(7));
		carColor.setMinWidth(180);
		TextField carColort = new TextField();
		carColort.setMinWidth(250);
		IconedTextFieled(carColor, carColort);
		HBox h9 = new HBox(carColor, carColort);
		h9.setAlignment(Pos.CENTER_LEFT);

		Label transmissionType = new Label("Transmission Type : ");
		transmissionType.setPadding(new Insets(7));
		transmissionType.setMinWidth(180);

		RadioButton gear = new RadioButton("Gear");
		RadioButton Automatic = new RadioButton("Automatic");
		ToggleGroup tg = new ToggleGroup();
		gear.setToggleGroup(tg);
		Automatic.setToggleGroup(tg);
		gear.setFont(new Font("Times New Roman", 14));
		Automatic.setFont(new Font("Times New Roman", 14));
		HBox tBox = new HBox(50, gear, Automatic);
		tBox.setMinWidth(250);
		tBox.setAlignment(Pos.CENTER);

		IconedTextFieled(transmissionType, tBox);
		HBox h8 = new HBox(transmissionType, tBox);
		h8.setAlignment(Pos.CENTER_LEFT);

		Label numOfPassengers = new Label("Number of Passengers : ");
		numOfPassengers.setPadding(new Insets(7));
		numOfPassengers.setMinWidth(180);
		TextField numOfPassengerst = new TextField();
		numOfPassengerst.setMinWidth(250);
		IconedTextFieled(numOfPassengers, numOfPassengerst);
		HBox h6 = new HBox(numOfPassengers, numOfPassengerst);
		h6.setAlignment(Pos.CENTER_LEFT);

		Label numOfDoors = new Label("Num Of Doors : ");
		numOfDoors.setPadding(new Insets(7));
		numOfDoors.setMinWidth(180);
		TextField numOfDoorst = new TextField();
		numOfDoorst.setMinWidth(250);
		IconedTextFieled(numOfDoors, numOfDoorst);
		HBox h10 = new HBox(numOfDoors, numOfDoorst);
		h10.setAlignment(Pos.CENTER_LEFT);

		Label petrolType = new Label("Petrol Type : ");
		petrolType.setPadding(new Insets(7));
		petrolType.setMinWidth(180);

		RadioButton disel = new RadioButton("Disel");
		RadioButton petrol = new RadioButton("Petrol");
		ToggleGroup tg1 = new ToggleGroup();
		disel.setToggleGroup(tg1);
		petrol.setToggleGroup(tg1);
		disel.setFont(new Font("Times New Roman", 14));
		petrol.setFont(new Font("Times New Roman", 14));
		HBox pBox = new HBox(50, disel, petrol);
		pBox.setMinWidth(250);
		pBox.setAlignment(Pos.CENTER);

		IconedTextFieled(petrolType, pBox);
		HBox h11 = new HBox(petrolType, pBox);
		h11.setAlignment(Pos.CENTER_LEFT);

		Label priceWithoutCustoms = new Label("Price Without Customs : ");
		priceWithoutCustoms.setPadding(new Insets(7));
		priceWithoutCustoms.setMinWidth(180);
		TextField priceWithoutCustomst = new TextField();
		priceWithoutCustomst.setMinWidth(250);
		IconedTextFieled(priceWithoutCustoms, priceWithoutCustomst);
		HBox h12 = new HBox(priceWithoutCustoms, priceWithoutCustomst);
		h12.setAlignment(Pos.CENTER_LEFT);

		Label rentPrice = new Label("Rent Price : ");
		rentPrice.setPadding(new Insets(7));
		rentPrice.setMinWidth(180);
		TextField rentPricet = new TextField();
		rentPricet.setMinWidth(250);
		IconedTextFieled(rentPrice, rentPricet);
		HBox h13 = new HBox(rentPrice, rentPricet);
		h13.setAlignment(Pos.CENTER_LEFT);

		Label year = new Label("Year : ");
		year.setPadding(new Insets(7));
		year.setMinWidth(180);
		TextField yeart = new TextField();
		yeart.setMinWidth(250);
		IconedTextFieled(year, yeart);
		HBox h14 = new HBox(year, yeart);
		h14.setAlignment(Pos.CENTER_LEFT);

		VBox v = new VBox(25, h4, h5, h3, h6, h7, h8);
		v.setAlignment(Pos.CENTER_LEFT);
		v.setPadding(new Insets(30));

		VBox v2 = new VBox(25, h9, h10, h11, h12, h13, h14);
		v2.setAlignment(Pos.CENTER_LEFT);
		v2.setPadding(new Insets(30));

		HBox tow = new HBox(15, v, v2);
		tow.setAlignment(Pos.CENTER_LEFT);
		tow.setPadding(new Insets(30));

		HBox mix = new HBox(60, tow);
		mix.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("addCar.png"));
		a.setFitHeight(70);
		a.setFitWidth(80);
		Button add = new Button("Add Car", a);
		add.setPrefSize(200, 100);
		icons(add);
		butoonEffect(add);
		add.setEffect(new DropShadow());

		add.setOnAction(e -> {
			try {
				char tranSelect = ((RadioButton) tg.getSelectedToggle()).getText().charAt(0);
				char petSelect = ((RadioButton) tg1.getSelectedToggle()).getText().charAt(0);

				applyOnDataBase(
						"insert into VEHICLES(OPTIONS,PRICE_WIHTOUT_CUSTOMS,TRANSMISSION_TYPE,NUM_OF_PASS,PETROL_TYPE,CAR_MODEL,CAR_COLOR"
								+ ",NUM_OF_DOORS,CAR_BRAND,RENT_PRICE,EMP_ID,year) value ('" + optionst.getText()
								+ "', " + priceWithoutCustomst.getText() + ", '" + tranSelect + "', "
								+ numOfPassengerst.getText() + ", '" + petSelect + "', '" + carModelt.getText() + "','"
								+ carColort.getText() + "'," + numOfDoorst.getText() + ",'" + carBrandt.getText() + "',"
								+ rentPricet.getText() + ","
								+ empIdt.getValue().substring(0, empIdt.getValue().indexOf(',')) + "," + yeart.getText()
								+ ");");

				optionst.clear();
				priceWithoutCustomst.clear();
				tg.selectToggle(null);
				numOfPassengerst.clear();
				tg1.selectToggle(null);
				carModelt.clear();
				empIdt.setValue("");
				carColort.clear();
				numOfDoorst.clear();
				carBrandt.clear();
				rentPricet.clear();
				yeart.clear();

			} catch (Exception e1) {
				error.setContentText("Car not add! there are somthing wrong!!");
				error.show();
			}

		});

		ImageView s = new ImageView(new Image("serchCar.png"));
		s.setFitHeight(70);
		s.setFitWidth(80);

		ImageView b = new ImageView(new Image("back.png"));
		b.setFitHeight(67);
		b.setFitWidth(80);
		Button back = new Button("Back to\n car page", b);
		back.setPrefSize(200, 100);
		icons(back);
		butoonEffect(back);
		back.setEffect(new DropShadow());

		back.setOnAction(e -> {
			carButtonAction(primaryStage);
		});

		HBox h = new HBox(50, add, back);
		h.setAlignment(Pos.CENTER);

		VBox v1 = new VBox(50, mix, h);
		v1.setAlignment(Pos.CENTER);

		pane2.setCenter(v1);

		pane.setCenter(pane2);
		VBox buttons = menue(primaryStage);
		pane.setLeft(buttons);
		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void butoonEffect(Node b) {
		b.setOnMouseMoved(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + " -fx-text-fill: #CE2029;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});

		b.setOnMouseExited(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + " -fx-text-fill: #f2f3f4;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});
	}

	private void icons(Node l) {
		l.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + " -fx-text-fill: #f2f3f4;\n" + "-fx-background-color: transparent;\n"
				+ "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
	}

	private TableView<Customer> tableCustomer() {
		TableView<Customer> table = new TableView<Customer>();

		table.setEditable(false);

		TableColumn<Customer, Integer> CusId = new TableColumn<Customer, Integer>("Customer ID");
		CusId.setMinWidth(150);
		CusId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCusId()).asObject());
		CusId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> cusName = new TableColumn<Customer, String>("Customer Name");
		cusName.setMinWidth(160);
		cusName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCusName()));
		cusName.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> address = new TableColumn<Customer, String>("Address");
		address.setMinWidth(160);
		address.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getAddress()));
		address.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, String> carrier = new TableColumn<Customer, String>("Carrier");
		carrier.setMinWidth(100);
		carrier.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCarrier()));
		carrier.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> budget = new TableColumn<Customer, Integer>("Budget");
		budget.setMinWidth(200);
		budget.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getBudget()).asObject());
		budget.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> empId = new TableColumn<Customer, Integer>("Employee ID");
		empId.setMinWidth(160);
		empId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getEmpId()).asObject());
		empId.setStyle("-fx-alignment: CENTER;");

		TableColumn<Customer, Integer> cusPnum = new TableColumn<Customer, Integer>("Customer Phone Numbers");
		cusPnum.setMinWidth(200);
		cusPnum.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getCusPnum()).asObject());
		cusPnum.setStyle("-fx-alignment: CENTER;");

		customers.clear();
		ResultSet rs = appyQueryOnDataBase("select * from customers;");

		try {
			while (rs.next()) {
				customers.add(new Customer(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
						Integer.parseInt(rs.getString(4)), rs.getString(5), Integer.parseInt(rs.getString(6)),
						Integer.parseInt(rs.getString(7))));
			}
		} catch (NumberFormatException | SQLException e1) {
			error.setContentText(e1.getMessage());
			error.show();
		}
		ObservableList<Customer> data = FXCollections.observableArrayList(customers);

		table.setItems(data);
		table.setMaxWidth(1150);
		table.setMinHeight(400);
		table.getColumns().addAll(CusId, cusName, address, budget, carrier, empId, cusPnum);

		return table;
	}

	private void styleTitle(Node n) {
		n.setStyle("-fx-font-size: 30;\n" + "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
				+ " -fx-text-fill: black;\n");
	}

	private LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}
}
