package com.classes.DAO;

import com.classes.Conexao.Conexao;
import com.classes.DTO.ItemVenda;
import com.classes.DTO.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDAO {

    public int registrarVenda(Venda v) throws Exception {
        Connection conn = Conexao.conectar();
        if (conn == null) throw new Exception("Conexão nula");
        String sqlVenda = "INSERT INTO venda (cliente_id, data_venda, valor_total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO item_venda (venda_id, remedio_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        String sqlReduzEstoque = "UPDATE remedio SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement psVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                if (v.getClienteId() == null) psVenda.setNull(1, Types.INTEGER); else psVenda.setInt(1, v.getClienteId());
                psVenda.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
                psVenda.setDouble(3, v.getValorTotal());
                psVenda.executeUpdate();

                ResultSet rsKeys = psVenda.getGeneratedKeys();
                if (!rsKeys.next()) throw new Exception("Falha ao criar venda");
                int vendaId = rsKeys.getInt(1);

                try (PreparedStatement psItem = conn.prepareStatement(sqlItem);
                     PreparedStatement psReduz = conn.prepareStatement(sqlReduzEstoque)) {

                    for (ItemVenda it : v.getItens()) {
                        psItem.setInt(1, vendaId);
                        psItem.setInt(2, it.getRemedioId());
                        psItem.setInt(3, it.getQuantidade());
                        psItem.setDouble(4, it.getSubtotal());
                        psItem.executeUpdate();

                        psReduz.setInt(1, it.getQuantidade());
                        psReduz.setInt(2, it.getRemedioId());
                        psReduz.setInt(3, it.getQuantidade());
                        int updated = psReduz.executeUpdate();
                        if (updated == 0) throw new Exception("Estoque insuficiente para remédio ID " + it.getRemedioId());
                    }
                }

                conn.commit();
                return vendaId;
            }
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public List<Integer> listarIdsVendas() {
        List<Integer> lista = new ArrayList<>();
        String sql = "SELECT id FROM venda ORDER BY id DESC";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(rs.getInt("id"));
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<ItemVenda> listarItensPorVenda(int vendaId) {
        List<ItemVenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_venda WHERE venda_id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vendaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemVenda it = new ItemVenda();
                    it.setId(rs.getInt("id"));
                    it.setVendaId(rs.getInt("venda_id"));
                    it.setRemedioId(rs.getInt("remedio_id"));
                    it.setQuantidade(rs.getInt("quantidade"));
                    it.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(it);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    // relatório - remédios mais vendidos
    public void reportMaisVendidos() {
        String sql = "SELECT r.nome, SUM(iv.quantidade) as total_vendido " +
                     "FROM item_venda iv JOIN remedio r ON iv.remedio_id = r.id " +
                     "GROUP BY iv.remedio_id ORDER BY total_vendido DESC LIMIT 10";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n=== TOP REMÉDIOS MAIS VENDIDOS ===");
            while (rs.next()) {
                System.out.println(rs.getString("nome") + " - vendido(s): " + rs.getInt("total_vendido"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // total de vendas em período
    public double totalVendasPorPeriodo(Date inicio, Date fim) {
        String sql = "SELECT SUM(valor_total) AS total FROM venda WHERE DATE(data_venda) BETWEEN ? AND ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fim.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }

}
