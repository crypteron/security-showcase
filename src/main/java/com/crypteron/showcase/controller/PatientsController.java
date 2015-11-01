package com.crypteron.showcase.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crypteron.showcase.model.Patient;
import com.crypteron.showcase.model.ShowcaseConstants;

@Path("patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientsController {
  private static final EntityManagerFactory entityManagerFactory = Persistence
      .createEntityManagerFactory(ShowcaseConstants.PERSISTENCE_UNIT_NAME);

  @GET
  public List<Patient> getPatients() {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final TypedQuery<Patient> patientsQuery = entityManager.createQuery("from Patient", Patient.class);
    return patientsQuery.getResultList();
  }

  @GET
  @Path("{id}")
  public Patient getPatient(@PathParam("id") final int id) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final Patient existingPatient = entityManager.find(Patient.class, id);
    if (existingPatient == null) {
      throw new NotFoundException();
    }
    return existingPatient;
  }

  @POST
  public Response createPatient(final Patient patient) throws Exception {
    patient.setId(0);
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    final Patient newPatient = entityManager.merge(patient);
    entityManager.getTransaction().commit();
    final URI newPatientURI = new URI(Integer.toString(newPatient.getId()));
    return Response.created(newPatientURI).entity(newPatient).build();
  }

  @PUT
  @Path("{id}")
  public Patient updatePatient(@PathParam("id") final int id, final Patient patient) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    final Patient updatedPatient = entityManager.merge(patient);
    entityManager.getTransaction().commit();
    return updatedPatient;
  }

  @DELETE
  @Path("{id}")
  public void deletePatient(@PathParam("id") final int id, final Patient patient) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    final Patient existingPatient = entityManager.find(Patient.class, id);
    entityManager.remove(existingPatient);
    entityManager.getTransaction().commit();
  }
}
