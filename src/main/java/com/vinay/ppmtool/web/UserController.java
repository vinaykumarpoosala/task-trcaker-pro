package com.vinay.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinay.ppmtool.domain.User;
import com.vinay.ppmtool.payload.LoginRequest;
import com.vinay.ppmtool.payload.LoginResponse;
import com.vinay.ppmtool.security.JwtTokenProvider;
import com.vinay.ppmtool.service.UserService;
import com.vinay.ppmtool.service.ValidationErrorMapService;
import com.vinay.ppmtool.validators.UserValidator;

import static com.vinay.ppmtool.security.SecurityConstants.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private ValidationErrorMapService validationErrorMapService; 
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private UserValidator userValidator;
	
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest ,
			BindingResult result){
		ResponseEntity<?> errorMap = validationErrorMapService.mapForErrors(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername() , loginRequest.getPassword()
						)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX+jwtTokenProvider.generateToken(authentication);
		
		
		return  ResponseEntity.ok(new LoginResponse(true,jwt));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user ,
				BindingResult result){ 
		
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = validationErrorMapService.mapForErrors(result);
		if(errorMap != null) return errorMap;
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser , HttpStatus.CREATED);
		
	}
}
