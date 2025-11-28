package com.classes.DTO;

public class ItemVenda {
    private int id;
    private int vendaId;
    private int remedioId;
    private int quantidade;
    private double subtotal;

    public ItemVenda() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getVendaId() { return vendaId; }
    public void setVendaId(int vendaId) { this.vendaId = vendaId; }
    public int getRemedioId() { return remedioId; }
    public void setRemedioId(int remedioId) { this.remedioId = remedioId; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
