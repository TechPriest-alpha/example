package my.scala.study.spring.domain

import my.scala.study.spring.domain.dto.{DomainEvent, Result}
import my.scala.study.spring.system.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RestController}

@RestController("domainInput")
@RequestMapping(Array("/domain"))
class Input @Autowired()(logic: Logic, output: Output) extends Loggable {

  @RequestMapping(value = Array("/event"), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
  def acceptChange(@RequestBody event: DomainEvent): Result = {
    logic.accept(event)
    output.doNothing()
    new Result("OK", 0)
  }
}
