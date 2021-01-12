package ru.rsuog.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import ru.rsuog.core.model.entity.Address;
import ru.rsuog.core.service.AddressService;
import ru.rsuog.core.service.PersonService;

@RestController
@RequestMapping("/api")
public class AddressController {
  private final AddressService addressService;
  private final PersonService personService;
  private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

  public AddressController(AddressService addressService, PersonService personService) {
    this.addressService = addressService;
    this.personService = personService;
  }

  @GetMapping({"/address"})
  public List<Address> getAllAddress() {
    logger.info("HTTP Query: GET /api/address");
    return addressService.getAllAddress();
  }

  @GetMapping({"/address/{addressId}"})
  public Optional<Address> getAddressById(@PathVariable(value = "addressId") Long id) {
    logger.info("HTTP Query: GET /api/address/"+id);
    return addressService.getAddressById(id);
  }

  @PostMapping({"/address"})
  public Long saveAddress(@RequestBody Address address) throws Exception {
    Long retId = null;
    try {
      if(personService.getPeronById(address.getRefPersonId()).isEmpty()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person with id: "+address.getRefPersonId()+" doesn't exist");
      }else {
        address.setPerson(personService.getPeronById(address.getRefPersonId()).get());
        retId = addressService.addAddress(address).getId();
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }

    logger.info("HTTP Query: POST /api/address request body: " + address.toString() + " Address Id: " +retId);
    return retId;
  }

  @DeleteMapping({"/address/{addressId}"})
  public boolean deleteAddressById(@PathVariable(value = "addressId") Long id) throws Exception {
    logger.info("HTTP Query: DELETE /api/address/" + id);
    try {
      addressService.removeAddressById(id);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }
}
