package com.generation.lojadegame.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojadegame.model.Produto;
import com.generation.lojadegame.repository.CategoriaRepository;
import com.generation.lojadegame.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@GetMapping("/plataforma/{plataforma}")
	public ResponseEntity<List<Produto>> getByPlataforma(@PathVariable String plataforma) {
		return ResponseEntity.ok(produtoRepository.findAllByPlataformaContainingIgnoreCase(plataforma));
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Produto>> getByDescricao(@PathVariable String descricao) {
		List<Produto> produtos = produtoRepository.findAllByCategoriaDescricaoContainingIgnoreCase(descricao);
		if (produtos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/preco/{preco}")
	public ResponseEntity<List<Produto>> getByPreco(@PathVariable BigDecimal preco) {
		List<Produto> produtos = produtoRepository.findByPreco(preco);
		if (produtos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		if (!categoriaRepository.existsById(produto.getCategoria().getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"A categoria informada (ID: " + produto.getCategoria().getId() + ") não existe.");
		}

		Produto produtoSalvo = produtoRepository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		if (!produtoRepository.existsById(produto.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (!categoriaRepository.existsById(produto.getCategoria().getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe.");
		}

		Produto produtoAtualizado = produtoRepository.save(produto);
		return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		produtoRepository.deleteById(id);
	}
}
