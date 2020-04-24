package com.example.h2flyway;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private final PersonRepo personRepo;

	public PersonController(PersonRepo personRepo) {
		this.personRepo = personRepo;
	}

	@GetMapping("/person/{id}")
	public String getPerson(@PathVariable("id") long id) {
		Optional<Person> person = personRepo.findById(Long.valueOf(id));

		if (!person.isPresent()) {
			Person newPerson = createPerson(id);
			personRepo.save(newPerson);
			person = Optional.of(newPerson);
		}

		return person.get().toString();
	}

	public static Person createPerson(long id) {
		Person person = new Person();
		person.setId(id);
		person.setName(UUID.randomUUID().toString());
		person.setAge(RandomUtils.nextInt(1, 100));
		return person;
	}
}
