package my.scala.study.akka.domain

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object DomainSetup {

  def apply(): Behavior[Any] = {
    Behaviors.setup { context =>
      Behaviors.receiveMessage { message =>
        Behaviors.same
      }
    }
  }

}
