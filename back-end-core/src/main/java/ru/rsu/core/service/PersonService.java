package ru.rsu.core.service;

import java.util.List;
import java.util.Optional;

import ru.rsu.core.model.entity.Person;

public interface PersonService {
  List<Person> getAllPerson();

  Optional<Person> getPeronById(long id);

  Person addPerson(Person person);

  void removePersonById(long id);
}