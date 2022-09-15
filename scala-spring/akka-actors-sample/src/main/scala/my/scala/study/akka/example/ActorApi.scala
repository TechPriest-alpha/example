package my.scala.study.akka.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}
import my.scala.study.akka.example.GreeterMain

@RestController
@RequestMapping(value = Array("/actors"), produces = Array(MediaType.APPLICATION_JSON_VALUE), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
class ActorApi  @Autowired()(val actor: GreeterMain) {

  @GetMapping(value = Array("/simple"))
  def simple(): String = {
    actor ! SayHello("Hi")
    ""
  }
}
