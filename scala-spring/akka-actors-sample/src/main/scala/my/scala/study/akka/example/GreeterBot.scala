package my.scala.study.akka.example

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.scalalogging.Logger
import my.scala.study.akka.example.GreeterMain.SayHello
import org.springframework.stereotype.Service


//#greeter-actor

//#greeter-bot
object GreeterBot {
  private val log = Logger(getClass.getName)

  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      context.log.info("Greeting {} for {}", n, message.whom)
      if (n == max) {
        System.exit(0)
        Behaviors.stopped
      } else {
        message.from ! Greeter.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}
//#greeter-bot


//#greeter-main

//#main-class

//object AkkaQuickstart extends App {
//  //#actor-system
//  val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")
//  //#actor-system
//
//  //#main-send-messages
//  greeterMain ! SayHello("Charles")
//  //#main-send-messages
//}

//#main-class
//#full-example
