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
  public int    id;
  @Secure
  public String firstName;
  @Secure
  public String lastName;
  @Secure
  public String ssn;
}
