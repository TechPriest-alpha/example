package my.scala.study.spring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import my.scala.study.Loggable
import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2, Result}
import my.scala.study.spring.system.{OutputMarker, PartialResult}
import org.junit.jupiter.api.{BeforeEach, Disabled, Test}
import org.mockito.{ArgumentMatchers, Mockito}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.{MockBean, SpyBean}
import org.springframework.context.annotation.{Configuration, EnableAspectJAutoProxy, Import}
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request

import javax.naming.PartialResultException

@SpringBootTest
@AutoConfigureMockMvc
class InputTest @Autowired()(mockMvc: MockMvc, mapper: ObjectMapper, logic1: Logic1, logic2: Logic2) extends Loggable {

  @SpyBean
  var output: Output = null


  @BeforeEach def setup(): Unit = {
    logic1.processedEvents.clear()
    logic2.processedEvents.clear()
  }

  @Test def hello1(): Unit = {
    val event = DomainEvent1("example1")
    val requestBuilder = MockMvcRequestBuilders.post("/domain/event1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(event))

    val asyncRequestMock = mockMvc.perform(requestBuilder)
      .andExpect(request().asyncStarted())
      .andReturn()

    mockMvc.perform(asyncDispatch(asyncRequestMock))
      .andExpect(result => {
        val x: Result = mapper.readValue(result.getResponse.getContentAsString, classOf[Result])
        assert(x.code == 0, "Code wrong")
        assert(x.response == "OK", "Response wrong")
      })
    assert(logic1.processedEvents.containsKey(event.data1))
    assert(logic1.processedEvents.containsValue(event))
    assert(logic2.processedEvents.isEmpty)

    Mockito.verify(output).process(ArgumentMatchers.any(classOf[PartialResult]), ArgumentMatchers.eq(null))
  }

  @Test def hello2(): Unit = {
    val event = DomainEvent2("example2")
    val requestBuilder = MockMvcRequestBuilders.post("/domain/event2")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(event))

    val asyncRequestMock = mockMvc.perform(requestBuilder)
      .andExpect(request().asyncStarted())
      .andReturn()


    mockMvc.perform(asyncDispatch(asyncRequestMock))
      .andExpect(result => {
        val x: Result = mapper.readValue(result.getResponse.getContentAsString, classOf[Result])
        assert(x.code == 0, "Code wrong")
        assert(x.response == "OK", "Response wrong")
      })
    assert(logic2.processedEvents.containsKey(event.data2))
    assert(logic2.processedEvents.containsValue(event))
    assert(logic1.processedEvents.isEmpty)
    Mockito.verify(output).process(ArgumentMatchers.any(classOf[PartialResult]), ArgumentMatchers.eq(null))
  }


  @Test
  @Disabled("Since json has no typing, DomainEvent2 is deserialized as DomainEvent1 causing NPE in Logic1. Need to think of better code structure")
  def hello3(): Unit = {
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
