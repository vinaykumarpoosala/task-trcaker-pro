package com.vinay.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vinay.ppmtool.domain.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Long> {

	User  findByUsername(String username);
	
	User getById(Long id);
	
}
