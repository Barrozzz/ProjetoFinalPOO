package com.classes.DTO;

import java.util.Date;

public class Remedio {
    private int id;
    private String nome;
    private String fabricante;
    private String principioAtivo;
    private double preco;
    private Date validade;
    private int quantidade;
    private boolean exigeReceita;

    public Remedio() {}

    public Remedio(int id, String nome, String fabricante, String principioAtivo, double preco, Date validade, int quantidade, boolean exigeReceita) {
        this.id = id; this.nome = nome; this.fabricante = fabricante; this.principioAtivo = principioAtivo;
        this.preco = preco; this.validade = validade; this.quantidade = quantidade; this.exigeReceita = exigeReceita;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public String getPrincipioAtivo() { return principioAtivo; }
    public void setPrincipioAtivo(String principioAtivo) { this.principioAtivo = principioAtivo; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public Date getValidade() { return validade; }
    public void setValidade(Date validade) { this.validade = validade; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public boolean isExigeReceita() { return exigeReceita; }
    public void setExigeReceita(boolean exigeReceita) { this.exigeReceita = exigeReceita; }
}
