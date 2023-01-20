package my.scala.study.akka.example

import akka.actor.typed.ActorSystem
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, EntityTypeKey}
import com.typesafe.scalalogging.Logger
import my.scala.study.Loggable
import my.scala.study.akka.domain.dto.{DomainEvents, InitEvent}
import my.scala.study.akka.example.GreeterMain
import my.scala.study.akka.example.GreeterMain.SayHello
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(value = Array("/actors"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
class ActorApi @Autowired()(
                             val actor1: ActorSystem[GreeterMain.SayHello],
                             val sharding: ClusterSharding,
                             val domainTypeKey: EntityTypeKey[DomainEvents]
                           ) extends Loggable {

  @GetMapping(value = Array("/simple/{name}"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  def simple(@PathVariable("name") name: String): String = {
    actor1 ! GreeterMain.SayHello(name)
    log.info("Simple called")
    "ASD"
  }

  @GetMapping(value = Array("/simple2/{name}"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  def simple2(@PathVariable("name") name: String): String = {
    val ref = sharding.entityRefFor(domainTypeKey, name)
    ref ! InitEvent(name, "Business value")
    log.info("Simple2 called")
    "Business event sent"
  }
}
