package com.inadvance.inadvance.service;

import com.inadvance.inadvance.Dao.IPhonesDao;
import com.inadvance.inadvance.Dao.IStatusDao;
import com.inadvance.inadvance.Dao.IUsuarioDao;
import com.inadvance.inadvance.controller.UserController;
import com.inadvance.inadvance.entity.Phones;
import com.inadvance.inadvance.entity.Status;
import com.inadvance.inadvance.entity.Usuario;
import com.inadvance.inadvance.security.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class UsuarioServiceImp implements IUsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImp.class);
    @Autowired
    public IUsuarioDao usuarioService;
    @Autowired
    public IStatusDao statusService;

    @Autowired
    public IPhonesDao phoneService;



    @Override
    @Transactional
    public Usuario getUsuario(Usuario usuario, HttpServletRequest request ) {
        this.setBitacoraDeCambios(request, "obtengo Usuario "+ usuario.getEmail());
        return usuarioService.findByEmail(usuario.getEmail());
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Usuario usuario, HttpServletRequest request) {
        logger.info("Ingreso a updateUsuario");

        // Buscar el usuario por email
        Usuario usuarioTemp = usuarioService.findByEmail(usuario.getEmail());

        // Verificar si el usuario existe
        if (usuarioTemp == null) {
            throw new IllegalArgumentException("El usuario con el correo " + usuario.getEmail() + " no existe.");
        }

        // Actualizar los datos del usuario
        usuarioTemp.setName(usuario.getName());
        usuarioTemp.setFechaModificacion(new Date());

        // Verificar si el usuario tiene teléfonos
        if (usuario.getPhones() != null && !usuario.getPhones().isEmpty()) {
            // Limpiar la lista de teléfonos existente
            usuarioTemp.getPhones().clear();

            // Iterar sobre la lista de teléfonos nuevos
            for (Phones phone : usuario.getPhones()) {
                // Crear un nuevo teléfono para asegurarse de que los datos se copien correctamente
                Phones nuevoPhone = new Phones();
                nuevoPhone.setNumber(phone.getNumber());
                nuevoPhone.setCitycode(phone.getCitycode());
                nuevoPhone.setCountrycode(phone.getCountrycode());
                nuevoPhone.setUsuario(usuarioTemp); // Asignar el usuario actual al teléfono

                // Guardar el nuevo teléfono en la base de datos
                phoneService.save(nuevoPhone);

                // Agregar el teléfono a la lista de teléfonos del usuario
                usuarioTemp.getPhones().add(nuevoPhone);
            }
        }

        // Guardar el usuario actualizado junto con los teléfonos
        return usuarioService.save(usuarioTemp);
    }

    @Override
    @Transactional
    public List<Usuario> getUsuarios( HttpServletRequest request) {
        logger.info("ingreso a getUsuarios");
        this.setBitacoraDeCambios(request, "Obtengo todos los usuarios ");
        return usuarioService.findAll();
    }

    @Override
    @Transactional
    public Usuario setUsuarios(Usuario usuario, HttpServletRequest request) {
        logger.info("Ingreso a setUsuarios");
        this.setBitacoraDeCambios(request, "Crea un nuevo usuario con el email  "+ usuario.getEmail());
        // Verificar si el email ya está en uso por otro usuario
        Usuario usuarioExistente = usuarioService.findByEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }
        usuario.setFechaModificacion(new Date());
        usuario.setFechaCreacion(new Date());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()) );
        // Asignar el usuario a cada phone antes de guardar
        if (usuario.getPhones() != null) {
            for (Phones phone : usuario.getPhones()) {
                phone.setUsuario(usuario); // Asignar el usuario a cada phone
            }
        }

        // Guardar el usuario y sus phones
        return usuarioService.save(usuario);
    }

    @Override
    @Transactional
    public void setBitacoraDeCambios(HttpServletRequest valor, String actividad) {
        String token = valor.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            // Eliminar el prefijo "Bearer " del token
            String nuevoToken = token.replace("Bearer ", "");

            // Obtener la autenticación desde el token
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) TokenUtils.getAuthentication(nuevoToken);
            if (auth == null || auth.getName() == null) {
                logger.error("El token no es válido o no se pudo extraer el usuario.");
                return; // Salir si el token no es válido
            }

            // Buscar el usuario por el email extraído del token
            String email = auth.getName();
            Usuario usuario = usuarioService.findByEmail(email);

            if (usuario == null) {
                logger.error("No se encontró el usuario con el correo: {}", email);
                return; // Salir si no se encontró el usuario
            }

            // Crear y asignar valores al objeto Status (bitácora)
            Status status = new Status();
            status.setToken(nuevoToken);
            status.setFechaUltimoIngreso(new Date());
            status.setName(auth.getName()); // Asignar el nombre de usuario
            status.setActividad(actividad); // Registra la actividad realizada

            // Guardar el nuevo registro de bitácora
            statusService.save(status);

            logger.info("Bitácora actualizada para el usuario: {} con actividad: {}", usuario.getEmail(), actividad);
        } else {
            logger.warn("El token es nulo o vacío, no se puede actualizar la bitácora.");
        }
    }
}