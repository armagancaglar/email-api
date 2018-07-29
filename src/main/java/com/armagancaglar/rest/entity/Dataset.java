package com.armagancaglar.rest.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
//Dataset for getting datas from XML
@XmlRootElement(name = "dataset")
public class Dataset {
	  
	private  List<String> emails;
	private  List<String> resources;
	
	public Dataset() {}
	
	@XmlElementWrapper(name="emails")
	@XmlElement(name="email")
	public List<String> getEmails() {
		return emails;
	}
	
	public void setEmails(List<String> emails) {
	    this.emails = emails;
	}
	
	@XmlElementWrapper(name="resources")
	@XmlElement(name="url")
	public List<String> getResources() {
		return resources;
	}
	
	public void setResources(List<String> urls) {
	    this.resources = urls;
	}	   
}
