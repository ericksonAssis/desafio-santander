package com.santander.ibanking.service.request;

public enum RegraTaxaAdm {

    RegraSaqueUm("Isento de taxa de saque, valor menor ou igual à R$ 100"),
    RegraSaqueDois("Taxa de 0.4%, valor maior que R$ 100,00 e menor ou igual a R$ 300,00"),
    RegraSaqueTres("Taxa de 1%, valor maior que R$ 300,00"),
    RegraSaqueExclusive("Isento de Taxa de saque, cliente Plano Exclusive"),
    RegraDeposito("Isento de Taxa, é um depósito");

    private String descricao;

    RegraTaxaAdm(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
