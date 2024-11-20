package com.biblioteca.presentation.Controller;

import com.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.domain.service.IEmprestimoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
@Tag(name = "Emprestimo", description = "APIs relacionadas a emprestimo")
public class EmprestimoController {

    @Autowired
    private IEmprestimoService emprestimoService;

    @PostMapping
    public EmprestimoDTO cadastrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        return emprestimoService.cadastrarEmprestimo(emprestimoDTO);
    }

    @GetMapping
    public List<EmprestimoDTO> listarTodosEmprestimos() {
        return emprestimoService.listarTodosEmprestimos();
    }

    @PutMapping("/{id}")
    public EmprestimoDTO atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO emprestimoAtualizado) {
        return emprestimoService.atualizarEmprestimo(id, emprestimoAtualizado);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarEmprestimo(
            @PathVariable Long id,
            @RequestBody LocalDate dataDevolucaoRealizada) {
        emprestimoService.finalizarEmprestimo(id, dataDevolucaoRealizada);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<EmprestimoDTO> listarEmprestimosPorUsuario(@PathVariable Long usuarioId) {
        return emprestimoService.listarEmprestimosPorUsuario(usuarioId);
    }

    @GetMapping("/livro/{livroId}")
    public List<EmprestimoDTO> listarEmprestimosPorLivro(@PathVariable Long livroId) {
        return emprestimoService.listarEmprestimosPorLivro(livroId);
    }

    @PutMapping("/{id}/renovar")
    public ResponseEntity<EmprestimoDTO> renovarEmprestimo(@PathVariable Long id) {
        EmprestimoDTO emprestimoDTO = emprestimoService.renovarEmprestimo(id);
        return ResponseEntity.ok(emprestimoDTO);
    }
}
