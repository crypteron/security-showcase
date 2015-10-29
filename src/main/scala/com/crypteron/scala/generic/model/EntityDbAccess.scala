package com.crypteron.scala.generic.model

import javax.persistence._
import javax.persistence.criteria._
import scala.reflect._
import scala.collection.JavaConversions._
import com.crypteron.ciphercore.config.CrypteronConfiguration
import com.crypteron.ciphercore.config.ConfigurationParameter

/**
 * Generic Scala wrapper around Java-centric JPA.
 */
trait EntityDbAccess[Entity] {
  implicit protected val persistenceUnitName: String
  private val entityManagerFactory = {
    // Accommodate overriding App Secret built in with environment variable i.e. cloud deployments
    import ConfigurationParameter.APP_SECRET
    val config = new CrypteronConfiguration
    val appSecretVariable = APP_SECRET.getConfigurationKey.toUpperCase replace (".", "_")
    sys.env get appSecretVariable foreach (appSecret => config.setConfiguration(APP_SECRET, appSecret))
    Persistence createEntityManagerFactory (persistenceUnitName, config)
  }
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
