package com.ireceptorplus.ireceptorchainclient.FileStorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = null;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
