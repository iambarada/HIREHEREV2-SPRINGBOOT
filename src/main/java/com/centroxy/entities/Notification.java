package com.centroxy.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Component
@Table(name = "notification")
@Data
public class Notification {
	
	@Id
	private String id;
	private String triggeredBy;
	private String receivedBy;
	private String causedFor;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateTime;
	private Boolean isRead;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch= FetchType.LAZY , optional = true)
	@JoinColumn(name = "project_id")
	private Project projectDetails;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch= FetchType.LAZY , optional = true)
	@JoinColumn(name = "jd_id")
	private JobDescription jd;
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch= FetchType.LAZY , optional = true)
	@JoinColumn(name = "emp_id")
	private Employee emp;

	public static String generateId() {
		String id = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		return id;
	}
	
	public static Timestamp showDate() {
		
		Timestamp instant= Timestamp.from(Instant.now());
		
		return instant;
		
	}
	

	
	

}
