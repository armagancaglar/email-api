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
	@Override
	public List<Email> findByEmail(String email) {
		// TODO Auto-generated method stub
		List<Email> eList = (List<Email>) emailRepository.findByEmail(email);
		return eList;
	}

	@Override
	public void saveEmail(Email email) {
		// TODO Auto-generated method stub
		emailRepository.save(email);
	}

	@Override
	public List<Email> findAll() {
		// TODO Auto-generated method stub
		return emailRepository.findAll();
	}

	@Override
	public Long getCount() {
		return emailRepository.count();
	}

	@Override
	public int countByEmail(String email) {
		// TODO Auto-generated method stub
		return emailRepository.countByEmail(email);
	}

	@Override
	public void updateByEmailId(int id, String email) {
		Email mail = emailRepository.findById(id);
		mail.setEmail(email);
		emailRepository.save(mail);
	}

	@Override
	public Email findByEmailId(int id) {
		return emailRepository.findById(id);
	}

	@Override
	public void deleteEmail(Email email) {
		emailRepository.delete(email);
		
	}

}
