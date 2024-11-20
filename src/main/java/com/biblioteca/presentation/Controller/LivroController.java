package com.biblioteca.presentation.Controller;

import com.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.domain.service.ILivroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livro", description = "APIs relacionadas a livros")
public class LivroController {

    @Autowired
    private ILivroService livroService;

    // Endpoint para cadastrar um novo livro
    @PostMapping
    public LivroDTO cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        return livroService.cadastrarLivro(livroDTO);
    }

    // Endpoint para atualizar um livro existente
    @PutMapping("/{id}")
    public LivroDTO atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO livroAtualizado) {
        return livroService.atualizarLivro(id, livroAtualizado);
    }

    // Endpoint para remover um livro pelo ID
    @DeleteMapping("/{id}")
    public void removerLivro(@PathVariable Long id) {
        livroService.removerLivro(id);
    }

    // Endpoint para listar todos os livros
    @GetMapping
    public List<LivroDTO> listarTodosLivros() {
        return livroService.listarTodosLivros();
    }

    // Endpoint para buscar livro por ID
    @GetMapping("/{id}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    // Endpoint para listar livros disponíveis
    @GetMapping("/disponiveis")
    public List<LivroDTO> listarLivrosDisponiveis() {
        return livroService.listarLivrosDisponiveis();
    }
}