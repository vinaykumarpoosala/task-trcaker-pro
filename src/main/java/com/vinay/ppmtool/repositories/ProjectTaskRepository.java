package com.vinay.ppmtool.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vinay.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {


	Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);

	ProjectTask findByProjectSequence(String pt_id);

}
