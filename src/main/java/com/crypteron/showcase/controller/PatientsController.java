package com.crypteron.showcase.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.crypteron.showcase.model.Patient;

/**
 * Please don't judge me by this code, it has been simplified down for the demo.
 */
@Path("patients")
public class PatientsController extends BasePatientController {

  @GET
  public List<Patient> getAllPatients() {
    final EntityManager entityManager = getEntityManager();
    final TypedQuery<Patient> patientQuery = queryWithEntityManagerForAllEntitiesOfClass(entityManager, Patient.class);
    final List<Patient> patients = patientQuery.getResultList();
    closeEntityManager(entityManager);
    return patients;
  }

  @GET
  @Path("{id}")
  public Patient getPatient(@PathParam("id") final int id) {
    final EntityManager entityManager = getEntityManager();
    final Patient existingPatient = entityManager.find(Patient.class, id);
    closeEntityManager(entityManager);
    return existingPatient;
  }

  @POST
  public Response createPatient(final Patient patient) throws Exception {
    final EntityManager entityManager = getEntityManager();
    final Patient newPatient = entityManager.merge(patient);
    closeEntityManager(entityManager);
    final URI createdURL = new URI(Integer.toString(newPatient.getId()));
    return Response.created(createdURL).entity(newPatient).build();
  }

  @PUT
  @Path("{id}")
  public Patient updateEntity(@PathParam("id") final String id, final Patient patient) {
    final EntityManager entityManager = getEntityManager();
    final Patient updatedPatient = entityManager.merge(patient);
    closeEntityManager(entityManager);
    return updatedPatient;
  }

  @DELETE
  @Path("{id}")
  public void deletePatient(@PathParam("id") final int id) {
    final EntityManager entityManager = getEntityManager();
    final Patient existingPatient = entityManager.find(Patient.class, id);
    entityManager.remove(existingPatient);
    closeEntityManager(entityManager);
  }
}
