package com.armagancaglar.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.armagancaglar.rest.entity.Dataset;

@RestController
@RequestMapping("dummy")
public class DummyRest {
	
	//TEST XML SERVİCES
		@RequestMapping(path="/test-getxml",method=RequestMethod.GET, produces="application/xml")
		public ResponseEntity<Dataset> getXML() {
			Dataset dataset = new Dataset();
			List<String> eList = new ArrayList<String>();
			eList.add("user1@comeon.com");
			List<String> urlList = new ArrayList<String>();
			urlList.add("http://localhost:8080/email/test-getxml3");
			urlList.add("http://localhost:8080/email/test-getxml3");
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
