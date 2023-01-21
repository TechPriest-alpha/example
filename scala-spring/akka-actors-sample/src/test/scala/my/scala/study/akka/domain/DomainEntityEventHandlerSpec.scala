package my.scala.study.akka.domain

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.receptionist.Receptionist
import my.scala.study.akka.domain.DomainStateLogger.StateLoggerKey
import my.scala.study.akka.domain.dto.{InitEvent, StateInfo}
import my.scala.study.akka.example.Greeter.{Greet, Greeted}
import org.junit.jupiter.api.Assertions
import org.scalatest.wordspec.AnyWordSpecLike

class DomainEntityEventHandlerSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  //#definition

  "A Domain event handler" must {
    //#test
    "process events" in {
      val replyProbe = createTestProbe[StateInfo]()
      system.receptionist ! Receptionist.Register(StateLoggerKey, replyProbe.ref)

      val underTest = spawn(DomainEntityEventHandler("test-id"))
      underTest ! InitEvent("test-id", "test-name")
      replyProbe.expectMessage(StateInfo("test-id", 1))
    }
    //#test
  }


}