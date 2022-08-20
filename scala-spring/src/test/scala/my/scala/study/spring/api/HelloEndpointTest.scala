package my.scala.study.spring.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class HelloEndpointTest @Autowired()(mockMvc: MockMvc) {


  @Test def hello(): Unit = {
    mockMvc.perform(MockMvcRequestBuilders.get("/hello/say/name"))
      .andExpect(result => result.getResponse.getContentAsString.equals("Hello name !"))
  }
}
