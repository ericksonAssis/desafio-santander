package com.santander.ibanking.repository;

import com.santander.ibanking.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByNumeroConta(String numeroConta);
}
