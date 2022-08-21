package my.scala.study.spring.domain

import hacks.ClassGetter
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
class Input @Autowired()(ctx: ApplicationContext, output: Output) extends Loggable {

  @PostMapping(value = Array("/event1"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent1): Result = {
    handle(event)
  }

  @PostMapping(value = Array("/event2"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent2): Result = {
    handle(event)
  }

  private def handle(event: Any) = {

    val logics = ctx.getBeansOfType(ClassGetter.getClassOf).values()
    //    logics.stream()
    //      .map(l => l.asInstanceOf[LogicFor])
    //      .filter(l => l.predicate(event))
    //      .foreach(l => l.accept(event))
    output.doNothing()
    new Result("OK", 0)
  }

  @RequestMapping(path = Array("/data"))
  def someData(): String = {
    log.info("System data requested")
    "Some data: available procs: " + Runtime.getRuntime.availableProcessors()
  }
}
