package tech.pinho.caixateste.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.pinho.caixateste.repository.ContaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContaRepository contaRepository;

    @Test
    @Transactional
    void deveCriarConta() throws Exception {
        mockMvc.perform(
                        post("/contas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "titular": "Bruno"
                                        }
                                        """)
                )
                .andExpect(status().isOk());

        assertTrue(contaRepository.findById(1).isPresent());
        assertEquals("Bruno", contaRepository.findById(1).get().getTitular());

    }

    @Test
    void deveListarContas() throws Exception {
        mockMvc.perform(
                        get("/contas")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
