package my.scala.study.spring.domain.cfg

import my.scala.study.spring.system.Stringable
import org.springframework.boot.context.properties.{ConfigurationProperties, ConstructorBinding}

@ConfigurationProperties(value = "domain")
@ConstructorBinding
class DomainConfig(val custom1: Custom, val custom2: Custom) extends Stringable {

}

class Custom(val key1: String, val key2: Boolean, val key3: Int) extends Stringable {
}
