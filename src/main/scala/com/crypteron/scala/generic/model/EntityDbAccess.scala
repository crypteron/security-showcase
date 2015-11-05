package com.crypteron.scala.generic.model

import javax.persistence._
import javax.persistence.criteria._
import scala.reflect._
import scala.collection.JavaConversions._

/**
  * Generic Scala wrapper around Java-centric JPA.
  */
trait EntityDbAccess[Entity] {
  implicit protected val persistenceUnitName: String
  private val entityManagerFactory = Persistence createEntityManagerFactory persistenceUnitName
  protected var entityManager: EntityManager = _

  private def entityClass(implicit c: ClassTag[Entity]) = c.runtimeClass

  implicit class EntityManagerWith(em: EntityManager)(implicit c: ClassTag[Entity]) {
    def criteriaForEntity: CriteriaQuery[Entity] = {
      val queryCriteria = em.getCriteriaBuilder createQuery entityClass
      queryCriteria from entityClass
      queryCriteria.asInstanceOf[CriteriaQuery[Entity]]
    }
    def queryForAllEntities: TypedQuery[Entity] = em createQuery criteriaForEntity

    def allEntities: List[Entity] = queryForAllEntities.getResultList.toList.asInstanceOf[List[Entity]]
    def entityForId(id: Any)(implicit c: ClassTag[Entity]): Option[Entity] = Option(em.find(entityClass, id).asInstanceOf[Entity])
    def saveEntity(entity: Entity): Option[Entity] = Option(em.merge(entity))
    def removeEntity(entity: Entity): Unit = em.remove(entity)
  }

  def openDb(): Unit = {
    entityManager = entityManagerFactory.createEntityManager
    entityManager.getTransaction.begin()
  }

  def closeDb(): Unit = {
    entityManager.getTransaction.commit()
    entityManager.close()
  }
}
