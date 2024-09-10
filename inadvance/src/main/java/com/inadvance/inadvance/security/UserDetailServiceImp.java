package com.inadvance.inadvance.security;


import com.inadvance.inadvance.Dao.IUsuarioDao;
import com.inadvance.inadvance.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Usuario usuario = usuarioRepository.findByEmail(email);
            return new UserDetailImpl(usuario);
        } catch (Exception e){
            throw new UsernameNotFoundException("El usuario no fue encontrado" + email + "no existe. ");
        }
      }
}
