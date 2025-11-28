package com.classes.DAO;

import com.classes.Conexao.Conexao;
import com.classes.DTO.Remedio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemedioDAO {

    public void adicionar(Remedio r) {
        String sql = "INSERT INTO remedio (nome, fabricante, principio_ativo, preco, validade, quantidade, exige_receita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getFabricante());
            ps.setString(3, r.getPrincipioAtivo());
            ps.setDouble(4, r.getPreco());
            ps.setDate(5, new java.sql.Date(r.getValidade().getTime()));
            ps.setInt(6, r.getQuantidade());
            ps.setBoolean(7, r.isExigeReceita());
            ps.executeUpdate();
            System.out.println("Remédio cadastrado.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Remedio> listar() {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public Remedio buscarPorNome(String nome) {
        String sql = "SELECT * FROM remedio WHERE nome LIKE ? LIMIT 1";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public Remedio buscarPorId(int id) {
        String sql = "SELECT * FROM remedio WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void atualizar(Remedio r) {
        String sql = "UPDATE remedio SET nome=?, fabricante=?, principio_ativo=?, preco=?, validade=?, quantidade=?, exige_receita=? WHERE id=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getFabricante());
            ps.setString(3, r.getPrincipioAtivo());
            ps.setDouble(4, r.getPreco());
            ps.setDate(5, new java.sql.Date(r.getValidade().getTime()));
            ps.setInt(6, r.getQuantidade());
            ps.setBoolean(7, r.isExigeReceita());
            ps.setInt(8, r.getId());
            ps.executeUpdate();
            System.out.println("Remédio atualizado.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM remedio WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Não é possível remover o remédio: ele já está vinculado a vendas.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
        return false;
    }


    public void reporEstoque(int id, int qtd) {
        String sql = "UPDATE remedio SET quantidade = quantidade + ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qtd);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Estoque reposto.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Remedio> listarEstoqueBaixo(int limite) {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio WHERE quantidade < ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Remedio> listarVencendoEm(int dias) {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio WHERE validade <= DATE_ADD(CURDATE(), INTERVAL ? DAY)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dias);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Remedio> listarQueExigemReceita() {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio WHERE exige_receita = 1";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    private Remedio map(ResultSet rs) throws SQLException {
        Remedio r = new Remedio();
        r.setId(rs.getInt("id"));
        r.setNome(rs.getString("nome"));
        r.setFabricante(rs.getString("fabricante"));
        r.setPrincipioAtivo(rs.getString("principio_ativo"));
        r.setPreco(rs.getDouble("preco"));
        r.setValidade(rs.getDate("validade"));
        r.setQuantidade(rs.getInt("quantidade"));
        r.setExigeReceita(rs.getBoolean("exige_receita"));
        return r;
    }
}
