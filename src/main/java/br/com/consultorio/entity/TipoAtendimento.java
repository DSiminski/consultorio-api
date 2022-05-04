package br.com.consultorio.entity;

public enum  TipoAtendimento {
        plano("Plano"),
        convenio("Convenio");


        public final String valor;

        private TipoAtendimento(String valor){
            this.valor = valor;
        }
    }
