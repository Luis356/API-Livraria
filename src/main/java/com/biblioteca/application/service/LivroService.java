package com.biblioteca.application.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.application.Mappers;
import com.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.domain.entity.Livro;
import com.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.domain.service.ILivroService;
import com.biblioteca.shared.CustomException;

@Service
public class LivroService implements ILivroService {

    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private Mappers mapper;

    // Cadastrar um novo livro
    @Override
    public LivroDTO cadastrarLivro(LivroDTO livroDTO) {
        Livro livro = mapper.livroDtoToEntity(livroDTO);
        livro = livroRepository.save(livro);
        return mapper.livroToDto(livro);
    }

    // Atualizar um livro existente
    @Override
    public LivroDTO atualizarLivro(Long id, LivroDTO livroAtualizado) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new CustomException("Livro não encontrado com o ID: " + id));
        
        // Atualiza os campos do livro com os dados do DTO
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setEditora(livroAtualizado.getEditora());
        livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livro.setDisponibilidade(livroAtualizado.isDisponibilidade());

        livro = livroRepository.save(livro);
        return mapper.livroToDto(livro);
    }

    // Remover um livro pelo ID
    @Override
    public void removerLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    // Listar todos os livros
    @Override
    public List<LivroDTO> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(mapper::livroToDto)
                .collect(Collectors.toList());
    }

    // Buscar livro por ID
    @Override
    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new CustomException("Livro não encontrado com o ID: " + id));
        return mapper.livroToDto(livro);
    }

    // Buscar livro por título
    @Override
    public LivroDTO buscarPorTitulo(String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new CustomException("Livro não encontrado com o título: " + titulo));
        return mapper.livroToDto(livro);
    }

    // Buscar livro por autor
    @Override
    public List<LivroDTO> buscarPorAutor(String autor) {
        List<Livro> livros = livroRepository.findByAutor(autor)
                .orElseThrow(() -> new CustomException("Nenhum livro encontrado com o autor: " + autor));
        return livros.stream()
                .map(mapper::livroToDto)
                .collect(Collectors.toList());
    }

    // Buscar livros disponíveis
    @Override
    public List<LivroDTO> listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = livroRepository.findByDisponibilidadeTrue();
        return livrosDisponiveis.stream()
                .map(mapper::livroToDto)
                .collect(Collectors.toList());
    }
}