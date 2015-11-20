package com.crypteron.showcase.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crypteron.showcase.model.Patient;
import com.crypteron.showcase.model.ShowcaseConstants;

/**
 * Please don't judge me by this code, it has been simplified down for the demo.
 */
@Path("patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientsController {
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
      .createEntityManagerFactory(ShowcaseConstants.PERSISTENCE_UNIT_NAME);

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
  public Patient updatePatient(@PathParam("id") final String id, final Patient patient) {
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

  private <T> TypedQuery<T> queryWithEntityManagerForAllEntitiesOfClass(final EntityManager entityManager,
      final Class<T> patientClass) {
    final CriteriaQuery<T> queryCriteria = entityManager.getCriteriaBuilder().createQuery(patientClass);
    queryCriteria.from(patientClass);
    return entityManager.createQuery(queryCriteria);
  }

  private EntityManager getEntityManager() {
    final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    entityManager.getTransaction().begin();
    return entityManager;
  }

  private void closeEntityManager(final EntityManager entityManager) {
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
