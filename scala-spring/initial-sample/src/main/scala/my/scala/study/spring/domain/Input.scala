package my.scala.study.spring.domain

import my.scala.study.Loggable
import my.scala.study.spring.BootConfig
import my.scala.study.spring.domain.cfg.DomainConfig
import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2, Result}
import my.scala.study.spring.system.{LogicFor, OutputMarker}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

import java.util
import java.util.concurrent.CompletableFuture
import scala.collection.mutable
import scala.concurrent.Future
import scala.jdk.CollectionConverters
import scala.reflect.ClassTag


@RestController("domainInput")
@RequestMapping(path = Array("/domain"))
class Input @Autowired()(val logics: java.util.List[LogicFor[?]], val output: Output) extends Loggable {

  @Async
  @PostMapping(value = Array("/event1"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent1): CompletableFuture[Result] = {
    handle(event)
  }

  @Async
  @PostMapping(value = Array("/event2"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent2): CompletableFuture[Result] = {
    handle(event)
  }

  private def handle(event: Any): CompletableFuture[Result] = {

    //    val x: List[LogicFor[?]] = List(Logic1(), Logic2())
    //    val logics = ctx.getBeansOfType(classOf[LogicFor[?]]).values()
    val execution = Seq(logics.stream()
      .filter(l => l.predicate(event))
      .map(l => l.asInstanceOf[LogicFor[event.type]].accept(event))
      .map(f => f.whenComplete((result, error) => output.process(result, error)))
      .toArray[CompletableFuture[Any]])

//    val x = execution.toArray[CompletableFuture[?]]

    CompletableFuture.allOf(execution.toArray:*)
      .handle((r, e) => new Result("OK", 0))
      execution.get(0).handle((r, e) => new Result("OK", 0))
    //    log.info("Logics: {}", logics)

  }

  @RequestMapping(path = Array("/data"))
  def someData(): String = {
    log.info("System data requested")
    "Some data: available procs: " + Runtime.getRuntime.availableProcessors()
  }
}
