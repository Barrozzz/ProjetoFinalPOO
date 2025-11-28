package com.classes.Controller;

import com.classes.DAO.ClienteDAO;
import com.classes.DTO.Cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private ClienteDAO dao = new ClienteDAO();

    public void cadastrar(Scanner sc) {
        try {
            Cliente c = new Cliente();
            System.out.println("\n--- CADASTRO CLIENTE ---");
            System.out.print("Nome: "); c.setNome(sc.nextLine());
            System.out.print("CPF: "); c.setCpf(sc.nextLine());
            System.out.print("Alergias (separar por vírgula, vazio se não): "); c.setAlergias(sc.nextLine());
            dao.adicionar(c);
        } catch (Exception e) { 
        	System.out.println("Erro cadastrar cliente: " + e.getMessage()); 
        }
    }

    public void listar() {
        List<Cliente> lista = dao.listar();
        if (lista.isEmpty()) { 
        	System.out.println("Nenhum cliente cadastrado."); 
        	return; 
        }
        System.out.println("\n--- CLIENTES ---");
        for (Cliente c : lista) System.out.println(c.getId() + " | " + c.getNome() + " | CPF: " + c.getCpf() + " | Alergias: " + c.getAlergias());
    }
}
