package com.crypteron.showcase.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crypteron.showcase.model.ShowcaseConstants;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BasePatientController {
  private static final EntityManagerFactory entityManagerFactory = Persistence
      .createEntityManagerFactory(ShowcaseConstants.PERSISTENCE_UNIT_NAME);

  protected <T> TypedQuery<T> queryWithEntityManagerForAllEntitiesOfClass(final EntityManager entityManager,
      final Class<T> patientClass) {
    final CriteriaQuery<T> queryCriteria = entityManager.getCriteriaBuilder().createQuery(patientClass);
    queryCriteria.from(patientClass);
    return entityManager.createQuery(queryCriteria);
  }

  protected EntityManager getEntityManager() {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    return entityManager;
  }

  protected void closeEntityManager(final EntityManager entityManager) {
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
