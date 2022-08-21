package my.scala.study.spring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2, Result}
import my.scala.study.spring.system.Loggable
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class InputTest @Autowired()(mockMvc: MockMvc, mapper: ObjectMapper, @SpyBean output: Output, @SpyBean logic1: Logic1, @SpyBean logic2: Logic2) extends Loggable {

  @Test def hello1(): Unit = {
    val event = DomainEvent1("example1")
    mockMvc.perform(MockMvcRequestBuilders.post("/domain/event1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(event)))
      .andExpect(result => {
        val x: Result = mapper.readValue(result.getResponse.getContentAsString, classOf[Result])
        assert(x.code == 0, "Code wrong")
        assert(x.response == "OK", "Response wrong")
      })
    Mockito.verify(logic1).accept(event)
    Mockito.verify(output).doNothing();
  }

  @Test def hello2(): Unit = {
    val event = DomainEvent2("example2")
    mockMvc.perform(MockMvcRequestBuilders.post("/domain/event2")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(event)))
      .andExpect(result => {
        val x: Result = mapper.readValue(result.getResponse.getContentAsString, classOf[Result])
        assert(x.code == 0, "Code wrong")
        assert(x.response == "OK", "Response wrong")
      })
    Mockito.verify(output).doNothing();
    Mockito.verify(logic2).accept(event)
  }


  @Test def hello3(): Unit = {
    val event = DomainEvent2("example2")
    mockMvc.perform(MockMvcRequestBuilders.post("/domain/event1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(event)))
      .andExpect(result => {
        assert(result.getResponse.getErrorMessage == "")
      })
    Mockito.verify(output).doNothing();
    Mockito.verify(logic2).accept(event)
  }
}