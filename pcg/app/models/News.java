package models;

import java.util.GregorianCalendar;

public class News {

	private java.sql.Timestamp timestamp;

	private String message;
	private String link;
	private String title;

	public News(String message, String link, String title) {
		timestamp = new java.sql.Timestamp(new GregorianCalendar()
				.getTimeInMillis());
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
		return timestamp.toLocaleString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
