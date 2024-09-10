package com.inadvance.inadvance.Dao;

import com.inadvance.inadvance.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusDao extends JpaRepository<Status,Long> {
}
