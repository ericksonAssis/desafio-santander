package com.santander.ibanking;

import com.santander.ibanking.model.entity.Movimento;
import com.santander.ibanking.repository.MovimentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class MovimentoRepositoryLayerTest {

    @Autowired
    MovimentoRepository movimentoRepository;

    @Test
    void salva_movimento(){
        Movimento movimento = criaMovimento();
        Movimento savedMovimento = movimentoRepository.save(movimento);
        assertThat(true, is(savedMovimento != null));
    }

    private static Movimento criaMovimento() {
        return Movimento.builder().numeroConta("200")
                .valorTaxaAdministracao(BigDecimal.TEN)
                .tipoMovimentacao("DEPOSITO")
                .valorMovimentacao(BigDecimal.valueOf(1000.00))
                .saldoAnterior(BigDecimal.ZERO)
                .regra_taxa_aplicada("Isento")
                .saldoAtual(BigDecimal.valueOf(Double.parseDouble("1000.00")))
                .dataMovimentacao(LocalDateTime.now())
                .build();
    }

    @Test
    void realiza_consulta_historico(){
        salva_movimento();
        List<Movimento> movimentos = movimentoRepository.findAll();
        assertThat(true, is(movimentos.size()>0));
    }
}
