package com.biblioteca.domain.entity;

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
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usando auto increment
    private Long id; // ID do Livro

    @Column(nullable = false)
    private String titulo; // Titulo do livro

    @Column(nullable = false)
    private String autor; // autor do livro

    @Column(nullable = false)
    private String editora; // editora do livro

    @Column(name = "Ano_Publicacao", nullable = false)
    private int anoPublicacao; // ano de publicacao do livro

    @Column(name = "Disponibilidade")
    private boolean disponibilidade; // Status do disponivel ou emprestado

}