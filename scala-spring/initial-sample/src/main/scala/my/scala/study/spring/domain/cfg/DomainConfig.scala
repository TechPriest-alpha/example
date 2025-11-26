package my.scala.study.spring.domain.cfg

import my.scala.study.Stringable
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "domain")
@ConstructorBinding
class DomainConfig(val custom1: Custom, val custom2: Custom) extends Stringable {

}

class Custom(val key1: String, val key2: Boolean, val key3: Int) extends Stringable {
}
