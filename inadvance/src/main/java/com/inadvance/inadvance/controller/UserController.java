package com.inadvance.inadvance.controller;

import com.inadvance.inadvance.entity.Usuario;
import com.inadvance.inadvance.security.JWTAuthorizationFilter;
import com.inadvance.inadvance.service.IUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.HeaderParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public IUsuarioService usuarioService;
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>>  getUsers( HttpServletRequest  token) {
        logger.info("ingreso a getUsers");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.getUsuarios( token));
    }
    @PutMapping("/userUpdate")
    public ResponseEntity<Usuario> setUsuariosUpdate(@Valid @RequestBody Usuario usuario, HttpServletRequest token){
        logger.info("ingreso a setUsuariosUpdate ");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.updateUsuario(usuario, token));
    }

    @PostMapping("/usuariosnuevos")
    public ResponseEntity<Usuario> setUsuarios(@Valid @RequestBody Usuario usuario, HttpServletRequest request){
        logger.info("ingreso a setUsuarios ");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.setUsuarios(usuario,  request));
    }


}
