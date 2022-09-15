package my.scala.study.akka.example

import org.junit.jupiter.api.{Disabled, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.{MockMvcResultHandlers, MockMvcResultMatchers}

@SpringBootTest
@AutoConfigureMockMvc
class ActorApiTest @Autowired()(mockMvc: MockMvc){

  @Test def hello(): Unit = {
    mockMvc.perform(MockMvcRequestBuilders.get("/actors/simple").contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.content().string("ASD"))
      .andExpect(MockMvcResultMatchers.status().isOk)
    //      .andDo(MockMvcResultHandlers.print())
  }
}
