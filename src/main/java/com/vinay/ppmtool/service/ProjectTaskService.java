package com.vinay.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinay.ppmtool.domain.Backlog;
import com.vinay.ppmtool.domain.Project;
import com.vinay.ppmtool.domain.ProjectTask;
import com.vinay.ppmtool.exceptions.BacklogNotFoundException;
import com.vinay.ppmtool.repositories.BacklogRepository;
import com.vinay.ppmtool.repositories.ProjectRepository;
import com.vinay.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask , String username) {

		//PTs to be added to a specific project, project != null, BL exists
        Backlog backlog =  projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the bl to pt
        System.out.println(backlog);
        projectTask.setBacklog(backlog);
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer BacklogSequence = backlog.getPTSequence();
        // Update the BL SEQUENCE
        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);

        //Add Sequence to Project Task
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority when priority null

        //INITIAL status when status is null
        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        //Fix bug with priority in Spring Boot Server, needs to check null first
        if(projectTask.getPriority()==null||projectTask.getPriority()==0){ //In the future we need projectTask.getPriority()== 0 to handle the form
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> getAllTasks(String backlog_id) {

		Project project = projectRepository.findProjectByProjectIdentifier(backlog_id);

		if (null == project)
			throw new BacklogNotFoundException("No Project Exists With '" + backlog_id + "' Id.");
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        //make sure we are searching on an existing backlog
        projectService.findProjectByIdentifier(backlog_id, username);


        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new BacklogNotFoundException("Project Task '"+pt_id+"' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new BacklogNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }


        return projectTask;
    }
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

	 public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
	        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
	        projectTaskRepository.delete(projectTask);
	    }
}
