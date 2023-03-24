package application;

public class Vehicles {
	protected int empId;
	protected int carId; 
	protected String options;
	protected String carBrand;
	protected String carModel;
	protected String carColor;
	protected char transmissionType;
	protected int numOfPassengers;
	protected int numOfDoors;
	protected char petrolType;
	protected int priceWithoutCustoms;
	protected int rentPrice;
	protected int year;

	
	public Vehicles(int carId, String options, int priceWithoutCustoms, char transmissionType, int numOfPassengers,
			char petrolType, String carModel, String carColor, int numOfDoors, String carBrand, 
			int rentPrice, int empId, int year) {
		super();
		this.carId = carId;
		this.options = options;
		this.priceWithoutCustoms = priceWithoutCustoms;
		this.transmissionType = transmissionType;
		this.numOfPassengers = numOfPassengers;
		this.petrolType = petrolType;
		this.carModel = carModel;
		this.carColor = carColor;
		this.numOfDoors = numOfDoors;
		this.carBrand = carBrand;
		this.rentPrice = rentPrice;
		this.empId = empId;
		this.year = year;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public int getPriceWithoutCustoms() {
		return priceWithoutCustoms;
	}

	public void setPriceWithoutCustoms(int priceWithoutCustoms) {
		this.priceWithoutCustoms = priceWithoutCustoms;
	}

	public char getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(char transmissionType) {
		this.transmissionType = transmissionType;
	}

	public int getNumOfPassengers() {
		return numOfPassengers;
	}

	public void setNumOfPassengers(int numOfPassengers) {
		this.numOfPassengers = numOfPassengers;
	}

	public char getPetrolType() {
		return petrolType;
	}

	public void setPetrolType(char petrolType) {
		this.petrolType = petrolType;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public int getNumOfDoors() {
		return numOfDoors;
	}

	public void setNumOfDoors(int numOfDoors) {
		this.numOfDoors = numOfDoors;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	
	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
