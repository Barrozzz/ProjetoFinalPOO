package com.classes.Controller;

import com.classes.DAO.RemedioDAO;
import com.classes.DAO.VendaDAO;

public class RelatorioController {
    private VendaDAO vendaDAO = new VendaDAO();
    private RemedioDAO remedioDAO = new RemedioDAO();

    public void maisVendidos() {
        vendaDAO.reportMaisVendidos();
    }

    public void resumoEstoque() {
        remedioDAO.listar().forEach(r -> System.out.println(r.getId() + " | " + r.getNome() + " | Qtde: " + r.getQuantidade() + " | Val: " + r.getValidade()));
    }
}
