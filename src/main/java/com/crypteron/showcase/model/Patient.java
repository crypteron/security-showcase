package com.crypteron.showcase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.crypteron.Secure;

@Entity
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int    id;
  private String name;
  @Secure
  private String ssn;

  public int getId() {
    return this.id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getSsn() {
    return this.ssn;
  }

  public void setSsn(final String ssn) {
    this.ssn = ssn;
  }

}
