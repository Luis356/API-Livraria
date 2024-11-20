package com.biblioteca.domain.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import com.biblioteca.shared.StatusEmprestimo;

@Data
@Builder
public class EmprestimoDTO {
    private Long id; // ID do empréstimo
    private Long usuarioId ; // Referência ao DTO do usuário
    private Long livroId; // Referência ao DTO do livro
    private LocalDate dataEmprestimo; // Data do empréstimo
    private LocalDate dataDevolucaoPrevista; // Data de devolução prevista
    private LocalDate dataDevolucaoRealizada; // Data de devolução realizada
    private StatusEmprestimo status; // Status do empréstimo (ativo, concluído, em atraso)
    private Double multaAplicada; // Multa aplicada (caso haja atraso)
}