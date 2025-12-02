package com.classes.main;

import java.util.Scanner;

import com.classes.Controller.RemedioController;
import com.classes.Controller.ClienteController;
import com.classes.Controller.ReceitaController;
import com.classes.Controller.VendaController;
import com.classes.Controller.RelatorioController;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);

        RemedioController remedioCtrl = new RemedioController();
        ClienteController clienteCtrl = new ClienteController();
        ReceitaController receitaCtrl = new ReceitaController();
        VendaController vendaCtrl = new VendaController();
        RelatorioController relCtrl = new RelatorioController();

        int op = -1;
        while (op != 0) {
            System.out.println("\n===== SISTEMA DE FARMÁCIA =====");
            System.out.println("1 - Cadastrar Remédio");
            System.out.println("2 - Listar Remédios");
            System.out.println("3 - Buscar Remédio por Nome");
            System.out.println("4 - Atualizar Remédio");
            System.out.println("5 - Remover Remédio");
            System.out.println("6 - Movimentar Estoque (repor/diminuir)");
            System.out.println("7 - Estoque Baixo (<20)");
            System.out.println("8 - Verificar Validade (30 dias)");
            System.out.println("9 - Listar Remédios que Exigem Receita");

            System.out.println("10 - Cadastrar Cliente");
            System.out.println("11 - Listar Clientes");

            System.out.println("12 - Cadastrar Receita");
            System.out.println("13 - Listar Receitas");
            System.out.println("14 - Buscar Receita por CPF");
            System.out.println("15 - Remover Receita");

            System.out.println("16 - Registrar Venda");
            System.out.println("17 - Listar Vendas (IDs)");
            System.out.println("18 - Detalhar Venda (itens)");
            System.out.println("19 - Relatório: Remédios mais vendidos");
            System.out.println("20 - Relatório: Total vendas por período");

            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            try {
                op = Integer.parseInt(leia.nextLine());
            } catch (Exception e) { 
            	op = -1;
            }

            switch (op) {
                case 1 -> remedioCtrl.cadastrar(leia);
                case 2 -> remedioCtrl.listar();
                case 3 -> remedioCtrl.buscarPorNome(leia);
                case 4 -> remedioCtrl.atualizar(leia);
                case 5 -> remedioCtrl.remover(leia);
                case 6 -> remedioCtrl.movimentarEstoque(leia);
                case 7 -> remedioCtrl.estoqueBaixo();
                case 8 -> remedioCtrl.verificarValidade();
                case 9 -> remedioCtrl.listarQueExigemReceita();

                case 10 -> clienteCtrl.cadastrar(leia);
                case 11 -> clienteCtrl.listar();

                case 12 -> receitaCtrl.cadastrar(leia);
                case 13 -> receitaCtrl.listar();
                case 14 -> receitaCtrl.buscarPorCpf(leia);
                case 15 -> receitaCtrl.remover(leia);

                case 16 -> vendaCtrl.registrarVenda(leia);
                case 17 -> vendaCtrl.listar();
                case 18 -> vendaCtrl.detalhar(leia);
                case 19 -> vendaCtrl.relatoriosMaisVendidos();
                case 20 -> vendaCtrl.totalVendasNoPeriodo(leia);

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }

        leia.close();
    }
}
