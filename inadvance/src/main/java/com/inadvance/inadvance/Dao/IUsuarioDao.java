package com.inadvance.inadvance.Dao;

import com.inadvance.inadvance.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);
}
