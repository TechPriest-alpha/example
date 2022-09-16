package my.scala.study.akka.example

import akka.actor.typed.ActorSystem
import com.typesafe.scalalogging.Logger
import my.scala.study.Loggable
import my.scala.study.akka.example.GreeterMain
import my.scala.study.akka.example.GreeterMain.SayHello
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(value = Array("/actors"), produces = Array(MediaType.APPLICATION_JSON_VALUE), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
class ActorApi @Autowired()(val actor: ActorSystem[GreeterMain.SayHello]) extends Loggable {

  @GetMapping(value = Array("/simple/{name}"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  def simple(@PathVariable("name") name: String): String = {
    actor ! GreeterMain.SayHello(name)
    log.info("Simple called")
    "ASD"
  }
}
