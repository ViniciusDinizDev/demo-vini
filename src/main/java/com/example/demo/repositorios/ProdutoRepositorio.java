package com.example.demo.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto,Long> {

    @Query("""
        SELECT p
        FROM Produto p
        WHERE p.quantidadeEmEstoque < :limite
    """)
    List<Produto> buscarProdutosComEstoqueCritico(
            @Param("limite") Integer limite
    );


    @Query("""
        SELECT p
        FROM Produto p
        WHERE p.dataDeValidade BETWEEN :hoje AND :dataLimite
    """)
    List<Produto> buscarProdutosProximosDaValidade(
            @Param("hoje") LocalDate hoje,
            @Param("dataLimite") LocalDate dataLimite
    );
}
