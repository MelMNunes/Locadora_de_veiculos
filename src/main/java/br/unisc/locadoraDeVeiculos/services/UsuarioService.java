package br.unisc.locadoraDeVeiculos.services;

import br.unisc.locadoraDeVeiculos.entidades.Usuario;
import br.unisc.locadoraDeVeiculos.entidades.Veiculo;
import br.unisc.locadoraDeVeiculos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado!"));
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verifica se já existe um usuário com o mesmo email
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        // Define o tipo com base no domínio do email
        String email = usuario.getEmail();
        if (email.endsWith("@admin.com")) {
            usuario.setTipo("ADMIN");
        } else if (email.endsWith("@func.com")) {
            usuario.setTipo("FUNCIONARIO");
        } else {
            usuario.setTipo("USUARIO");
        }

        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id); // Verifica se o usuario existe
        usuarioRepository.delete(usuario); // Exclui o veículo
    }


}

