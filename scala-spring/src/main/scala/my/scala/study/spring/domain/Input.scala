package my.scala.study.spring.domain

import my.scala.study.spring.BootConfig
import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2, Result}
import my.scala.study.spring.system.{Loggable, LogicFor}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

import java.util
import scala.collection.mutable
import scala.reflect.ClassTag

@RestController("domainInput")
@RequestMapping(path = Array("/domain"))
class Input @Autowired()(val logics: java.util.List[LogicFor[?]], val output: Output) extends Loggable {

  @PostMapping(value = Array("/event1"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent1): Result = {
    handle(event)
  }

  @PostMapping(value = Array("/event2"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent2): Result = {
    handle(event)
  }

  private def handle(event: Any) = {

    //    val x: List[LogicFor[?]] = List(Logic1(), Logic2())
    //    val logics = ctx.getBeansOfType(classOf[LogicFor[?]]).values()
    logics.stream()
      .filter(l => l.predicate(event))
      .forEach(l => l.asInstanceOf[LogicFor[event.type]].accept(event))

//    log.info("Logics: {}", logics)
    output.doNothing()
    new Result("OK", 0)
  }

  @RequestMapping(path = Array("/data"))
  def someData(): String = {
    log.info("System data requested")
    "Some data: available procs: " + Runtime.getRuntime.availableProcessors()
  }
}
