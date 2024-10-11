package br.unisc.locadoraDeVeiculos.controllers;

import br.unisc.locadoraDeVeiculos.entidades.Usuario;
import br.unisc.locadoraDeVeiculos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Cadastro de novo usuário
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar usuário");
        }
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestParam String email, @RequestParam String senha) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario != null && usuario.getSenha().equals(senha)) {
                return ResponseEntity.ok("Login realizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado!");
        }
    }

    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<String> excluirUsuario(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem excluir veículos");
        }
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok("Usuario excluído com sucesso");
    }
}
