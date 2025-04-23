package com.generation.lojadegame.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.lojadegame.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	public List<Produto> findAllByPlataformaContainingIgnoreCase(@Param("pataforma") String plataforma);

	public List<Produto> findAllByCategoriaDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

	public List<Produto> findByPreco(BigDecimal preco);

}
