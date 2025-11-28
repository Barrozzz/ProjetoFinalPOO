package com.classes.Controller;

import com.classes.DAO.ClienteDAO;
import com.classes.DAO.RemedioDAO;
import com.classes.DAO.ReceitaDAO;
import com.classes.DAO.VendaDAO;
import com.classes.DTO.Cliente;
import com.classes.DTO.ItemVenda;
import com.classes.DTO.Remedio;
import com.classes.DTO.Venda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VendaController {
    private RemedioDAO remedioDAO = new RemedioDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ReceitaDAO receitaDAO = new ReceitaDAO();
    private VendaDAO vendaDAO = new VendaDAO();
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void registrarVenda(Scanner sc) {
        try {
            System.out.println("\n--- REGISTRAR VENDA ---");
            System.out.print("ID do cliente (0 para não cadastrar): ");
            int cid = Integer.parseInt(sc.nextLine());
            Integer clienteId = (cid == 0 ? null : cid);
            Cliente cliente = null;
            if (clienteId != null) cliente = clienteDAO.buscarPorId(clienteId);

            List<ItemVenda> itens = new ArrayList<>();
            double total = 0.0;

            while (true) {
                System.out.print("ID do remédio (0 para finalizar): ");
                int rid = Integer.parseInt(sc.nextLine());
                if (rid == 0) break;
                Remedio r = remedioDAO.buscarPorId(rid);
                if (r == null) { 
                	System.out.println("Remédio não encontrado."); 
                	continue; 
                }

                if (cliente != null && cliente.getAlergias() != null && !cliente.getAlergias().trim().isEmpty()) {
                    String alergias = cliente.getAlergias().toLowerCase();
                    String princip = (r.getPrincipioAtivo() == null) ? "" : r.getPrincipioAtivo().toLowerCase();
                    if (!princip.isEmpty() && alergias.contains(princip)) {
                        System.out.println("Cliente tem alergia ao princípio ativo: " + r.getPrincipioAtivo() + ". Venda não permitida deste item.");
                        continue;
                    }
                }

                if (r.isExigeReceita()) {
                    if (cliente == null) {
                        System.out.println("Remédio exige receita e não há cliente cadastrado. Não é possível vender esse item.");
                        continue;
                    } else {
                        boolean valida = receitaDAO.validarReceitaPorCpfEremedio(cliente.getCpf(), r.getId());
                        if (!valida) {
                            System.out.println("Receita inválida ou inexistente para este remédio para o cliente. Não é possível vender.");
                            continue;
                        }
                    }
                }

                System.out.print("Quantidade: ");
                int qtd = Integer.parseInt(sc.nextLine());
                if (qtd <= 0) { 
                	System.out.println("Quantidade inválida."); 
                	continue; 
                }
                if (qtd > r.getQuantidade()) { 
                	System.out.println("Estoque insuficiente."); 
                	continue; 
                }

                ItemVenda it = new ItemVenda();
                it.setRemedioId(r.getId());
                it.setQuantidade(qtd);
                it.setSubtotal(qtd * r.getPreco());
                itens.add(it);
                total += it.getSubtotal();
                System.out.println("Adicionado: " + r.getNome() + " x" + qtd + " -> R$" + it.getSubtotal());
            }

            if (itens.isEmpty()) { 
            	System.out.println("Nenhum item adicionado. Venda cancelada."); 
            	return; 
            }

            Venda v = new Venda();
            v.setClienteId(clienteId);
            v.setDataVenda(new Date());
            v.setValorTotal(total);
            v.setItens(itens);

            int idVenda = vendaDAO.registrarVenda(v);
            System.out.println("Venda registrada com ID: " + idVenda);
            gerarRecibo(idVenda, v);
        } 
        catch (Exception e) {
            System.out.println("Erro registrar venda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void gerarRecibo(int idVenda, Venda v) {
        System.out.println("\n--- RECIBO (Venda ID: " + idVenda + ") ---");
        if (v.getClienteId() != null) System.out.println("Cliente ID: " + v.getClienteId());
        System.out.println("Data: " + fmt.format(v.getDataVenda()));
        System.out.println("Itens:");
        for (ItemVenda it : v.getItens()) {
            Remedio r = remedioDAO.buscarPorId(it.getRemedioId());
            String nome = (r != null) ? r.getNome() : ("Remédio ID " + it.getRemedioId());
            System.out.println("- " + nome + " x" + it.getQuantidade() + " -> R$" + it.getSubtotal());
        }
        System.out.printf("Valor total: R$ %.2f%n", v.getValorTotal());
        System.out.println("--- FIM RECIBO ---\n");
    }

    public void listar() {
        List<Integer> ids = vendaDAO.listarIdsVendas();
        if (ids.isEmpty()) { 
        	System.out.println("Nenhuma venda registrada."); 
        	return; 
        }
        System.out.println("\n--- VENDAS (IDs) ---");
        for (Integer id : ids) System.out.println("Venda ID: " + id);
    }

    public void detalhar(Scanner sc) {
        try {
            System.out.print("ID da venda: ");
            int id = Integer.parseInt(sc.nextLine());
            List<ItemVenda> itens = vendaDAO.listarItensPorVenda(id);
            if (itens.isEmpty()) { System.out.println("Venda não encontrada ou sem itens."); return; }
            System.out.println("\n--- ITENS DA VENDA " + id + " ---");
            for (ItemVenda it : itens) {
                Remedio r = remedioDAO.buscarPorId(it.getRemedioId());
                System.out.println("Remédio: " + (r != null ? r.getNome() : it.getRemedioId()) + " | Qtde: " + it.getQuantidade() + " | Subtotal: R$" + it.getSubtotal());
            }
        } 
        catch (Exception e) { 
        	System.out.println("Erro detalhar venda: " + e.getMessage()); 
        }
    }

    public void relatoriosMaisVendidos() {
        vendaDAO.reportMaisVendidos();
    }

    public void totalVendasNoPeriodo(Scanner sc) {
        try {
            System.out.print("Data início (yyyy-MM-dd): ");
            String sInicio = sc.nextLine();
            System.out.print("Data fim (yyyy-MM-dd): ");
            String sFim = sc.nextLine();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date inicio = fmt.parse(sInicio);
            Date fim = fmt.parse(sFim);
            double total = vendaDAO.totalVendasPorPeriodo(inicio, fim);
            System.out.printf("Total de vendas entre %s e %s = R$ %.2f%n", sInicio, sFim, total);
        } 
        catch (Exception e) { 
        	System.out.println("Erro relatório: " + e.getMessage()); 
        }
    }
}

