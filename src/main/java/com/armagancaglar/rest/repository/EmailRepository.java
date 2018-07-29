package com.armagancaglar.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.armagancaglar.rest.model.Email;

@Repository("emailRepository")
public interface EmailRepository extends JpaRepository<Email, Long>{
	List<Email> findByEmail (String email);
	List<Email> findAll();
	int countByEmail (String email);
	Email findById (int id);

}
