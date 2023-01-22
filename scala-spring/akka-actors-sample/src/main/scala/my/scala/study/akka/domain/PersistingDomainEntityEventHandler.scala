package my.scala.study.akka.domain

import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.persistence.PersistenceIdentity
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import akka.serialization.*
import my.scala.study.Loggable
import my.scala.study.akka.domain.DomainStateLogger.StateLoggerKey
import my.scala.study.akka.domain.dto.*

/**
 * Non-persisting actor
 */
//noinspection DuplicatedCode
object PersistingDomainEntityEventHandler extends Loggable {
  def apply(entityId: String, persistenceId: PersistenceId): Behavior[DomainEvents] = {
    Behaviors.setup { context => {
      val listingResponseAdapter = context.messageAdapter[Receptionist.Listing](ListingResponse.apply)

      val commandHandler: (Int, DomainEvents) => Effect[DomainEvents, Int] = { (_, cmd) =>
        log.info("DEF")
        cmd match {
          case ListingResponse(StateLoggerKey.Listing(listings)) =>
            log.info(s"LISTING ARRIVED! $listings")
            Effect.none.thenRun(state => {
              log.info(s"LISTING EFFECT! $state $entityId $persistenceId $listings")
              listings.take(1).foreach(loger => loger.tell(StateInfo(entityId, state)))
            })
          case i: InitEvent =>
            log.info(s"MESSAGE! $entityId $i $persistenceId")
            Effect.persist(i).thenRun(state => {
              context.system.receptionist ! Receptionist.Find(StateLoggerKey, listingResponseAdapter)
              log.info(s"Processed event $i with $entityId $state times")
            })
        }
      }

      val eventHandler: (Int, DomainEvents) => Int = { (state, evt) =>
        log.info("ABC")
        evt match {
          case i: InitEvent => state + 1
          case _ => state
        }
      }

      Behaviors.setup { message =>
        log.info(s"SETUP! $entityId $message $persistenceId")
        EventSourcedBehavior(persistenceId, emptyState = 0, commandHandler, eventHandler)
      }
    }
    }

  }


}
