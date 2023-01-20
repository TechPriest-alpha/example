package my.scala.study.akka.domain

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import my.scala.study.Loggable
import my.scala.study.akka.domain.dto.{DomainEvent1, DomainEvent2, DomainEvents, InitEvent}

import java.util.concurrent.atomic.AtomicInteger

object DomainEntityEventHandler extends Loggable {
  def apply(entityId: String): Behavior[DomainEvents] = {

    def process(state: Integer): Behavior[DomainEvents] = {
      Behaviors.receiveMessage { message =>
        log.info(s"Processed event $message with $entityId $state times")
        process(state + 1)
      }
    }

    log.info(s"State initialized for $entityId")
    process(0)
  }
}
