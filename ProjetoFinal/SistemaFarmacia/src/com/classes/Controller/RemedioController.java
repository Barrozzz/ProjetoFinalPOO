package com.classes.Controller;

import com.classes.DAO.RemedioDAO;
import com.classes.DTO.Remedio;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class RemedioController {
    private RemedioDAO dao = new RemedioDAO();
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public void cadastrar(Scanner sc) {
        try {
            Remedio r = new Remedio();
            System.out.println("\n--- CADASTRO DE REMÉDIO ---");
            System.out.print("Nome: "); r.setNome(sc.nextLine());
            System.out.print("Fabricante: "); r.setFabricante(sc.nextLine());
            System.out.print("Princípio ativo: "); r.setPrincipioAtivo(sc.nextLine());
            System.out.print("Preço: "); r.setPreco(Double.parseDouble(sc.nextLine()));
            System.out.print("Validade (yyyy-MM-dd): "); r.setValidade(fmt.parse(sc.nextLine()));
            System.out.print("Quantidade: "); r.setQuantidade(Integer.parseInt(sc.nextLine()));
            System.out.print("Exige receita? (1 = Sim / 0 = Não): ");
            int opc = Integer.parseInt(sc.nextLine());
            r.setExigeReceita(opc == 1);
            dao.adicionar(r);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar remédio: " + e.getMessage());
        }
    }

    public void listar() {
        List<Remedio> lista = dao.listar();
        if (lista.isEmpty()) { System.out.println("Nenhum remédio cadastrado."); return; }
        System.out.println("\n--- REMÉDIOS ---");
        for (Remedio r : lista) {
            System.out.println("ID:" + r.getId() + " | " + r.getNome() + " | " + r.getPrincipioAtivo()
                    + " | R$" + r.getPreco() + " | Qtde:" + r.getQuantidade() + " | Val:" + r.getValidade()
                    + " | Receita:" + (r.isExigeReceita()? "Sim":"Não"));
        }
    }

    public void buscarPorNome(Scanner sc) {
        System.out.print("Nome (ou parte do nome): ");
        String nome = sc.nextLine();
        Remedio r = dao.buscarPorNome(nome);
        if (r == null) System.out.println("Não encontrado.");
        else {
            System.out.println("ID:" + r.getId() + " | " + r.getNome() + " | R$" + r.getPreco() + " | Qtde:" + r.getQuantidade());
        }
    }

    public void atualizar(Scanner sc) {
        try {
            System.out.print("ID do remédio a atualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            Remedio existente = dao.buscarPorId(id);
            if (existente == null) { System.out.println("Remédio não encontrado."); return; }
            Remedio r = new Remedio();
            r.setId(id);
            System.out.print("Novo nome: "); r.setNome(sc.nextLine());
            System.out.print("Fabricante: "); r.setFabricante(sc.nextLine());
            System.out.print("Princípio ativo: "); r.setPrincipioAtivo(sc.nextLine());
            System.out.print("Preço: "); r.setPreco(Double.parseDouble(sc.nextLine()));
            System.out.print("Validade (yyyy-MM-dd): "); r.setValidade(fmt.parse(sc.nextLine()));
            System.out.print("Quantidade: "); r.setQuantidade(Integer.parseInt(sc.nextLine()));
            System.out.print("Exige receita? (1 = Sim / 0 = Não): ");
            int opc = Integer.parseInt(sc.nextLine());
            r.setExigeReceita(opc == 1);
            dao.atualizar(r);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void remover(Scanner sc) {
        try {
            System.out.print("ID do remédio a remover: ");
            int id = Integer.parseInt(sc.nextLine());
            dao.remover(id);
        } catch (Exception e) { System.out.println("Erro ao remover: " + e.getMessage()); }
    }

    public void movimentarEstoque(Scanner sc) {
        try {
            System.out.println("1 - Repor estoque\n2 - Diminuir estoque (venda manual)");
            int op = Integer.parseInt(sc.nextLine());
            System.out.print("ID do remédio: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Quantidade: ");
            int qtd = Integer.parseInt(sc.nextLine());
            if (op == 1) dao.reporEstoque(id, qtd);
            else {
               
                Remedio r = dao.buscarPorId(id);
                if (r == null) { System.out.println("Remédio não encontrado."); return; }
                if (r.getQuantidade() < qtd) { System.out.println("Estoque insuficiente."); return; }
           
                r.setQuantidade(r.getQuantidade() - qtd);
                dao.atualizar(r);
                System.out.println("Estoque reduzido manualmente.");
            }
        } 
        catch (Exception e) { 
        	System.out.println("Erro movimentar estoque: " + e.getMessage()); 
        }
    }

    public void estoqueBaixo() {
        List<Remedio> lista = dao.listarEstoqueBaixo(20);
        if (lista.isEmpty()) { 
        	System.out.println("Nenhum remédio com estoque baixo."); 
        	return; 
        }
        System.out.println("\n--- ESTOQUE BAIXO (<20) ---");
        for (Remedio r : lista) System.out.println(r.getId() + " | " + r.getNome() + " | Qtde: " + r.getQuantidade());
    }

    public void verificarValidade() {
        List<Remedio> proximos = dao.listarVencendoEm(30);
        if (proximos.isEmpty()) System.out.println("Nenhum remédio vencendo nos próximos 30 dias.");
        else {
            System.out.println("\n--- REMÉDIOS VENCENDO EM 30 DIAS ---");
            for (Remedio r : proximos) System.out.println(r.getId() + " | " + r.getNome() + " | Val: " + r.getValidade());
        }
    }

    public void listarQueExigemReceita() {
        List<Remedio> lista = dao.listarQueExigemReceita();
        if (lista.isEmpty()) System.out.println("Nenhum remédio exige receita.");
        else {
            System.out.println("\n--- REMÉDIOS QUE EXIGEM RECEITA ---");
            for (Remedio r : lista) System.out.println(r.getId() + " | " + r.getNome() + " | Princípio: " + r.getPrincipioAtivo());
        }
    }
}

