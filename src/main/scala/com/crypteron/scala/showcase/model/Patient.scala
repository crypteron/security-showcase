package com.crypteron.scala.showcase.model

import scala.annotation.meta._
import javax.persistence._

@Entity
class Patient(@(Id @field)@(GeneratedValue @field)(strategy = GenerationType.AUTO) var id: Int,
    var name: String = null,
    var secureSSN: String = null) {
  def this() = this(id = 0)
}

case class PatientDto(id: Int = 0, name: String, ssn: String)
