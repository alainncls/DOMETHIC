package fr.epf.medfile.models;

import java.util.Date;

public class Patient {

    public enum Sex {
	MALE("Male"), FEMALE("Female");

	private String value;

	Sex(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}
    }

    /* Attributes */
    // Final Informations
    private int ID;

    private String firstName;
    private String lastName;
    private String socialSecurity;
    private Date birthDate;
    private Sex sex;

    // Modifiable Informations
    private String address;
	private String city;
	private String zipCode;
    private String email;
    private String phoneNumber;
    private Date entryDate;
    private String causes;
    private String service;
    private String healthInfos;
    private String birthAddress;

    /* Constructors */
    // Empty Constructor
    public Patient() {
    }

	@Override
	public String toString() {
		return "Patient [ID=" + ID + ", firstName=" + firstName + ", lastName="
				+ lastName + ", socialSecurity=" + socialSecurity
				+ ", birthDate=" + birthDate + ", sex=" + sex + ", address="
				+ address + ", city=" + city + ", zipCode=" + zipCode
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", entryDate=" + entryDate + ", causes=" + causes
				+ ", service=" + service + ", healthInfos=" + healthInfos
				+ ", birthAddress=" + birthAddress + "]";
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

    public String getSocialSecurity() {
	return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
	this.socialSecurity = socialSecurity;
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

	public String getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getHealthInfos() {
		return healthInfos;
	}

	public void setHealthInfos(String healthInfos) {
		this.healthInfos = healthInfos;
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
    
    
}
