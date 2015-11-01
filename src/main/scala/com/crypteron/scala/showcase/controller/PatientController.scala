package com.crypteron.scala.showcase.controller

import com.crypteron.scala.generic.controller._
import com.crypteron.scala.showcase.model._

class PatientController extends EntityController[Patient, PatientDto] {
  implicit protected val persistenceUnitName = ShowcaseConstants.PersistenceUnitName
  implicit object patientConversions extends EntityConversions[Patient, PatientDto] {
    def entityToDto(p: Patient): PatientDto = PatientDto(id = p.id, name = p.name, ssn = p.secureSSN)
    def dtoToEntity(dto: PatientDto): Patient = new Patient(id = dto.id, name = dto.name, secureSSN = dto.ssn)
    def routeIdToId(routeId: String): Any = routeId.toInt
    def dtoToRouteId(dto: PatientDto): String = dto.id.toString
    def entityToCreateable(p: Patient): Patient = new Patient(id = 0, name = p.name, secureSSN = p.secureSSN)
  }
  withCrud routedAt "/patients"
}
