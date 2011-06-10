package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    private Date timestamp = new Date();
    private SimpleDateFormat format = new SimpleDateFormat(
	    "dd/MM/yyyy kk:mm:ss z");
    private String message;
    private String link;
    private String title;

    public News(String message, String link, String title) {

	this.message = message;
	this.link = link;
	this.title = title;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getLink() {
	return link;
    }

    public void setLink(String link) {
	this.link = link;
    }

    public String getTimestamp() {
	return format.format(timestamp);
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
