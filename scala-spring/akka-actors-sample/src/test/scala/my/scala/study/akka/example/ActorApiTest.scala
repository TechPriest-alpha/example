package my.scala.study.akka.example

import akka.actor.typed.ActorSystem
import my.scala.study.akka.example.GreeterMain.SayHello
import org.junit.jupiter.api.{Disabled, Test}
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.{MockMvcResultHandlers, MockMvcResultMatchers}

@SpringBootTest
@AutoConfigureMockMvc
class ActorApiTest @Autowired()(mockMvc: MockMvc) {

  @MockBean
  var actor: ActorSystem[GreeterMain.SayHello] = null

  @Test def hello(): Unit = {
    val wow = "wow"
    mockMvc.perform(MockMvcRequestBuilders.get(s"/actors/simple/$wow").contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.content().string("ASD"))
      .andExpect(MockMvcResultMatchers.status().isOk)
    //      .andDo(MockMvcResultHandlers.print())
    Mockito.verify(actor).tell(SayHello(wow))
  }
}
