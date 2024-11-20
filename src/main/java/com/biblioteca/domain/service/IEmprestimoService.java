package com.biblioteca.domain.service;

import com.biblioteca.domain.dto.EmprestimoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IEmprestimoService {

    EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO emprestimoDTO);

    EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO emprestimoAtualizado);

    EmprestimoDTO buscarPorId(Long id);

    EmprestimoDTO renovarEmprestimo(Long id);

    void finalizarEmprestimo(Long id, LocalDate dataDevolucaoRealizada);

    List<EmprestimoDTO> listarTodosEmprestimos();

    List<EmprestimoDTO> listarEmprestimosPorUsuario(Long usuarioId);

    List<EmprestimoDTO> listarEmprestimosPorLivro(Long livroId);

}
