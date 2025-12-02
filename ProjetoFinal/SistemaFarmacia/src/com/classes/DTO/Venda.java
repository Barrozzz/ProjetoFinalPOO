package com.classes.DTO;

import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private Integer clienteId;
    private Date dataVenda;
    private double valorTotal;
    private List<ItemVenda> itens;

    public Venda() {}

    public int getId() { 
    	return id; 
    }
    public void setId(int id) { 
    	this.id = id; 
    }
    public Integer getClienteId() { 
    	return clienteId; 
    }
    public void setClienteId(Integer clienteId) { 
    	this.clienteId = clienteId; 
    }
    public Date getDataVenda() { 
    	return dataVenda; 
    }
    public void setDataVenda(Date dataVenda) { 
    	this.dataVenda = dataVenda; 
    }
    public double getValorTotal() { 
    	return valorTotal; 
    }
    public void setValorTotal(double valorTotal) { 
    	this.valorTotal = valorTotal; 
    }
    public java.util.List<ItemVenda> getItens() { 
    	return itens; 
    }
    public void setItens(java.util.List<ItemVenda> itens) { 
    	this.itens = itens; 
    }
}
