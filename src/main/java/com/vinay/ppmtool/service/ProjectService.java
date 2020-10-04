package com.vinay.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinay.ppmtool.domain.Backlog;
import com.vinay.ppmtool.domain.Project;
import com.vinay.ppmtool.domain.User;
import com.vinay.ppmtool.exceptions.BacklogNotFoundException;
import com.vinay.ppmtool.exceptions.ProjectIdException;
import com.vinay.ppmtool.repositories.BacklogRepository;
import com.vinay.ppmtool.repositories.ProjectRepository;
import com.vinay.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backLogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdate(Project project, String username) {
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new BacklogNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new BacklogNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}
		try {

			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if (project.getId() == null) {
				Backlog backLog = new Backlog();
				project.setBacklog(backLog);
				backLog.setProject(project);
				backLog.setProjectIdentifier(project.getProjectIdentifier());
			}

			if (project.getId() != null) {
				project.setBacklog(backLogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project identifier " + project.getProjectIdentifier() + " already taken");
		}
	}

	public Project findProjectByIdentifier(String projectId, String username) {

		Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project with identifier '" + projectId + "' doesn't exists");

		if(!project.getProjectLeader().equals(username)){
            throw new BacklogNotFoundException("Project not found in your account");
        }

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProject(String projectId, String username) {

		projectRepository.delete(findProjectByIdentifier(projectId, username));
	}

}
