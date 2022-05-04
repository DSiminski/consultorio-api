package br.com.consultorio.entity;

public enum Sexo {
    masculino("Maculino"),
    feminino("Feminino"),
    outro("Outros");

    public final String valor;

    private Sexo(String valor){
        this.valor = valor;
    }
}
