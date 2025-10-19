import java.util.Date;
import java.util.Random;

public class Denuncia {
    private String id;
    private boolean anonima;
    private String tipoFeminicidio;
    private String classificacao;
    private String provincia;
    private String distrito;
    private String bairro;
    private String detalhes;
    private String nomeDenunciante;
    private String contacto;
    private String email;
    private Date dataOcorrencia;
    private String status;
    private Date dataDenuncia;
    
    public Denuncia() {
        this.dataDenuncia = new Date();
        this.status = "Pendente";
        this.id = gerarID();
    }
    
    private String gerarID() {
        Random rand = new Random();
        return "DEN" + String.format("%06d", rand.nextInt(1000000));
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public boolean isAnonima() { return anonima; }
    public void setAnonima(boolean anonima) { this.anonima = anonima; }
    public String getTipoFeminicidio() { return tipoFeminicidio; }
    public void setTipoFeminicidio(String tipoFeminicidio) { this.tipoFeminicidio = tipoFeminicidio; }
    public String getClassificacao() { return classificacao; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
    public String getNomeDenunciante() { return nomeDenunciante; }
    public void setNomeDenunciante(String nomeDenunciante) { this.nomeDenunciante = nomeDenunciante; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(Date dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDataDenuncia() { return dataDenuncia; }
}