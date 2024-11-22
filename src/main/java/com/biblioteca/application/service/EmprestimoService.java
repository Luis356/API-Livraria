package com.biblioteca.application.service;

import com.biblioteca.application.Mappers;
import com.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.domain.entity.Livro;
import com.biblioteca.domain.entity.Usuario;
import com.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.domain.repository.IUsuarioRepository;
import com.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.shared.CustomException;
import com.biblioteca.shared.StatusEmprestimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService implements IEmprestimoService {

    @Autowired
    private IEmprestimoRepository emprestimoRepository;

    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers mapper;

    @Transactional
    @Override
    public EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO emprestimoDTO) {
        // Mapeando o DTO para a entidade
        Emprestimo emprestimo = mapper.emprestimoDtoToEntity(emprestimoDTO);

        // Verificar se o usuário já atingiu o limite de empréstimos ativos
        long quantidadeEmprestimosAtivos = emprestimoRepository.countByUsuarioIdAndStatus(
                emprestimoDTO.getUsuarioId(), StatusEmprestimo.ativo);
        if (quantidadeEmprestimosAtivos >= 5) {
            throw new CustomException("O usuário já atingiu o limite de 5 livros emprestados.");
        }

        // Obter o livro para validar disponibilidade
        Livro livro = livroRepository.findById(emprestimoDTO.getLivroId())
                .orElseThrow(() -> new CustomException("Livro não encontrado com o ID: " + emprestimoDTO.getLivroId()));

        // Validar se o livro está disponível
        if (!livro.isDisponibilidade()) {
            throw new CustomException("O livro já está emprestado.");
        }

        // Atualizar a disponibilidade do livro
        livro.setDisponibilidade(false);
        livroRepository.save(livro);

        // Atualizar atributos automáticos do empréstimo
        emprestimo.setStatus(StatusEmprestimo.ativo); // Define o status como ativo
        emprestimo.setDataEmprestimo(LocalDate.now()); // Data de empréstimo é hoje
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(14)); // 14 dias para devolução
        emprestimo = emprestimoRepository.save(emprestimo);

        // Incrementar quantidade de livros emprestados do usuário
        Usuario usuario = usuarioRepository.findById(emprestimoDTO.getUsuarioId())
                .orElseThrow(
                        () -> new CustomException("Usuário não encontrado com o ID: " + emprestimoDTO.getUsuarioId()));
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() + 1);
        usuarioRepository.save(usuario);

        // Retornar o DTO atualizado
        return mapper.emprestimoToDto(emprestimo);
    }

    @Override
    public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO emprestimoAtualizado) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado com o ID: " + id));
        emprestimo.setDataDevolucaoRealizada(emprestimoAtualizado.getDataDevolucaoRealizada());
        emprestimo.setStatus(emprestimoAtualizado.getStatus());
        emprestimo.setMultaAplicada(emprestimoAtualizado.getMultaAplicada());
        emprestimo = emprestimoRepository.save(emprestimo);
        return mapper.emprestimoToDto(emprestimo);
    }

    @Transactional
    @Override
    public void finalizarEmprestimo(Long id, LocalDate dataDevolucaoRealizada) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado com o ID: " + id));

        // Atualizar status e data de devolução do empréstimo
        emprestimo.setDataDevolucaoRealizada(dataDevolucaoRealizada);
        long diasAtraso = emprestimo.getDataDevolucaoPrevista().until(dataDevolucaoRealizada).getDays();
        if (diasAtraso > 0) {
            double multa = diasAtraso * 1.0; // R$ 1,00 por dia de atraso
            emprestimo.setMultaAplicada(multa);
        } else {
            emprestimo.setMultaAplicada(0.0);
        }
        emprestimo.setStatus(StatusEmprestimo.concluido);

        // Atualizar a disponibilidade do livro
        Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(
                        () -> new CustomException("Livro não encontrado com o ID: " + emprestimo.getLivro().getId()));
        livro.setDisponibilidade(true);
        livroRepository.save(livro);

        // Decrementar quantidade de livros emprestados do usuário
        Usuario usuario = emprestimo.getUsuario();
        if (usuario != null && usuario.getQuantidadeLivrosEmprestados() > 0) {
            usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() - 1);
            usuarioRepository.save(usuario);
        }

        emprestimoRepository.save(emprestimo);
    }

    @Transactional
    @Override
    public EmprestimoDTO renovarEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado com o ID: " + id));

        // Verificar se o livro está disponível para renovação
        Livro livro = emprestimo.getLivro();
        if (!livro.isDisponibilidade()) {
            throw new CustomException("O livro não pode ser renovado, pois não está disponível.");
        }

        // Atualizar a data de devolução para mais 14 dias
        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista().plusDays(14));
        emprestimoRepository.save(emprestimo);

        return mapper.emprestimoToDto(emprestimo);
    }

    @Override
    public List<EmprestimoDTO> listarTodosEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(mapper::emprestimoToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmprestimoDTO> listarEmprestimosPorUsuario(Long usuarioId) {
        return emprestimoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::emprestimoToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmprestimoDTO> listarEmprestimosPorLivro(Long livroId) {
        return emprestimoRepository.findByLivroId(livroId)
                .stream()
                .map(mapper::emprestimoToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmprestimoDTO buscarPorId(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado com o ID: " + id));
        return mapper.emprestimoToDto(emprestimo);
    }
}