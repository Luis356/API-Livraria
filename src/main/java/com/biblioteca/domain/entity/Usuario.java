package com.biblioteca.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usando auto increment
    private Long id; // ID do usuario

    @Column(nullable = false)
    private String nome; // Nome do usuario

    @Column(nullable = false, unique = true)
    private String email; // E-mail do usuario

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro; // data de cadastro do usuario

    @Column(name = "quantidade_livros_emprestados", nullable = false)
    private int quantidadeLivrosEmprestados; // Quantidade de livros atualmente emprestados

}