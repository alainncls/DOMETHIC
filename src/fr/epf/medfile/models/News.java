package fr.epf.medfile.models;

import java.util.Date;

public class News {

    // Attributes
    private int ID;
    private int patientID;
    private int authorID;
    private Date date;
    private String title;
    private String content;

    public News() {

    }

	@Override
    public String toString() {
	return "News [ID=" + ID + ", patientID=" + patientID + ", date=" + date + ", title=" + title + ", content=" + content + "]";
    }

    public int getID() {
	return ID;
    }

    public void setID(int id) {
	ID = id;
    }

    public int getPatientID() {
	return patientID;
    }

    public void setPatientID(int patientID) {
	this.patientID = patientID;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }
    
    public int getAuthorID(){
    	return authorID;
    }

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
}
