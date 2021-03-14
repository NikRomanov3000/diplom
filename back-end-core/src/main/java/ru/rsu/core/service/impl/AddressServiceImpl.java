package ru.rsu.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.rsu.core.model.entity.Address;
import ru.rsu.core.repository.AddressRepository;
import ru.rsu.core.service.AddressService;

@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public List<Address> getAllAddress() {
    return addressRepository.findAll();
  }

  @Override
  public Optional<Address> getAddressById(long id) {
    return addressRepository.findById(id);
  }

  @Override
  @Transactional
  public Address addAddress(Address address) {
    return addressRepository.save(address);
  }

  @Override
  @Transactional
  public void removeAddressById(long id) {
    addressRepository.deleteById(id);
  }
}
