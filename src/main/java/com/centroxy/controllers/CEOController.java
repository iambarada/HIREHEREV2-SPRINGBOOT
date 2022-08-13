package com.centroxy.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.centroxy.entities.JobDescription;
import com.centroxy.entities.Project;
import com.centroxy.services.ICEOService;
import com.centroxy.services.JobDescriptionService;

/**
 * @author Jogesh Krupa Dash 06-Jul-2022 7:24:33 pm
 */
@RestController
@RequestMapping("/ceo")
@CrossOrigin(origins = "http://localhost:4200")
public class CEOController {

	private final ICEOService ceoService;
	private final JobDescriptionService jobDescriptionService;
	private final INotificationService notificationService;
	private final SimpMessagingTemplate template;
	private final Notification notification;


	@Autowired
	public CEOController(ICEOService ceoService, JobDescriptionService jobDescriptionService, INotificationService notificationService,SimpMessagingTemplate template,Notification notification) {
		this.ceoService = ceoService;
		this.jobDescriptionService = jobDescriptionService;
		this.notificationService = notificationService;
		this.template= template;
		this.notification =notification;
	}

	// To add new project by CEO
	// here we are passing both json & multipart form data
	@PostMapping(value = "/saveProject", consumes = { "application/json", "multipart/form-data" })
	public ResponseEntity<String> saveProject(@Valid @RequestPart("project") Project project,
			@RequestParam("projectLogo") MultipartFile projectLogo) throws IOException {
		project.setProjectLogo(projectLogo.getBytes());
		Project addedProject = ceoService.addNewProject(project);
		if (addedProject != null) {
			return new ResponseEntity<String>("Project added successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Project Adding Failed", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @author Subhasmita Panda 07-July-2022 11:47:27 am
	 */
	// For Fetching All Demands
	@GetMapping("/demands")
	public ResponseEntity<List<JobDescription>> fetchDemands() {
		List<JobDescription> demands = jobDescriptionService.getDemands();
		return ResponseEntity.ok(demands);
	}

	@MessageMapping("/extractAllDemands")
	public void fetchAllDemands() throws Exception {
		List<JobDescription> demands = jobDescriptionService.getDemands();
		JSONObject details = new JSONObject();
		details.put("demands", demands);
		template.convertAndSend("/message/demands", details);
	}

	/**
	 * @author Subhasmita Panda 07-July-2022 11:47:27 am
	 */
	// For Fetching a particular demand by it's id
	@GetMapping("/demand/{jdId}")
	public ResponseEntity<JobDescription> fetchDemandById(@PathVariable String jdId) {
		JobDescription demandByJdId = jobDescriptionService.getDemandById(jdId);
		return ResponseEntity.ok(demandByJdId);
	}

	/**
	 * @author Sumita Sethy 18-July-2022 11:47:27 am
	 */
	// api for accept or reject the demandresource by CEO
	@GetMapping(value = "/set/{status}/{id}")
	public ResponseEntity<String> demandStatus(@PathVariable String status, @PathVariable String id) {
		JobDescription jobDescriptionObj = jobDescriptionService.getDemandById(id);
		if (status.equals("accept")) {
			jobDescriptionObj.setIsApproved("Approved");
			JobDescription approvedJD = jobDescriptionService.saveJd(jobDescriptionObj);
			notification.setId(Notification.generateId());
			notification.setCausedFor("Demand Approved");
			notification.setTriggeredBy("CEO");
			notification.setReceivedBy("PM,HR");
			notification.setDateTime(LocalDateTime.now());
			notification.setIsRead(false);
			notification.setJd(approvedJD);
			notificationService.saveNotification(notification);
			return ResponseEntity.ok("Resource demand accepted");
		} else {
			jobDescriptionObj.setIsApproved("Rejected");
			JobDescription rejectedJD = jobDescriptionService.saveJd(jobDescriptionObj);
			notification.setId(Notification.generateId());
			notification.setCausedFor("Demand Rejected");
			notification.setTriggeredBy("CEO");
			notification.setReceivedBy("PM");
			notification.setDateTime(LocalDateTime.now());
			notification.setIsRead(false);
			notification.setJd(rejectedJD);
			notificationService.saveNotification(notification);
			return ResponseEntity.ok("Resource demand rejected");
		}
	}

	@MessageMapping("/extractAllCEONotifications")
	public void fetchAllNotifications() throws Exception {
		List<Notification> notifications = notificationService.fetchAllNotificationsForCEO();
		JSONObject details = new JSONObject();
		details.put("notifications", notifications);
		System.out.println("notifications"+notifications);
		template.convertAndSend("/message/ceo/notifications", details);
	}

}
