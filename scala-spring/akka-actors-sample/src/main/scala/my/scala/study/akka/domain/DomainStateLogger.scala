package my.scala.study.akka.domain

import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import my.scala.study.Loggable
import my.scala.study.akka.domain.dto.*

import java.util.concurrent.atomic.AtomicInteger

object DomainStateLogger extends Loggable {
  val StateLoggerKey = ServiceKey[StateInfo]("stateInfoLogger")

  def apply(): Behavior[StateInfo] = {
    Behaviors.setup { context =>
      context.system.receptionist ! Receptionist.Register(StateLoggerKey, context.self)

      Behaviors.receiveMessage { message =>
        log.info(s"Processed event $message")
        Behaviors.same
      }
    }


  }
}
