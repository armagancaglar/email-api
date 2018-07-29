package com.armagancaglar.rest.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.armagancaglar.rest.entity.Dataset;
import com.armagancaglar.rest.model.Email;
import com.armagancaglar.rest.service.EmailService;


@RestController
@RequestMapping("email")
public class EmailController {

	static final String URL_EMAILS_XML = "http://localhost:8080/email/getxml";
	@Autowired
	private EmailService emailService;

	//Sending XML bulk to feth urls and emails
	@RequestMapping(path="/send-xml",method=RequestMethod.POST, consumes= "application/xml", produces="application/xml")
	public void send(@RequestBody Dataset dataset) {
		List<String> uList = dataset.getResources();		
		for(String url : uList) {
			fetchUrlFromUrl(url);
		}
	}
	
	//Lists all emails
	@RequestMapping(path="/list-all-emails",method=RequestMethod.GET)
	public String getAllEmails() {
		List<Email> eList = emailService.findAll();
		return eList.stream()
		.map(e -> e.getEmail().toString())
		.collect(Collectors.joining("\n"));
	}
	
	//Get Count of email
	@RequestMapping(path="/get-email-count", method=RequestMethod.GET)
	private Long getNumberOfEmails(){
	    return emailService.getCount();
	}
	
	//Get single email occurence count
	@RequestMapping(path="/get-single-email-count/{email}",method=RequestMethod.GET)
	public int getCustomer(@PathVariable("email") String email) {
		return emailService.countByEmail(email);
	}	
	
	//Create single email
	@RequestMapping(path="/create/{email}",method=RequestMethod.POST)
	public void send(@PathVariable("email") String email) {
		if(emailValidator(email)) {
			saveEmail(email);
		}
	}
	
	//Get single email by id
	@RequestMapping(path="/get-email/{id}",method=RequestMethod.GET)
	public String getAnEmailById(@PathVariable("id") int id) {
		Email email = emailService.findByEmailId(id);
		if(email != null) {
			return email.getEmail();
		}
		return "Email does not found";
	}
	
	//Delete single email by id
	@RequestMapping(path="/delete/{id}", method=RequestMethod.DELETE)
	public void deleteEmail(@PathVariable("id") int id) {
		Email email = emailService.findByEmailId(id);
		if(email != null) {
			emailService.deleteEmail(email);	
		}		
	}
	
	//Update an email by id
	//Replaces email with given variable
	@RequestMapping(path="/update/{id}/{updateEmail}", method=RequestMethod.PATCH)
	public void updateEmail(@PathVariable("id") int id, @PathVariable("updateEmail") String updateEmail) {
		if(emailValidator(updateEmail)) {
			emailService.updateByEmailId(id, updateEmail);	
		}		
	}
	
	//Email validator dropping invalid emails and unwanted domains
	public boolean emailValidator(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@+(comeon.com|cherry.se)");
		Matcher m = p.matcher(email);		
		/*if(EmailValidator.getInstance().isValid(email))
			int atIndex = email.indexOf("@") + 1;
			String domainValidation = email.substring(atIndex, email.length());
			if(domainValidation.equals("comeon.com") || domainValidation.equals("cherry.se")) {
				return true;
			}*/
		if(m.matches() && EmailValidator.getInstance().isValid(email)){
			return true;
		}
		return false;
	}
	
	//Save email method that saves emails to database
	public void saveEmail(String item) {
		if(emailValidator(item)) {
			Email email = new Email();
			email.setEmail(item);
			emailService.saveEmail(email);
		}
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
	public Dataset getXML() {
		Dataset dataset = new Dataset();
		List<String> eList = new ArrayList<String>();
		eList.add("user1@comeon.com");
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://localhost:8080/email/test-getxml2");

		dataset.setResources(urlList);
		dataset.setEmails(eList);
		return dataset;
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
