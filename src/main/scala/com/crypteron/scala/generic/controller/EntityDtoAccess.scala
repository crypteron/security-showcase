package com.crypteron.scala.generic.controller

import scala.reflect._
import com.crypteron.scala.generic.model.EntityDbAccess

trait EntityConversions[Entity, EntityDto] {
  def entityToDto(entity: Entity): EntityDto
  def dtoToEntity(dto: EntityDto): Entity
  def routeIdToId(routeId: String): Any
  def dtoToRouteId(dto: EntityDto): String
  def entityToCreateable(entity: Entity): Entity
}

trait EntityDtoAccess[Entity, EntityDto] extends EntityDbAccess[Entity] with JsonSupport {
  private def toEntityDto(entity: Entity)(implicit convert: EntityConversions[Entity, EntityDto]) = convert entityToDto entity
  private def currentEntityId(implicit convert: EntityConversions[Entity, EntityDto]) = convert routeIdToId params("id")
  private def currentDtoAsEntity(implicit mf: Manifest[EntityDto],
    convert: EntityConversions[Entity, EntityDto]) = convert dtoToEntity currentDto

  private def currentDtoAsEntityToCreate(implicit mf: Manifest[EntityDto],
    convert: EntityConversions[Entity, EntityDto]) = convert entityToCreateable currentDtoAsEntity

  def currentEntityAsDto(implicit c: ClassTag[Entity],
    convert: EntityConversions[Entity, EntityDto]): Option[EntityDto] = currentEntity map toEntityDto

  def currentDto(implicit mf: Manifest[EntityDto]): EntityDto = parse(request.body).extract[EntityDto]
  def routeIdForDto(dto: EntityDto)(implicit convert: EntityConversions[Entity, EntityDto]): String = convert dtoToRouteId dto

  def currentEntity(implicit c: ClassTag[Entity],
    convert: EntityConversions[Entity, EntityDto]): Option[Entity] = entityManager entityForId currentEntityId

  def allEntities(implicit c: ClassTag[Entity],
    convert: EntityConversions[Entity, EntityDto]): List[EntityDto] = entityManager.allEntities map toEntityDto

  def createEntity()(implicit c: ClassTag[Entity], mf: Manifest[EntityDto],
    convert: EntityConversions[Entity, EntityDto]): Option[EntityDto] = entityManager.saveEntity(currentDtoAsEntityToCreate) map toEntityDto

  def updateEntity()(implicit c: ClassTag[Entity], mf: Manifest[EntityDto],
    convert: EntityConversions[Entity, EntityDto]): Option[EntityDto] = entityManager.saveEntity(currentDtoAsEntity) map toEntityDto

  def removeEntity()(implicit c: ClassTag[Entity], convert: EntityConversions[Entity, EntityDto]) = entityManager.removeEntity(currentEntity.get)

  before() { openDb() }
  after() { closeDb() }
}
