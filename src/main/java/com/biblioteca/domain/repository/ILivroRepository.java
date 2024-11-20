package com.biblioteca.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.biblioteca.domain.entity.Livro;

@Repository
public interface ILivroRepository extends JpaRepository<Livro, Long> {

    // Buscar livro pelo título
    Optional<Livro> findByTitulo(String titulo);

    // Buscar livros pelo autor
    Optional<List<Livro>> findByAutor(String autor);

    List<Livro> findByDisponibilidadeTrue(); // Livros disponíveis para empréstimo

    @Modifying
    @Query("UPDATE Livro l SET l.disponibilidade = :disponibilidade WHERE l.id = :id")
    void atualizarDisponibilidade(@Param("id") Long id, @Param("disponibilidade") Boolean disponibilidade);

}
