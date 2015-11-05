package com.crypteron.showcase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InsecurePatient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int    id;
  private String firstName;
  private String lastName;
  private String ssn;

  public int getId() {
    return this.id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getSsn() {
    return this.ssn;
  }

  public void setSsn(final String ssn) {
    this.ssn = ssn;
  }

}
