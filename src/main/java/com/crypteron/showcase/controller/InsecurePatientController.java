package com.crypteron.showcase.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;

import com.crypteron.showcase.model.InsecurePatient;

@Path("insecure/patients")
public class InsecurePatientController extends EntityCrudController<InsecurePatient> {

  @Override
  String routeIdForEntity(final InsecurePatient patient) {
    return Integer.toString(patient.getId());
  }

  @Override
  URI uriForEntity(final InsecurePatient patient) {
    try {
      return new URI(routeIdForEntity(patient));
    } catch (final URISyntaxException e) {
      throw new InternalServerErrorException(e);
    }
  }

  @Override
  void prepareEntityForCreate(final InsecurePatient patient) {
    patient.setId(0);
  }
}
