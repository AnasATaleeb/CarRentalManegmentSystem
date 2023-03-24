package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
	private int empID;
	private String name;
	private String address;
	private int empPnum;
	private Date dateOFBirth;
	private Date startWork;
	private int baseSalary;
	private double comissionRate;
	private int yearlySales;
	
	public Employee(int empID, String name, String address, int empPnum, Date dateOFBirth,
			Date startWork, int baseSalary, double comissionRate, int yearlySales) {
		super();
		this.empID = empID;
		this.name = name;
		this.address = address;
		this.empPnum = empPnum;
		this.dateOFBirth = dateOFBirth;
		this.startWork = startWork;
		this.baseSalary = baseSalary;
		this.comissionRate = comissionRate;
		this.yearlySales = yearlySales;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getEmpPnum() {
		return empPnum;
	}

	public void setEmpPnum(int empPnum) {
		this.empPnum = empPnum;
	}

	public String getDateOFBirth() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormatted = fmt.format(dateOFBirth);
		return dateFormatted;
	}

	public void setDateOFBirth(Date dateOFBirth) {
		this.dateOFBirth = dateOFBirth;
	}

	public String getStartWork() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormatted = fmt.format(startWork);
		return dateFormatted;
	}

	public void setStartWork(Date startWork) {
		this.startWork = startWork;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getComissionRate() {
		return comissionRate;
	}

	public void setComissionRate(double comissionRate) {
		this.comissionRate = comissionRate;
	}

	public int getYearlySales() {
		return yearlySales;
	}

	public void setYearlySales(int yearlySales) {
		this.yearlySales = yearlySales;
	}

	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", name=" + name + ", address=" + address + ", empPnum=" + empPnum
				+ ", dateOFBirth=" + dateOFBirth + ", startWork=" + startWork + ", baseSalary=" + baseSalary
				+ ", comissionRate=" + comissionRate + ", yearlySales=" + yearlySales + "]";
	}
	

}
