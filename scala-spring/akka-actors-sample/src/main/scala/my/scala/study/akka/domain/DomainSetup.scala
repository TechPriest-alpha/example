package my.scala.study.akka.domain

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import my.scala.study.Loggable

object DomainSetup extends Loggable {

  def apply(): Behavior[Nothing] = {
    Behaviors.setup { context =>
      context.spawn(DomainStateLogger(), "domain-state-logger")
      log.info("Setup completed")
      Behaviors.receiveMessage { message =>
        log.info(s"Unexpected message received $message")
        Behaviors.same
      }
    }
  }

}
