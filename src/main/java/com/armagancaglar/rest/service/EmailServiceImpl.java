package com.armagancaglar.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.armagancaglar.rest.model.Email;
import com.armagancaglar.rest.repository.EmailRepository;

@Service("EmailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;
	
	//List all emails which email field is equals to given criteria
	@Override
	public List<Email> findByEmail(String email) {
		List<Email> eList = (List<Email>) emailRepository.findByEmail(email);
		return eList;
	}

	//Save email to db
	@Override
	public void saveEmail(Email email) {
		emailRepository.save(email);
	}

	//Get all emails from db
	@Override
	public List<Email> findAll() {
		return emailRepository.findAll();
	}

	//Get all item count 
	@Override
	public Long getCount() {
		return emailRepository.count();
	}

	//Count email occurence
	@Override
	public int countByEmail(String email) {
		return emailRepository.countByEmail(email);
	}

	//Update email by given id and replace email with given email
	@Override
	public void updateByEmailId(int id, String email) {
		Email mail = emailRepository.findById(id);
		mail.setEmail(email);
		emailRepository.save(mail);
	}

	//Find email by id
	@Override
	public Email findByEmailId(int id) {
		return emailRepository.findById(id);
	}
	//Delete given email from db
	@Override
	public void deleteEmail(Email email) {
		emailRepository.delete(email);
		
	}

}
