package com.crypteron.showcase.controller;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crypteron.showcase.model.ShowcaseConstants;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BasePatientController {
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
  private static Logger                     LOG = LoggerFactory.getLogger(BasePatientController.class);

  static {
    final Map<String, String> env = System.getenv();
    final String mysqlHost = env.get("MYSQL_HOST");
    final String mysqlDatabase = env.get("MYSQL_DATABASE");
    final String mysqlUser = env.get("MYSQL_USER");
    final String mysqlPassword = env.get("MYSQL_PASSWORD");

    final Map<String, Object> envConfig = new HashMap<String, Object>();
    envConfig.put("javax.persistence.jdbc.url", String.format("jdbc:mysql://%s:3306/%s", mysqlHost, mysqlDatabase));
    envConfig.put("javax.persistence.jdbc.user", mysqlUser);
    envConfig.put("javax.persistence.jdbc.password", mysqlPassword);
    LOG.info("ENV CONFIG: {}", envConfig);
    ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(ShowcaseConstants.PERSISTENCE_UNIT_NAME, envConfig);
  }

  protected <T> TypedQuery<T> queryWithEntityManagerForAllEntitiesOfClass(final EntityManager entityManager,
      final Class<T> patientClass) {
    final CriteriaQuery<T> queryCriteria = entityManager.getCriteriaBuilder().createQuery(patientClass);
    queryCriteria.from(patientClass);
    return entityManager.createQuery(queryCriteria);
  }

  protected EntityManager getEntityManager() {
    final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    entityManager.getTransaction().begin();
    return entityManager;
  }

  protected void closeEntityManager(final EntityManager entityManager) {
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
