package ru.rsuog.core.service;

import java.util.List;
import java.util.Optional;

import ru.rsuog.core.model.entity.Address;

public interface AddressService {
  List<Address> getAllAddress();

  Optional<Address> getAddressById(long id);

  Address addAddress(Address address);

  void removeAddressById(long id);
}
