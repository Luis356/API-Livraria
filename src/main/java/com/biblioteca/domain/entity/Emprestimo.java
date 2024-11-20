package com.biblioteca.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.biblioteca.shared.StatusEmprestimo;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usando auto increment
    private Long id; // ID do empréstimo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Relacionamento com Usuário

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro; // Relacionamento com Livro

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo; // Data do empréstimo

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDate dataDevolucaoPrevista; // Data de devolução prevista

    
    @Column(name = "data_devolucao_realizada")
    private LocalDate dataDevolucaoRealizada; // Data de devolução realizada
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status; // Status do empréstimo (ativo, concluído, em atraso)
    
    @Column(name = "multa_aplicada")
    private Double multaAplicada; // Multa aplicada (caso haja atraso)
    
    @Transient // Evita salvar diretamente no banco
    public static final double MULTA_POR_DIA = 2.00; // Exemplo: R$ 2,00 por dia
}
