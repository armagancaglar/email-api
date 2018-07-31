package com.armagancaglar.rest.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.armagancaglar.rest.model.Email;
import com.armagancaglar.rest.repository.EmailRepository;
import com.armagancaglar.rest.service.EmailService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	EmailRepository emailRepo;
	
	@MockBean
	EmailService emailServ;
	
	private Email email;
	
	@Before
	public void prepare() {
		email = new Email();
		email.setId(1);
		email.setEmail("armagan@comeon.com");
		emailRepo.save(email);
	}
	
	@Test
	@Ignore
	public void getEmailByIdTest() throws Exception{
		given(emailRepo.findById(1)).willReturn(email);
		ResultActions result = mvc.perform(get("/email/get-email/1")).andExpect(status().isOk());
		assertEquals("armagan@comeon.com", result);
	}
	
	@Test
	@Ignore
	public void listAllEmailsTest() throws Exception {
		List<Email> eList = new ArrayList<Email>();
		eList.add(email);
		
		given(emailRepo.findAll()).willReturn(eList);
		mvc.perform(get("/email/list-all-emails")).andExpect(status().isOk());
	}

}
