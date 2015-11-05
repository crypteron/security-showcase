package com.crypteron.showcase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InsecurePatient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int    id;
  public String firstName;
  public String lastName;
  public String ssn;
}
