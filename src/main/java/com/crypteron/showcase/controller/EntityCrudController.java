package com.crypteron.showcase.controller;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.BadRequestException;
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

import com.crypteron.showcase.model.ShowcaseConstants;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class EntityCrudController<Entity> {
  private static final EntityManagerFactory entityManagerFactory = Persistence
      .createEntityManagerFactory(ShowcaseConstants.PERSISTENCE_UNIT_NAME);

  abstract String routeIdForEntity(Entity entity);

  abstract URI uriForEntity(Entity entity);

  abstract void prepareEntityForCreate(Entity entity);

  @GET
  public List<Entity> getAllEntities() {
    final EntityManager entityManager = getEntityManager();
    final CriteriaQuery<Entity> queryCriteria = entityManager.getCriteriaBuilder().createQuery(getEntityClass());
    queryCriteria.from(getEntityClass());
    final TypedQuery<Entity> entityQuery = entityManager.createQuery(queryCriteria);
    final List<Entity> entities = entityQuery.getResultList();
    closeEntityManager(entityManager);
    return entities;
  }

  @GET
  @Path("{id}")
  public Entity getEntity(@PathParam("id") final int id) {
    final EntityManager entityManager = getEntityManager();
    final Entity existingEntity = entityManager.find(getEntityClass(), id);
    closeEntityManager(entityManager);
    if (existingEntity == null) {
      throw new NotFoundException();
    }
    return existingEntity;
  }

  @POST
  public Response createEntity(final Entity entity) {
    prepareEntityForCreate(entity);
    final EntityManager entityManager = getEntityManager();
    final Entity newEntity = entityManager.merge(entity);
    closeEntityManager(entityManager);
    return Response.created(uriForEntity(newEntity)).entity(newEntity).build();
  }

  @PUT
  @Path("{id}")
  public Entity updateEntity(@PathParam("id") final String id, final Entity entity) {
    if (entity == null || !routeIdForEntity(entity).equals(id)) {
      throw new BadRequestException();
    }
    final EntityManager entityManager = getEntityManager();
    final Entity updatedEntity = entityManager.merge(entity);
    closeEntityManager(entityManager);
    return updatedEntity;
  }

  @DELETE
  @Path("{id}")
  public void deleteEntity(@PathParam("id") final String id) {
    final EntityManager entityManager = getEntityManager();
    final Entity existingEntity = entityManager.find(getEntityClass(), id);
    entityManager.remove(existingEntity);
    closeEntityManager(entityManager);
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

  @SuppressWarnings("unchecked")
  private Class<Entity> getEntityClass() {
    return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
}
