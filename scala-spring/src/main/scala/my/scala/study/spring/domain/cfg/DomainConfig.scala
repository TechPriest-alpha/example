package my.scala.study.spring.domain.cfg

import org.springframework.boot.context.properties.{ConfigurationProperties, ConstructorBinding}

@ConfigurationProperties(value = "domain")
@ConstructorBinding
class DomainConfig(val custom1: Custom, val custom2: Custom) {
  override def toString: String = s"\ncustom1: $custom1\ncustom2: $custom2"
}

class Custom(val key1: String, val key2: Boolean, val key3: Int) {
  override def toString: String = s"\nkey1: $key1,\nkey2: $key2,\nkey3: $key3"
}
