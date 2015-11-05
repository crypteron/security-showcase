package com.crypteron.showcase.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;

import com.crypteron.showcase.model.Patient;

@Path("patients")
public class PatientsController extends EntityCrudController<Patient> {

  @Override
  String routeIdForEntity(final Patient patient) {
    return Integer.toString(patient.id);
  }

  @Override
  URI uriForEntity(final Patient patient) {
    try {
      return new URI(routeIdForEntity(patient));
    } catch (final URISyntaxException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  void prepareEntityForCreate(final Patient patient) {
    patient.id = 0;
  }
}
