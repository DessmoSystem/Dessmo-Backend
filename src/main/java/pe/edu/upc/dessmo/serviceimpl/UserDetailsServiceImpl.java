package pe.edu.upc.dessmo.serviceimpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.dessmo.model.Usuario;
import pe.edu.upc.dessmo.repository.IUsuarioDAO;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    IUsuarioDAO data;

    public UserDetailsServiceImpl(IUsuarioDAO data) {
        this.data = data;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = data.findByUsernameUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No Encontrado: " + username));

        return UserDetailsImpl.build(usuario);
    }
}
