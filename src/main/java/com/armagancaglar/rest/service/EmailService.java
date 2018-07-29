package com.armagancaglar.rest.service;

import java.util.List;
import java.util.Optional;

import com.armagancaglar.rest.model.Email;

public interface EmailService {
	public List<Email> findByEmail(String email);
	public void saveEmail(Email email);
	public List<Email> findAll();
	public Long getCount();
	public int countByEmail(String email);
	public Email findByEmailId(int id);
	public void updateByEmailId(int id, String email);
	public void deleteEmail(Email email);

}
