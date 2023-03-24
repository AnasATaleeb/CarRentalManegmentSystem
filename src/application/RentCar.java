package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RentCar extends Vehicles {
	private int cusId;
	private Date rentDate;
	private Date returnDate;

	public RentCar(int carId, String options, int priceWithoutCustoms, char transmissionType, int numOfPassengers,
			char petrolType, String carModel, String carColor, int numOfDoors, String carBrand, int rentPrice,
			int empId, int year, int cusId, Date rentDate, Date returnDate) {
		super(carId, options, priceWithoutCustoms, transmissionType, numOfPassengers, petrolType, carModel, carColor,
				numOfDoors, carBrand, rentPrice, empId, year);
		this.cusId = cusId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
	}

	public int getCusId() {
		return cusId;
	}

	public void setCusId(int cusId) {
		this.cusId = cusId;
	}

	public String getRentDate() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormatted = fmt.format(rentDate);
		return dateFormatted;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public String getReturnDate() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormatted = fmt.format(returnDate);
		return dateFormatted;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

}
