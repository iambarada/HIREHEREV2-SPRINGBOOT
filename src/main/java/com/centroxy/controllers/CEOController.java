package com.centroxy.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
public class CEOController {

	private ICEOService ceoService;

	private JobDescriptionService jobDescriptionService;

	@Autowired
	public CEOController(ICEOService ceoService, JobDescriptionService jobDescriptionService) {
		this.ceoService = ceoService;
		this.jobDescriptionService = jobDescriptionService;
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
		System.out.println(jobDescriptionObj);
		if (status.equals("accept")) {
			jobDescriptionObj.setApproved(true);
			jobDescriptionService.saveJd(jobDescriptionObj);
			return ResponseEntity.ok("Resource demand accepted");

		} else {
			jobDescriptionObj.setApproved(false);
			jobDescriptionService.saveJd(jobDescriptionObj);
			return ResponseEntity.ok("Resource demand rejected");

		}
	}

}
