package br.com.consultorio.entity;

public enum StatusAgendamento {
    pendente("Pendente"),
    aprovado("Aprovado"),
    cancelado("Cancelado"),
    compareceu("Compareceu"),
    ncompareceu("Não Compareceu"),
    rejeitado ("Rejeitado");

    public final String valor;

    private StatusAgendamento(String valor){
        this.valor = valor;
    }
}
