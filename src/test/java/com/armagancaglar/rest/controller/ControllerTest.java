package com.armagancaglar.rest.controller;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.armagancaglar.rest.model.Email;
import com.armagancaglar.rest.repository.EmailRepository;
import com.armagancaglar.rest.service.EmailService;



@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

	private MockMvc mockMvc;
	
	@MockBean
	private EmailService emailServiceMock;
	
	@MockBean
	private EmailRepository emailRepoMock;
	
	@Test
	public void findAll_EmailsFound_ShoulReturnFoundEmails() throws Exception {
		Email first = new Email();
		first.setEmail("armagan@comeon.com");
		emailRepoMock.save(first);
		
		Email second = new Email();
		second.setEmail("cetin@comeon.com");
		emailRepoMock.save(second);
		
		when(emailServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
		
		mockMvc.perform(get("/email/list-all-emails"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
		
		verify(emailServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(emailServiceMock);
	}
}
