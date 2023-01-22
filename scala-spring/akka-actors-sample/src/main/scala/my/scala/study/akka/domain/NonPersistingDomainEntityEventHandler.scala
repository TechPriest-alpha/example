package my.scala.study.akka.domain

import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.persistence.PersistenceIdentity
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.Effect
import akka.serialization.*
import my.scala.study.Loggable
import my.scala.study.akka.domain.DomainStateLogger.StateLoggerKey
import my.scala.study.akka.domain.dto.*

/**
 * Non-persisting actor
 */
object NonPersistingDomainEntityEventHandler extends Loggable {
  def apply(entityId: String, persistenceId: PersistenceId): Behavior[DomainEvents] = {
    Behaviors.setup { context => {
      val listingResponseAdapter = context.messageAdapter[Receptionist.Listing](ListingResponse.apply)

      def process(state: Integer): Behavior[DomainEvents] = {
        Behaviors.receiveMessagePartial { message =>
          log.info(s"RECEIVE! $entityId $state $persistenceId")
          message match {
            case ListingResponse(StateLoggerKey.Listing(listings)) =>
              log.info(s"LISTING! $entityId $state $listings $persistenceId")
              listings.take(1).foreach(loger => loger.tell(StateInfo(entityId, state)))
              Behaviors.same
            case InitEvent(id, name) =>
              log.info(s"MESSAGE! $entityId $state $persistenceId")
              context.system.receptionist ! Receptionist.Find(StateLoggerKey, listingResponseAdapter)
              log.info(s"Processed event ($id, $name) with $entityId $state times")
              process(state + 1)
          }
        }
      }

      log.info(s"State initialized for $entityId")
      process(0)
    }
    }

  }
}
