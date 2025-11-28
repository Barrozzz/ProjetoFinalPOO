package com.classes.DAO;

import com.classes.Conexao.Conexao;
import com.classes.DTO.Receita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {

    public boolean adicionar(Receita r) {
        String sql = "INSERT INTO receita (nomePaciente, cpfPaciente, nomeMedico, crmMedico, idRemedio, dataEmissao, validadeDias, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNomePaciente());
            ps.setString(2, r.getCpfPaciente());
            ps.setString(3, r.getNomeMedico());
            ps.setString(4, r.getCrmMedico());
            ps.setInt(5, r.getIdRemedio());
            ps.setDate(6, new java.sql.Date(r.getDataEmissao().getTime()));
            ps.setInt(7, r.getValidadeDias());
            ps.setString(8, r.getObservacoes());
            ps.executeUpdate();
            System.out.println("Receita cadastrada.");
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Receita> listar() {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM receita";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Receita r = new Receita();
                r.setId(rs.getInt("id"));
                r.setNomePaciente(rs.getString("nomePaciente"));
                r.setCpfPaciente(rs.getString("cpfPaciente"));
                r.setNomeMedico(rs.getString("nomeMedico"));
                r.setCrmMedico(rs.getString("crmMedico"));
                r.setIdRemedio(rs.getInt("idRemedio"));
                r.setDataEmissao(rs.getDate("dataEmissao"));
                r.setValidadeDias(rs.getInt("validadeDias"));
                r.setObservacoes(rs.getString("observacoes"));
                lista.add(r);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public Receita buscarPorCPF(String cpf) {
        String sql = "SELECT * FROM receita WHERE cpfPaciente = ? LIMIT 1";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Receita r = new Receita();
                    r.setId(rs.getInt("id"));
                    r.setNomePaciente(rs.getString("nomePaciente"));
                    r.setCpfPaciente(rs.getString("cpfPaciente"));
                    r.setNomeMedico(rs.getString("nomeMedico"));
                    r.setCrmMedico(rs.getString("crmMedico"));
                    r.setIdRemedio(rs.getInt("idRemedio"));
                    r.setDataEmissao(rs.getDate("dataEmissao"));
                    r.setValidadeDias(rs.getInt("validadeDias"));
                    r.setObservacoes(rs.getString("observacoes"));
                    return r;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Receita removida.");
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // valida se existe receita válida para cliente CPF e remédio id
    public boolean validarReceitaPorCpfEremedio(String cpf, int remedioId) {
        String sql = "SELECT * FROM receita WHERE cpfPaciente = ? AND idRemedio = ? AND DATE_ADD(dataEmissao, INTERVAL validadeDias DAY) >= CURDATE() LIMIT 1";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.setInt(2, remedioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
