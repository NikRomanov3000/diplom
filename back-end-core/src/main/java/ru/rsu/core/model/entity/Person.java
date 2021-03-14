package ru.rsu.core.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "person")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @JsonProperty("id")
  private Long id;

  @Column(name = "person_name")
  @JsonProperty("name")
  private String personName;

  @Column(name = "passport_serial")
  @JsonProperty("passportSerial")
  private int passportSerial;

  @Column(name = "passport_number")
  @JsonProperty("passportNumber")
  private int passportNumber;

  @OneToMany(targetEntity = Address.class, mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Address> personAddresses;

  public Person() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public int getPassportSerial() {
    return passportSerial;
  }

  public void setPassportSerial(int passportSerial) {
    this.passportSerial = passportSerial;
  }

  public int getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(int passportNumber) {
    this.passportNumber = passportNumber;
  }

  public List<Address> getPersonAddresses() {
    return personAddresses;
  }

  public void setPersonAddresses(List<Address> personAddresses) {
    this.personAddresses = personAddresses;
  }

  public void addPersonAddress(Address personAddresses) {
    this.personAddresses.add(personAddresses);
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", personName='" + personName + '\'' +
        ", passportSerial=" + passportSerial +
        ", passportNumber=" + passportNumber +
        '}';
  }
}
