package com.santander.ibanking.repository;

import com.santander.ibanking.model.entity.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
}
