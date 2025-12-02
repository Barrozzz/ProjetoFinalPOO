package com.classes.Controller;

import com.classes.DAO.ReceitaDAO;
import com.classes.DTO.Receita;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ReceitaController {
    private ReceitaDAO dao = new ReceitaDAO();
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public void cadastrar(Scanner leia) {
        try {
            Receita r = new Receita();
            System.out.println("\n--- CADASTRO RECEITA ---");
            System.out.print("Nome do paciente: "); r.setNomePaciente(leia.nextLine());
            System.out.print("CPF do paciente: "); r.setCpfPaciente(leia.nextLine());
            System.out.print("Nome do médico: "); r.setNomeMedico(leia.nextLine());
            System.out.print("CRM do médico: "); r.setCrmMedico(leia.nextLine());
            System.out.print("ID do remédio autorizado: "); r.setIdRemedio(Integer.parseInt(leia.nextLine()));
            System.out.print("Data de emissão (yyyy-MM-dd): "); r.setDataEmissao(fmt.parse(leia.nextLine()));
            System.out.print("Validade em dias: "); r.setValidadeDias(Integer.parseInt(leia.nextLine()));
            System.out.print("Observações: "); r.setObservacoes(leia.nextLine());
            if (dao.adicionar(r)) System.out.println("Receita salva!");
            else System.out.println("Erro ao salvar receita!");
        } 
        catch (Exception e){ 
        	System.out.println("Erro cadastrar receita: " + e.getMessage()); 
        }
    }

    public void listar() {
        List<Receita> lista = dao.listar();
        if (lista.isEmpty()) { System.out.println("Nenhuma receita cadastrada."); 
        return; 
        }
        System.out.println("\n--- RECEITAS ---");
        for (Receita r : lista) {
            System.out.println("ID:" + r.getId() + " | Paciente:" + r.getNomePaciente() + " | CPF:" + r.getCpfPaciente() +
                    " | Remédio ID:" + r.getIdRemedio() + " | Emissão:" + r.getDataEmissao());
        }
    }

    public void buscarPorCpf(Scanner leia) {
        System.out.print("CPF do paciente: ");
        String cpf = leia.nextLine();
        Receita r = dao.buscarPorCPF(cpf);
        if (r == null) System.out.println("Nenhuma receita encontrada para o CPF informado.");
        else {
            System.out.println("ID:" + r.getId() + " | Paciente:" + r.getNomePaciente() + " | Remédio ID:" + r.getIdRemedio()
                    + " | Emissão:" + r.getDataEmissao() + " | Validade dias:" + r.getValidadeDias());
        }
    }

    public void remover(Scanner leia) {
        try {
            System.out.print("ID da receita a remover: ");
            int id = Integer.parseInt(leia.nextLine());
            if (dao.remover(id)) System.out.println("Receita removida!");
            else System.out.println("Erro ao remover receita!");
        } catch (Exception e) { 
        	System.out.println("Erro: " + e.getMessage()); 
        }
    }
}
