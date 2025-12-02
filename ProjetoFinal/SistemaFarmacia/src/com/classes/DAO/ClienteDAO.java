package com.classes.DAO;

import com.classes.Conexao.Conexao;
import com.classes.DTO.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void adicionar(Cliente c) {
        String sql = "INSERT INTO cliente (nome, cpf, alergias) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getAlergias());
            ps.executeUpdate();
            System.out.println("Cliente cadastrado.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setAlergias(rs.getString("alergias"));
                    return c;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setAlergias(rs.getString("alergias"));
                lista.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}
