package my.scala.study.akka.example

import akka.actor.typed.ActorSystem
import com.typesafe.scalalogging.Logger
import my.scala.study.akka.example.GreeterMain
import my.scala.study.akka.example.GreeterMain.SayHello
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(value = Array("/actors"), produces = Array(MediaType.APPLICATION_JSON_VALUE), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
class ActorApi @Autowired()(val actor: ActorSystem[GreeterMain.SayHello]) {
  private val log = Logger(classOf[ActorApi])

  @GetMapping(value = Array("/simple"), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  def simple(): String = {
    actor ! GreeterMain.SayHello("Hi")
    log.info("Simple called")
    "ASD"
  }
}
