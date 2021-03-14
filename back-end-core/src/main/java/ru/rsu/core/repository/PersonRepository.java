package ru.rsu.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.rsu.core.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
