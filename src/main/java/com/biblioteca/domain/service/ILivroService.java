package com.biblioteca.domain.service;

import java.util.List;
import com.biblioteca.domain.dto.LivroDTO;

public interface ILivroService {

    // Cadastrar um novo livro
    LivroDTO cadastrarLivro(LivroDTO livroDTO);

    // Atualizar um livro existente
    LivroDTO atualizarLivro(Long id, LivroDTO livroAtualizado);

    // Remover um livro pelo ID
    void removerLivro(Long id);

    // Listar todos os livros
    List<LivroDTO> listarTodosLivros();

    // Buscar livro por ID
    LivroDTO buscarPorId(Long id);

    // Listar livros disponíveis
    List<LivroDTO> listarLivrosDisponiveis();
    
    // Listar livros emprestados
    List<LivroDTO> listarLivrosEmprestados();
}
