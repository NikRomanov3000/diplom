package ru.rsu.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import ru.rsu.core.model.entity.Person;
import ru.rsu.core.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {
  private final PersonService personService;
  private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping({"/person"})
  public @ResponseBody
  List<Person> getAllPerson() {
    logger.info("HTTP Query: GET /api/person");
    return personService.getAllPerson();
  }

  @GetMapping({"/person/{personId}"})
  public Optional<Person> getPersonById(@PathVariable(value = "personId") Long id) {
    logger.info("HTTP Query: GET /api/person/"+id);
    return personService.getPeronById(id);
  }


  @PostMapping({"/person"})
  public Long savePerson(@RequestBody Person person) throws Exception {
    Long retId = null;
    try {
      retId =  personService.addPerson(person).getId();
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    logger.info("HTTP Query: POST /api/person request body: " + person.toString() + " Person Id: " +retId);
    return retId;
  }

  @DeleteMapping({"/person/{personId}"})
  public boolean deletePersonById(@PathVariable(value = "personId") Long id) throws Exception {
    logger.info("HTTP Query: DELETE /api/person/" + id);
    try {
      personService.removePersonById(id);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }
}
