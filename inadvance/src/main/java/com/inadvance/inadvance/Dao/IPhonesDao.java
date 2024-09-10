package com.inadvance.inadvance.Dao;

import com.inadvance.inadvance.entity.Phones;
import com.inadvance.inadvance.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface IPhonesDao extends JpaRepository<Phones, Long> {

    Phones findOneById(Long id);
}
