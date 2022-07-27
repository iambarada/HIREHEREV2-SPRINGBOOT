package com.centroxy.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import com.centroxy.generators.IdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:27:51 pm
 */

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_generator")
	@GenericGenerator(name = "project_id_generator", strategy = "com.centroxy.generators.IdGenerator", parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "CENPRJ"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d") })
	private String projectId;

	@NotEmpty(message="Project name shouldn't be empty")
	private String projectName;
	
	@NotEmpty(message="Client name shouldn't be empty")
	private String clientName;
	
	@NotEmpty(message="Client location shouldn't be empty")
	private String clientLocation;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate projectStartDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate projectEndDate;
	
	@Lob
	private byte[] projectLogo;

	
	@CreatedDate
	@Column(name = "creation_date_time", updatable = false)
	private LocalDateTime creationDateTime;
	
	
	@LastModifiedDate
	private LocalDateTime updationDateTime;

}
