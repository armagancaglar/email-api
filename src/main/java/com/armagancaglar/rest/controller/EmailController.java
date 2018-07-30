package com.armagancaglar.rest.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.armagancaglar.rest.entity.Dataset;
import com.armagancaglar.rest.model.Email;
import com.armagancaglar.rest.service.EmailService;
import com.armagancaglar.rest.utils.Utility;



@RestController
@RequestMapping("email")
public class EmailController {

	static final String URL_EMAILS_XML = "http://localhost:8080/email/getxml";
	@Autowired
	private EmailService emailService;

	//Sending XML bulk to feth urls and emails
	@RequestMapping(path="/send-xml",method=RequestMethod.POST, consumes= "application/xml", produces="application/xml")
	public ResponseEntity<?> sendXML(@RequestBody Dataset dataset) {	
		//Fetching all urls
		if(dataset.getResources() != null) {
			for(String url : dataset.getResources()) {
				fetchUrlFromUrl(url);
			}
		}
		//Fetching emails in first XML
		if(dataset.getEmails() != null) {
			for(String email : dataset.getEmails()) {
				saveEmail(email);
			}
		}		
		return new ResponseEntity<>("All emails are fetched",HttpStatus.OK);
	}
	
	//Lists all emails
	@RequestMapping(path="/list-all-emails",method=RequestMethod.GET)
	public ResponseEntity<String> listAllEmails() {
		List<Email> eList = emailService.findAll();
		if(!eList.isEmpty()) {
			return new ResponseEntity<>(eList.stream()
					.map(e -> e.getEmail().toString())
					.collect(Collectors.joining("\n")), HttpStatus.OK);	
		}
		return new ResponseEntity<>("There is no email", HttpStatus.NOT_FOUND);
	}
	
	//Get Count of email
	@RequestMapping(path="/get-email-count", method=RequestMethod.GET)
	private ResponseEntity<?> getEmailCount(){
	    return new ResponseEntity<>(emailService.getCount(), HttpStatus.OK);
	}
	
	//Get single email occurence count
	@RequestMapping(path="/get-single-email-count/{email}",method=RequestMethod.GET)
	public ResponseEntity<?> getSingleEmailCount(@PathVariable("email") String email) {
		return new ResponseEntity<>(emailService.countByEmail(email), HttpStatus.OK);
	}	
	
	//Create single email
	@RequestMapping(path="/create/{email}",method=RequestMethod.POST)
	public ResponseEntity<?> createEmail(@PathVariable("email") String email) {
		if(Utility.emailValidator(email)) {
			return new ResponseEntity<>(saveEmail(email), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Email is not created", HttpStatus.NOT_ACCEPTABLE);
	}
	
	//Get single email by id
	@RequestMapping(path="/get-email/{id}",method=RequestMethod.GET)
	public ResponseEntity<String> getEmailById(@PathVariable("id") int id) {
		Email email = emailService.findByEmailId(id);
		if(email != null) {
			return new ResponseEntity<>(email.getEmail(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Email does not found", HttpStatus.NOT_FOUND);
	}
	
	//Delete single email by id
	@RequestMapping(path="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmail(@PathVariable("id") int id) {
		Email email = emailService.findByEmailId(id);
		if(email != null) {
			emailService.deleteEmail(email);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	//Update an email by id
	//Replaces email with given variable
	@RequestMapping(path="/update/{id}/{updateEmail}", method=RequestMethod.PATCH)
	public ResponseEntity<String> updateEmail(@PathVariable("id") int id, @PathVariable("updateEmail") String updateEmail) {
		if(Utility.emailValidator(updateEmail)) {
			if(emailService.updateByEmailId(id, updateEmail)) {
				return new ResponseEntity<>("Email has been updated", HttpStatus.OK);	
			} else {
				return new ResponseEntity<>("Email Not Found", HttpStatus.NOT_FOUND);
			}			
		}	
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	//Save email method that saves emails to database
	public String saveEmail(String item) {
		if(Utility.emailValidator(item)) {
			Email email = new Email();
			email.setEmail(item);
			emailService.saveEmail(email);
			return "Email is created";
		}
		return "Email is not created";
	}
	
	//Fetches emails from given urls
	public void fetchEmailFromUrl(String item) {
		RestTemplate restTemplate = new RestTemplate();		
		Dataset dataset = restTemplate.getForObject(item, Dataset.class);
		List<String> eList = dataset.getEmails();		
		for(String email : eList) {
			saveEmail(email);
		}		 
	}
	
	//Fetches Urls from Urls and saves emails in XML
	public void fetchUrlFromUrl(String item) {
		RestTemplate restTemplate = new RestTemplate();		
		Dataset dataset = restTemplate.getForObject(item, Dataset.class);
		if(dataset.getResources() != null) {
			for(String url : dataset.getResources()) {
				fetchUrlFromUrl(url);
			}		
		}
		fetchEmailFromUrl(item);
	}

	//TEST XML SERVİCES
	@RequestMapping(path="/test-getxml",method=RequestMethod.GET, produces="application/xml")
	public ResponseEntity<Dataset> getXML() {
		Dataset dataset = new Dataset();
		List<String> eList = new ArrayList<String>();
		eList.add("user1@comeon.com");
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://localhost:8080/email/test-getxml2");

		dataset.setResources(urlList);
		dataset.setEmails(eList);
		return new ResponseEntity<>(dataset, HttpStatus.OK);
	}
	
	@RequestMapping(path="/test-getxml2",method=RequestMethod.GET, produces="application/xml")
	public Dataset getXML2() {
		Dataset dataset = new Dataset();
		List<String> eList = new ArrayList<String>();
		eList.add("user2@comeon.com");		
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://localhost:8080/email/test-getxml3");

		dataset.setResources(urlList);
		dataset.setEmails(eList);
		return dataset;
	}
	
	@RequestMapping(path="/test-getxml3",method=RequestMethod.GET, produces="application/xml")
	public Dataset getXML3() {
		Dataset dataset = new Dataset();
		List<String> eList = new ArrayList<String>();
		eList.add("user3@comeon.com");	
		List<String> urlList = new ArrayList<String>();

		dataset.setResources(urlList);
		dataset.setEmails(eList);
		return dataset;
	}
	//END OF TEST XML SERVICES
}
