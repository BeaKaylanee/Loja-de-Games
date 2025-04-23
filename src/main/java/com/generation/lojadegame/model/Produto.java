package com.generation.lojadegame.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O Atributo nome é obrigatório! ")
	@Size(min = 5, max = 100, message = "O atributo  nome deve conter no mínimo 05 e no máximo 100 caracteres !")
	private String nome;

	@NotBlank(message = "O Atributo descricao é obrigatório! ")
	@Size(min = 5, max = 1000, message = "O atributo  descricao deve conter no mínimo 05 e no máximo 1000 caracteres !")
	private String descricao;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull(message = "O preço é obrigatório!")
	@Positive(message = "O preço deve ser maior do que 0")
	private BigDecimal preco;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate datalancamento;

	@NotNull(message = "A quantidade é obrigatória!")
	@Positive(message = "A quantidade deve ser maior que zero.")
	private int quantidade;

	@Size(min = 5, max = 1000)
	private String plataforma;

	@ManyToOne
	@JsonIgnoreProperties("Produtos")
	private Categoria categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDate getDatalancamento() {
		return datalancamento;
	}

	public void setDatalancamento(LocalDate datalancamento) {
		this.datalancamento = datalancamento;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
