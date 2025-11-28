package com.classes.DTO;

import java.util.Date;

public class Receita {
    private int id;
    private String nomePaciente;
    private String cpfPaciente;
    private String nomeMedico;
    private String crmMedico;
    private int idRemedio;
    private Date dataEmissao;
    private int validadeDias;
    private String observacoes;

    public Receita() {}

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }
    public String getCpfPaciente() { return cpfPaciente; }
    public void setCpfPaciente(String cpfPaciente) { this.cpfPaciente = cpfPaciente; }
    public String getNomeMedico() { return nomeMedico; }
    public void setNomeMedico(String nomeMedico) { this.nomeMedico = nomeMedico; }
    public String getCrmMedico() { return crmMedico; }
    public void setCrmMedico(String crmMedico) { this.crmMedico = crmMedico; }
    public int getIdRemedio() { return idRemedio; }
    public void setIdRemedio(int idRemedio) { this.idRemedio = idRemedio; }
    public Date getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }
    public int getValidadeDias() { return validadeDias; }
    public void setValidadeDias(int validadeDias) { this.validadeDias = validadeDias; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
