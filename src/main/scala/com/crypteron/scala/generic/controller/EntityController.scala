package com.crypteron.scala.generic.controller

import java.net.HttpURLConnection.HTTP_NOT_FOUND
import scala.reflect.{ ClassTag, Manifest }
import org.scalatra._

trait EntityController[Entity, EntityDto] extends EntityCrudController[Entity, EntityDto] {
  var urlPattern: String = _

  def routedAt(routedAt: String): this.type = {
    urlPattern = routedAt
    this
  }

  notFound {
    response.setStatus(HTTP_NOT_FOUND)
  }
}
