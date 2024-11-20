package com.biblioteca.domain.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private int quantidadeLivrosEmprestados;
}
