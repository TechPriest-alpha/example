package my.scala.study

trait Stringable {
  private lazy val cache: String = {
    makeStr()
  }

  override def toString: String = {
    cache
  }

  private def makeStr(): String = {
    val clazz = this.getClass
    val all = clazz.getDeclaredFields ++ clazz.getFields;
    val tmp = all
      .filter(f => !f.getName.contains("$"))
      .map(f => {
        f.setAccessible(true)
        val value = f.get(this)
        val valueStr = if (value == null) "null" else value.toString;
        f.getName + ": '" + valueStr + "'"
      }).mkString("{", ", ", "}")
    clazz.getSimpleName + ": " + tmp
  }
}
