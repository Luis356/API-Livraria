package com.biblioteca.domain.service;

import java.util.List;
import com.biblioteca.domain.dto.UsuarioDTO;

public interface IUsuarioService {

    // Método para cadastrar um novo usuário
    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO);

    // Método para atualizar um usuário existente
    UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioAtualizado);

    // Método para remover um usuário pelo ID
    void removerUsuario(Long id);

    // Método para listar todos os usuários
    List<UsuarioDTO> listarTodosUsuarios();

    // Método para buscar um usuário por ID
    UsuarioDTO buscarPorId(Long id);

    // Declaração do método para buscar usuário por email
    UsuarioDTO buscarPorEmail(String email);
}