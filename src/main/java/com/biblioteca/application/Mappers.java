package com.biblioteca.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.domain.entity.Livro;
import com.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.domain.entity.Usuario;
import com.biblioteca.domain.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface Mappers {

    // Converte a entidade Usuario para UsuarioDTO
    UsuarioDTO usuarioToDto(Usuario usuario);

    // Converte o DTO UsuarioDTO para a entidade Usuario
    Usuario usuarioDtoToEntity(UsuarioDTO usuarioDTO);

    // Converte a entidade Livro para LivroDTO
    LivroDTO livroToDto(Livro livro);

    // Converte o DTO LivroDTO para a entidade Livro
    Livro livroDtoToEntity(LivroDTO livroDTO);

    // Converte a entidade Emprestimo para EmprestimoDTO
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "livro.id", target = "livroId")
    EmprestimoDTO emprestimoToDto(Emprestimo emprestimo);

    // Converte o DTO EmprestimoDTO para a entidade Emprestimo
    @Mapping(source = "usuarioId", target = "usuario.id")
    @Mapping(source = "livroId", target = "livro.id")
    Emprestimo emprestimoDtoToEntity(EmprestimoDTO emprestimoDTO);

}
