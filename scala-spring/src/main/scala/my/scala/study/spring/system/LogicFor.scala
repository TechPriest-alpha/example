package my.scala.study.spring.system

import java.util.concurrent.ConcurrentHashMap
import scala.reflect.ClassTag

trait LogicFor[A](implicit tag: ClassTag[A]) extends Loggable {

  val processedEvents = new ConcurrentHashMap[String, A]()

  def accept(event: A): Unit;

  def predicate(any: Any): Boolean = {
    tag.runtimeClass.isAssignableFrom(any.getClass)
  }
}