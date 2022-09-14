package my.scala.study.akka.example

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import my.scala.study.akka.example.Greeter.{Greet, Greeted}
import org.junit.jupiter.api.Assertions
import org.scalatest.wordspec.AnyWordSpecLike

class GreeterSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  //#definition

  "A Greeter" must {
    //#test
    "reply to greeted" in {
      val replyProbe = createTestProbe[Greeted]()
      val underTest = spawn(Greeter())
      underTest ! Greet("Santa", replyProbe.ref)
      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
    }
    //#test
  }

}
