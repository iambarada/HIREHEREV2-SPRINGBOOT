package com.centroxy.controllers;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.centroxy.entities.Project;
import com.centroxy.services.ICEOService;

@Controller
public class ProjectController {
	@Autowired
	private ICEOService ceoService;

	private final SimpMessagingTemplate template;

	@Autowired
	public ProjectController(ICEOService ceoService, SimpMessagingTemplate template) {
		this.ceoService = ceoService;
		this.template = template;
	}

	/**
	 * 
	 * @author ${Badal Kumar Sahoo} Jul 7, 2022 11:51:17 AM
	 * 
	 */

	// This method is used for fetch Project by ProjectId
	@GetMapping("/project/{projectId}")
	public ResponseEntity<Project> findProjectById(@PathVariable("projectId") String id) {

		Project project = ceoService.findProjectById(id);

		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	/**
	 * 
	 * @author Badal 13-Jul-2022 2:29:35 pm
	 * 
	 */

	// This method is used for get a Project by projectId by using websocket

	@SuppressWarnings("unchecked")
	@MessageMapping("/getProject/{projectId}")
	public void getProjectById(@DestinationVariable String projectId) throws Exception {

		Project project = ceoService.findProjectById(projectId);

		JSONObject details = new JSONObject();

		details.put("project", project);

		template.convertAndSend("/message/projectById", details);

	}

	/**
	 * 
	 * @author ${Badal Kumar Sahoo} Jul 7, 2022 11:51:17 AM
	 * 
	 */

	// This method is used for fetch all the projects

	@GetMapping("/allprojects")

	public ResponseEntity<List<Project>> fetchAllProjects() {

		List<Project> allProjects = ceoService.fetchAllProjects();

		return new ResponseEntity<>(allProjects, HttpStatus.OK);

	}

	/**
	 * 
	 * @author Badal 13-Jul-2022 3:38:20 pm
	 * 
	 */
	// This method is used for get all Projects by using websocket
	@SuppressWarnings("unchecked")
	@MessageMapping("/getAllProject")
	public void getAllProjects() throws Exception {
		List<Project> allProjects = ceoService.fetchAllProjects();
		JSONObject details = new JSONObject();
		details.put("allProjects", allProjects);
		template.convertAndSend("/message/allProject", details);
	}

}
