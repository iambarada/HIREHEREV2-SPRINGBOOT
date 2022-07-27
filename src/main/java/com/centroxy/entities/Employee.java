package com.centroxy.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.centroxy.generators.IdGenerator;
import lombok.Data;

/**
 * @author CHINMAYEE MOHAPATRA 7:10:42 pm 06-Jul-2022
 */

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // @author Balab 08-Jul-2022 11:09:52 am
@Proxy(lazy = false)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Id_generator")
	@GenericGenerator(name = "Id_generator", strategy = "com.centroxy.generators.IdGenerator", parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "CEN"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") })

	private String empNo;
	@NotEmpty(message = "Employee Name shouldn't be empty")
	private String empName;
	@NotEmpty(message = "Employee Address shouldn't be empty")
	private String empAddress;
	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String empMobNo;
	@NotEmpty(message = "Employee Email shouldn't be empty")
	@Email
	private String empEmail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate empDob;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate empDoj;
	@NotEmpty(message = "Employee Designation shouldn't be empty")
	private String empDesignation; 
	@NotEmpty(message = "Reporting to shouldn't be empty")
	private String reportingTo;
	@NotEmpty(message = "Employee Department shouldn't be empty")
	private String empDepartment;
	@Lob
	private byte[] empProfileImage;

	private boolean isAvailable;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectId")
	private Project project;

	@CreatedDate
	@Column(name = "creation_date_time", updatable = false)
	private LocalDateTime creationDateTime;
	@LastModifiedDate
	private LocalDateTime updationDateTime;

}
