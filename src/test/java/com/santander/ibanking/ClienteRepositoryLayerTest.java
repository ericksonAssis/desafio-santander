package com.santander.ibanking;


import com.santander.ibanking.model.entity.Cliente;
import com.santander.ibanking.model.entity.Movimento;
import com.santander.ibanking.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class ClienteRepositoryLayerTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    void salva_novo_cliente(){

        Cliente cliente = Cliente.builder()
                .saldo(BigDecimal.ZERO)
                .numeroConta("333")
                .nome("João")
                .dataNascimento(LocalDate.now().minusYears(18))
                .planoExclusive(false)
                .build();
        Cliente savedCustomer = clienteRepository.save(cliente);
        assertThat(false, is(savedCustomer.getNumeroConta().isEmpty()));
    }

    @Test
    void lista_todos_clientes(){

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(true, is(clientes.size()>0));
    }
}
