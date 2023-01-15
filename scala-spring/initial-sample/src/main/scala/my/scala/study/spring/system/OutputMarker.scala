package my.scala.study.spring.system

trait OutputMarker {

  def doNothing(): Unit

  def process(partialResult: PartialResult, error: Throwable): Unit
}
