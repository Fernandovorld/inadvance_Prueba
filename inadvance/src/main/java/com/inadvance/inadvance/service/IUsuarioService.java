package com.inadvance.inadvance.service;

import com.inadvance.inadvance.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IUsuarioService {

    public Usuario getUsuario(Usuario usuario, HttpServletRequest request);

    public Usuario updateUsuario(Usuario usuario, HttpServletRequest request);

    public List<Usuario> getUsuarios(HttpServletRequest request);

    public Usuario setUsuarios(Usuario usuario, HttpServletRequest request);

    public  void setBitacoraDeCambios(HttpServletRequest token, String actividad);

}
