package my.scala.study.akka.domain.dto

import my.scala.study.Stringable

trait DomainEvents extends Stringable {
  def id: String
}

final case class InitEvent(id: String, name: String) extends DomainEvents

final case class DomainEvent1(id: String, name: String) extends DomainEvents

final case class DomainEvent2(id: String, name: String) extends DomainEvents

