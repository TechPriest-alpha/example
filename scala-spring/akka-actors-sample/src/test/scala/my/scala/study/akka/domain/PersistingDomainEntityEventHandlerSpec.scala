package my.scala.study.akka.domain

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.receptionist.Receptionist
import akka.persistence.testkit.javadsl.EventSourcedBehaviorTestKit
import akka.persistence.typed.PersistenceId
import my.scala.study.akka.domain.DomainStateLogger.StateLoggerKey
import my.scala.study.akka.domain.dto.{InitEvent, StateInfo}
import my.scala.study.akka.example.Greeter.{Greet, Greeted}
import org.junit.jupiter.api.Assertions
import org.scalatest.wordspec.AnyWordSpecLike

class PersistingDomainEntityEventHandlerSpec extends ScalaTestWithActorTestKit(EventSourcedBehaviorTestKit.config) with AnyWordSpecLike {
  //#definition

  "A Domain event handler" must {
    //#test
    "process events" in {
      val replyProbe = createTestProbe[StateInfo]()
      system.receptionist ! Receptionist.Register(StateLoggerKey, replyProbe.ref)

      val underTest = spawn(PersistingDomainEntityEventHandler("test-id1", PersistenceId("test-persistence1", "test-id1")))
      underTest ! InitEvent("test-id1", "test-name1")
      replyProbe.expectMessage(StateInfo("test-id1", 1))
    }
    //#test
  }


}