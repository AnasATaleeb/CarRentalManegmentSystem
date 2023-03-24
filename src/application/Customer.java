package application;

public class Customer {
	private String cusName;
	private String address;
	private int cusId;
	private int budget;
	private String carrier;
	private int cusPnum;
	private int empId;

	public Customer(String cusName, String address, int cusId, int budget, String carrier, int cusPnum, int empId) {
		super();
		this.cusName = cusName;
		this.address = address;
		this.cusId = cusId;
		this.budget = budget;
		this.carrier = carrier;
		this.cusPnum = cusPnum;
		this.empId = empId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCusId() {
		return cusId;
	}

	public void setCusId(int cusId) {
		this.cusId = cusId;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getCusPnum() {
		return cusPnum;
	}

	public void setCusPnum(int cusPnum) {
		this.cusPnum = cusPnum;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

}
