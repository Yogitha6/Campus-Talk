package com.campusTalk.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="EVENT")
public class Event {

	// Using Hibernate annotations to do the mapping between the object and the database columns
	@Id @Column(name="eventId", nullable=false)//primary key 
	private int eventId;
	
	@Column(name="description", nullable=false)
	private String eventDescription;
	
	@Column(name="name", nullable=false)
	private String eventName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="startDate", nullable=false)
	private Date eventStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="endDate", nullable=false)
	private Date eventEndDate;
	
	@Column(name="location", nullable=false)
	private String eventLocation;
	
	@Column(name="link", nullable=false)
	private String eventLink;
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(Date date) {
		this.eventStartDate = date;
	}
	public Date getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getEventLink() {
		return eventLink;
	}
	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
}