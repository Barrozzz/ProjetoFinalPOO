package com.classes.DTO;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String alergias;

    public Cliente() {}

    public Cliente(int id, String nome, String cpf, String alergias) {
        this.id = id; this.nome = nome; this.cpf = cpf; this.alergias = alergias;
    }

    // getters/setters
    public int getId() { 
    	return id; 
    }
    public void setId(int id) { 
    	this.id = id; 
    }
    public String getNome() { 
    	return nome; 
    }
    public void setNome(String nome) {
    	this.nome = nome; }
    public String getCpf() { 
    	return cpf; 
    }
    public void setCpf(String cpf) { 
    	this.cpf = cpf; 
    }
    public String getAlergias() { 
    	return alergias; 
    }
    public void setAlergias(String alergias) { 
    	this.alergias = alergias; 
    }
}
