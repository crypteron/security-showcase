package com.crypteron.scala.generic.controller

import org.scalatra.json._
import org.json4s.{ DefaultFormats, Formats }

trait JsonSupport extends JacksonJsonSupport {
  // Sets up automatic case class to JSON output serialization, required by the JValueResult trait.
  protected implicit lazy val jsonFormats = DefaultFormats

  before() {
    contentType = formats("json")
  }
}
