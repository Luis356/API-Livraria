package com.biblioteca.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.application.Mappers;
import com.biblioteca.domain.dto.UsuarioDTO;
import com.biblioteca.domain.entity.Usuario;
import com.biblioteca.domain.repository.IUsuarioRepository;
import com.biblioteca.domain.service.IUsuarioService;
import com.biblioteca.shared.CustomException;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers mapper;

    // Cadastrar um novo usuário
    @Override
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = mapper.usuarioDtoToEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return mapper.usuarioToDto(usuario);
    }

    // Atualizar um usuário existente
    @Override
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuário não encontrado com o ID: " + id));
        
        // Atualiza os campos do usuário
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setDataCadastro(usuarioAtualizado.getDataCadastro());
        usuario.setQuantidadeLivrosEmprestados(usuarioAtualizado.getQuantidadeLivrosEmprestados());

        usuario = usuarioRepository.save(usuario);
        return mapper.usuarioToDto(usuario);
    }

    // Remover um usuário pelo ID
    @Override
    public void removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // Listar todos os usuários
    @Override
    public List<UsuarioDTO> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(mapper::usuarioToDto)
                .collect(Collectors.toList());
    }

    // Buscar usuário por ID
    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuário não encontrado com o ID: " + id));
        return mapper.usuarioToDto(usuario);
    }

    // Buscar usuário por email
    @Override
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Usuário não encontrado com o email: " + email));
        return mapper.usuarioToDto(usuario);
    }
}
        