package com.vinay.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vinay.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Project findProjectByProjectIdentifier(String projectId);

	Project findByProjectIdentifier(String projectIdentifier);

	Iterable<Project> findAllByProjectLeader(String username);

}
