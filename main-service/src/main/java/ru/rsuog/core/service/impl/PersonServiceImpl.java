package ru.rsuog.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.rsuog.core.model.entity.Person;
import ru.rsuog.core.repository.PersonRepository;
import ru.rsuog.core.service.PersonService;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public List<Person> getAllPerson() {
    return personRepository.findAll();
  }

  @Override
  public Optional<Person> getPeronById(long id) {
    return personRepository.findById(id);
  }

  @Override
  @Transactional
  public Person addPerson(Person person) {
    return personRepository.save(person);
  }

  @Override
  @Transactional
  public void removePersonById(long id) {
    personRepository.deleteById(id);
  }
}
