package my.scala.study.akka.example

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
class ActorApiTest @Autowired()(mockMvc: MockMvc){

  @Test def hello(): Unit = {
    mockMvc.perform(MockMvcRequestBuilders.get("/actors/simple"))
      .andExpect(result => result.getResponse.getContentAsString.equals("Hello name !"))
  }
}
