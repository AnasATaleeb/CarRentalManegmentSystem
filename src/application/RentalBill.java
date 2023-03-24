package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalBill {
	private int car_id;
	private int emp_id;
	private int cus_id;
	private int pay_id;
	private double price;
	private Date pay_date;

	public RentalBill() {
	}

	public RentalBill(int car_id, int emp_id, int cus_id, int pay_id, double price, Date pay_date) {
		super();
		this.car_id = car_id;
		this.emp_id = emp_id;
		this.cus_id = cus_id;
		this.pay_id = pay_id;
		this.price = price;
		this.pay_date = pay_date;
	}

	public int getCar_id() {
		return car_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public int getPay_id() {
		return pay_id;
	}

	public void setPay_id(int pay_id) {
		this.pay_id = pay_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPay_date() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormatted = fmt.format(pay_date);
		return dateFormatted;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public int getCus_id() {
		return cus_id;
	}

	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}

}
