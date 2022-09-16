package my.scala.study.spring.system

import my.scala.study.Loggable
import my.scala.study.spring.domain.cfg.DomainConfig
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.ConcurrentHashMap
import scala.reflect.ClassTag

trait LogicFor[A] @Autowired()(val cfg: DomainConfig)(implicit tag: ClassTag[A]) extends Loggable {

  val processedEvents = new ConcurrentHashMap[String, A]()

  def accept(event: A): Unit;

  def predicate(any: Any): Boolean = {
    log.info("Predicate applied with config: {}", cfg)
    tag.runtimeClass.isAssignableFrom(any.getClass)
  }
}