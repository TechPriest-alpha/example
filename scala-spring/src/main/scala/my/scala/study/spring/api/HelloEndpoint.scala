package my.scala.study.spring.api

import my.scala.study.spring.system.Loggable
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RestController}

@RestController("hello")
@RequestMapping(Array("/hello"))
class HelloEndpoint extends Loggable {
  //  private val logger = Logger(getClass.getName)

  @RequestMapping(Array("/say/{name}"))
  def name(@PathVariable("name") name: String): String = {
    log.info("Hello invoked: {}", name)
    s"Hello $name !"
  }

  @RequestMapping(Array("/data"))
  def someData(): String = {
    log.info("System data requested")
    "Some data: available procs: " + Runtime.getRuntime.availableProcessors()
  }
}
