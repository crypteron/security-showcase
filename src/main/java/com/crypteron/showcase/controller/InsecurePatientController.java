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

import com.crypteron.showcase.model.InsecurePatient;

/**
 * Please don't judge me by this code, it has been simplified down for the demo.
 */
@Path("insecure/patients")
public class InsecurePatientController extends BasePatientController {

  @GET
  public List<InsecurePatient> getAllPatients() {
    final EntityManager entityManager = getEntityManager();
    final TypedQuery<InsecurePatient> patientQuery = queryWithEntityManagerForAllEntitiesOfClass(entityManager,
        InsecurePatient.class);
    final List<InsecurePatient> patients = patientQuery.getResultList();
    closeEntityManager(entityManager);
    return patients;
  }

  @GET
  @Path("{id}")
  public InsecurePatient getPatient(@PathParam("id") final int id) {
    final EntityManager entityManager = getEntityManager();
    final InsecurePatient existingPatient = entityManager.find(InsecurePatient.class, id);
    closeEntityManager(entityManager);
    return existingPatient;
  }

  @POST
  public Response createPatient(final InsecurePatient patient) throws Exception {
    final EntityManager entityManager = getEntityManager();
    final InsecurePatient newPatient = entityManager.merge(patient);
    closeEntityManager(entityManager);
    final URI createdURL = new URI(Integer.toString(newPatient.getId()));
    return Response.created(createdURL).entity(newPatient).build();
  }

  @PUT
  @Path("{id}")
  public InsecurePatient updateEntity(@PathParam("id") final String id, final InsecurePatient patient) {
    final EntityManager entityManager = getEntityManager();
    final InsecurePatient updatedPatient = entityManager.merge(patient);
    closeEntityManager(entityManager);
    return updatedPatient;
  }

  @DELETE
  @Path("{id}")
  public void deletePatient(@PathParam("id") final int id) {
    final EntityManager entityManager = getEntityManager();
    final InsecurePatient existingPatient = entityManager.find(InsecurePatient.class, id);
    entityManager.remove(existingPatient);
    closeEntityManager(entityManager);
  }
}
