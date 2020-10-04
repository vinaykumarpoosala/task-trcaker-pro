package com.vinay.ppmtool.web;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinay.ppmtool.domain.Project;
import com.vinay.ppmtool.service.ProjectService;
import com.vinay.ppmtool.service.ValidationErrorMapService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ProjectController {

	@Autowired
	private ProjectService service;

	@Autowired
	private ValidationErrorMapService errorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result ,
			Principal principal) {

		ResponseEntity<?> errorMap = errorService.mapForErrors(result);
		if (errorMap != null)
			return errorMap;
		Project project1 = service.saveOrUpdate(project,principal.getName());
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> findProject(@PathVariable String projectId , Principal principal) {

		return new ResponseEntity<Project>(service.findProjectByIdentifier(projectId , principal.getName()), HttpStatus.OK);

	}

	@GetMapping("/all")
	public Iterable<Project> findAllProjects(Principal principal) {

		return service.findAllProjects(principal.getName());
	}

	@DeleteMapping("/{projectId}")

	public ResponseEntity<?> deleteProject(@PathVariable String projectId , Principal principal) {
		service.deleteProject(projectId , principal.getName());
		return new ResponseEntity<String>("Project with Id '" + projectId + "' has been deleted", HttpStatus.OK);
	}

}
