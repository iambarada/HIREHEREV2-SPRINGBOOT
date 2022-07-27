package com.centroxy.entities;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.centroxy.generators.IdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:27:51 pm
 */
@Data
@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_id_generator")
    @GenericGenerator(name = "interview_id_generator", strategy = "com.centroxy.generators.IdGenerator", parameters = {
            @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "INTR"),
            @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d") })
    private String interviewId;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "panel_id")
    @NotNull(message = "Interview Panel  shouldn't be empty")
    private InterviewPanel interviewedBy;

    @NotEmpty(message = "Candidate Name shouldn't be empty")
    private String candidateName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "jd_Id")
    @NotNull(message = "Job Description shouldn't be empty")
    private JobDescription jobDescription;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Interview Date And Time shouldn't be empty")
    private LocalDateTime interviewDateAndTime;

    private boolean isSelected;

   
    private String remarks;

    @Lob
    private byte[] resume;

}