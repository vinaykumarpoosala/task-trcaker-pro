package com.vinay.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinay.ppmtool.domain.User;
import com.vinay.ppmtool.exceptions.UserNameAlreadyExistsException;
import com.vinay.ppmtool.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User user) {

		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			return userRepository.save(user);
		} catch (Exception ex) {

			throw new UserNameAlreadyExistsException("Username already exists .");
		}
	}
}
