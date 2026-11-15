package com.example.demo.controladores;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.modelos.Produto;
import com.example.demo.repositorios.ProdutoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepositorio prodRepo; 

    Produto produto= new Produto();
    
    @BeforeEach 
    void setup(){
        prodRepo.deleteAll();
        produto.setNome("Café");
        produto.setDataDeValidade(LocalDate.of(2020, 1, 28));
        produto.setQuantidadeEmEstoque(50);
        prodRepo.save(produto);
    }
    @Test 
    @DisplayName("Deve listar produtos")
    void deveListarProdutos(){
        try {
            mockMvc.perform(get("/produtos/estoque-critico/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Café"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test 
    @DisplayName("Deve listar estoque-critico")
    void deveListarEstoqueCritico(){
        try {
            mockMvc.perform(get("/produtos/estoque-critico/60")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Café"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test 
    @DisplayName("Deve salvar produtos")
    void deveSalvar(){
        try {
            mockMvc.perform(post("/produtos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "nome": "Leite Condensado",
                              "dataDeValidade": "2022-12-22",
                              "quantidadeEmEstoque": 13
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Leite Condensado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
