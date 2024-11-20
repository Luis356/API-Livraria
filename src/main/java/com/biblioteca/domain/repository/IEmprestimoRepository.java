package com.biblioteca.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.shared.StatusEmprestimo;

public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Buscar empréstimos por ID do usuário
    List<Emprestimo> findByUsuarioId(Long usuarioId);

    // Buscar empréstimos por ID do livro
    List<Emprestimo> findByLivroId(Long livroId);

    long countByUsuarioIdAndStatus(Long usuarioId, StatusEmprestimo status);

}