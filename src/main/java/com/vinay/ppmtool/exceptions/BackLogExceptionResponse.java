package com.vinay.ppmtool.exceptions;

public class BackLogExceptionResponse {

	private String ProjectNotFound;

	public BackLogExceptionResponse(String ProjectNotFound) {
		super();
		this.ProjectNotFound = ProjectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String ProjectNotFound) {
		this.ProjectNotFound = ProjectNotFound;
	}
	
}
