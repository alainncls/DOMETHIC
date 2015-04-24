package fr.epf.medfile.models;

import java.util.Date;

import fr.epf.medfile.models.Patient.Sex;

public class User {
	// Final Informations
	private int ID;
	private String firstName;
	private String lastName;
	private String cps;
	private Date birthDate;
	private Sex sex;
	private String service;

	// Modifiable Informations
	private String address;
	private String city;
	private String zipCode;
	private String email;
	private String phoneNumber;
    private String healthInfos;
    private String birthAddress;
	
	private String password;
	private String login;

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", firstName=" + firstName + ", lastName="
				+ lastName + ", cps=" + cps + ", birthDate=" + birthDate
				+ ", sex=" + sex + ", service=" + service + ", address="
				+ address + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ "]";
	}

	/* Getters and Setters */
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCps() {
		return cps;
	}

	public void setCps(String cps) {
		this.cps = cps;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getHealthInfos() {
		return healthInfos;
	}

	public void setHealthInfos(String healthInfos) {
		this.healthInfos = healthInfos;
	}

	public String getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}
}
