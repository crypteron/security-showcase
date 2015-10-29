package com.crypteron.scala.generic.controller

import scala.reflect._
import org.scalatra._

trait EntityCrudController[Entity, EntityDto] extends ScalatraServlet with EntityDtoAccess[Entity, EntityDto] {
  def withCrud()(implicit c: ClassTag[Entity], mf: Manifest[EntityDto],
                 convert: EntityConversions[Entity, EntityDto]): this.type = {
    get() {
      allEntities
    }

    get("/:id") {
      currentEntityAsDto.getOrElse(NotFound())
    }

    post() {
      Created(createEntity())
    }

    put("/:id") {
      (currentEntityAsDto, currentDto) match {
        case (Some(e), d) if routeIdForDto(e) == routeIdForDto(d) => updateEntity()
        case (None, _) => NotFound()
        case _ => BadRequest()
      }
    }

    delete("/:id") {
      if (currentEntityAsDto.isDefined) removeEntity() else NotFound()
    }

    this
  }
}