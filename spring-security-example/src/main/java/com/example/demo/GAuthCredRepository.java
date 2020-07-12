package com.example.demo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GAuthCredRepository extends CrudRepository<GAuthCred, Integer> {
	Optional<GAuthCred> findByUserName(String userName);
}
