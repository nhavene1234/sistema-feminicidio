import java.util.Date;

public class Acompanhamento {
    private String idDenuncia;
    private String idAgente;
    private String nomeAgente;
    private String tipoAgente;
    private Date dataAcompanhamento;
    private String descricao;
    private String acaoTomada;
    private String statusAnterior;
    private String statusNovo;
    
    public Acompanhamento(String idDenuncia, String idAgente, String nomeAgente, String tipoAgente) {
        this.idDenuncia = idDenuncia;
        this.idAgente = idAgente;
        this.nomeAgente = nomeAgente;
        this.tipoAgente = tipoAgente;
        this.dataAcompanhamento = new Date();
    }
    
    // Getters e Setters
    public String getIdDenuncia() { return idDenuncia; }
    public String getIdAgente() { return idAgente; }
    public String getNomeAgente() { return nomeAgente; }
    public String getTipoAgente() { return tipoAgente; }
    public Date getDataAcompanhamento() { return dataAcompanhamento; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getAcaoTomada() { return acaoTomada; }
    public void setAcaoTomada(String acaoTomada) { this.acaoTomada = acaoTomada; }
    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    public String getStatusNovo() { return statusNovo; }
    public void setStatusNovo(String statusNovo) { this.statusNovo = statusNovo; }
}