package com.centroxy.controllers;

import java.util.List;

import javax.validation.Valid;

import com.centroxy.entities.Notification;
import com.centroxy.services.INotificationService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroxy.entities.Employee;
import com.centroxy.entities.JobDescription;
import com.centroxy.services.IPMService;
import com.centroxy.services.JobDescriptionService;

/**
 * @author Sumita Sethy 07-Jul-2022 11:48:03 am
 */
@RestController
@RequestMapping("/pm")
@CrossOrigin(origins = "http://localhost:4200")
public class PMController {

	private final JobDescriptionService jobDescriptionService;
	private final IPMService ipmService;
	private final SimpMessagingTemplate template;
	private final INotificationService notificationService;

	@Autowired
	public PMController(JobDescriptionService jobDescriptionService,SimpMessagingTemplate template, IPMService ipmService,INotificationService notificationService) {
		this.jobDescriptionService = jobDescriptionService;
		this.template= template;
		this.ipmService = ipmService;
		this.notificationService =notificationService;
	}

	// For Demanding Resource by PM
	@PostMapping("/demandresource")
	public ResponseEntity<String> createDemand(@Valid @RequestBody JobDescription jobDescription) {
		System.out.println(jobDescription);
		jobDescription.setIsApproved("Pending");
		JobDescription savedJD = jobDescriptionService.saveJd(jobDescription);
		if (savedJD != null) {
			return new ResponseEntity<String>("Demand Posted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Demand Posting Failed", HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * Badal 12-Jul-2022 5:44:12 pm
	 * 
	 */
	// This method is used for assign the Project to Employee
	@PostMapping("/assignProject/{projectId}")
	public ResponseEntity<String> assignProjectToEmployee(@RequestBody List<Employee> listOfEmp,
			@PathVariable String projectId) {
		String assignProject = ipmService.assignProject(listOfEmp, projectId);
		return new ResponseEntity<String>(assignProject, HttpStatus.OK);
	}

	@MessageMapping("/extractAllPMNotifications")
	public void fetchAllNotifications() throws Exception {
		List<Notification> notifications = notificationService.fetchAllNotificationsForPM();
		JSONObject details = new JSONObject();
		details.put("notifications", notifications);
		System.out.println("notifications"+notifications);
		template.convertAndSend("/message/pm/notifications", details);
	}

}
