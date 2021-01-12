package ru.rsuog.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.rsuog.core.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
