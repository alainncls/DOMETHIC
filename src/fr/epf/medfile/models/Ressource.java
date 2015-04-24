package fr.epf.medfile.models;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.graphics.Bitmap;

public class Ressource {
    private int id;
    private int idPatient;
    private int idAuthor;
    private String title;
    private String type;
    private String category;
    private String text;
    private Bitmap img;
    private Date date;

    public Ressource() {

    }

    @Override
    public String toString() {
	return "Ressource [id=" + id + ", idPatient=" + idPatient + ", idAuthor=" + idAuthor + ", title=" + title + ", type=" + type + ", category=" + category + ", text=" + text + ", img=" + img + ", date=" + date + "]";
    }

    /* Getter et Setter */
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getIdPatient() {
	return idPatient;
    }

    public void setIdPatient(int idPatient) {
	this.idPatient = idPatient;
    }

    public int getIdAuthor() {
	return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
	this.idAuthor = idAuthor;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Bitmap getImg() {
	return img;
    }

    public void setImg(Bitmap img) {
	this.img = img;
    }

    public byte[] getImgAsByte() {
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	img.compress(Bitmap.CompressFormat.PNG, 100, stream);
	return stream.toByteArray();
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

}
