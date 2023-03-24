package application;

public class NonRentCar extends Vehicles{

	public NonRentCar(int carId, String options, int priceWithoutCustoms, char transmissionType, int numOfPassengers,
			char petrolType, String carModel, String carColor, int numOfDoors, String carBrand, int rentPrice,
			int empId, int year) {
		super(carId, options, priceWithoutCustoms, transmissionType, numOfPassengers, petrolType, carModel, carColor,
				numOfDoors, carBrand, rentPrice, empId, year);
	}
	
}
