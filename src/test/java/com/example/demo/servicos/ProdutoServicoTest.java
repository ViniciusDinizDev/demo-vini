package com.example.demo.servicos;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.modelos.Produto;
import com.example.demo.repositorios.ProdutoRepositorio;

@ExtendWith(MockitoExtension.class)
public class ProdutoServicoTest {
    @Mock
    private ProdutoRepositorio prodRepo;

    @InjectMocks
    private ProdutoServico prodServ;

    Produto produto = new Produto();

    @BeforeEach 
    void setup(){
        produto.setNome("Café");
        produto.setDataDeValidade(LocalDate.of(2026, 11, 12));
        produto.setQuantidadeEmEstoque(50);
    }
    
    @Test
    @DisplayName("Deve retornar a lista de produtos em estoque crítico")
    void deveRetornarBuscarEstoquesCriticos() {
        //vai ver isso tá errado por causa do stub  
        List<Produto> esperado = List.of(produto);
        when(prodRepo.buscarProdutosComEstoqueCritico(10)).thenReturn(esperado);

        List<Produto> resultado = prodServ.buscarEstoquesCriticos(10);

        assertThat(resultado).hasSize(1).containsExactly(produto);
        verify(prodRepo).buscarProdutosComEstoqueCritico(10);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar estoques criticos")
    void deveLancarExcecaoBuscarEstoquesCriticos() {
        // when(prodRepo.buscarProdutosComEstoqueCritico(5)).thenReturn();
        // Acho que não preciso disso porque por padrão se não me engano retorna nulo

        assertThatThrownBy(() -> prodServ.buscarEstoquesCriticos(5))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Nenhum produto em estoque crítico");
    
        verify(prodRepo).buscarProdutosComEstoqueCritico(5);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar")
    void deveLancarExcecaoSalvar() {
        produto.setQuantidadeEmEstoque(-1);

    assertThatThrownBy(() -> prodServ.salvar(produto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Quantidade não pode ser negativa");

    verify(prodRepo, never()).save(any(Produto.class));
    }
}
