package com.classes.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String NOME_DO_BANCO = "farmacia";
    private static final String USUARIO = "root"; 
    private static final String SENHA = "";     
    private static final String URL = "jdbc:mysql://localhost:3306/" + NOME_DO_BANCO + "?useTimezone=true&serverTimezone=UTC";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
            return conexao;
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
