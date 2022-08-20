package my.scala.study.spring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import my.scala.study.spring.domain.dto.{DomainEvent, Result}
import my.scala.study.spring.system.Loggable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class InputTest @Autowired()(mockMvc: MockMvc, mapper: ObjectMapper) extends Loggable {


  @Test def hello(): Unit = {
    mockMvc.perform(MockMvcRequestBuilders.post("/domain/event")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(DomainEvent("example"))))
      .andExpect(result => {
        val x: Result = mapper.readValue(result.getResponse.getContentAsString, classOf[Result])
        assert(x.code == 0, "Code wrong")
        assert(x.response == "OK", "Response wrong")
      })
  }

}