package com.centroxy.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.centroxy.generators.IdGenerator;

import lombok.Data;

/**
 * @author Mahesh 11-Jul-2022
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class InterviewPanel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_panel_id_generator")
	@GenericGenerator(name = "interview_panel_id_generator", strategy = "com.centroxy.generators.IdGenerator", parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "INTRPNL"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") })
	private String panelId;
	private String panelFor;
	@CreatedDate
	@Column(name = "creation_date_time", nullable = false, updatable = false)
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime updatedDate;
	@OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.MERGE)
	@JoinColumn(name = "panelId")
	private List<Employee> employee;
}
