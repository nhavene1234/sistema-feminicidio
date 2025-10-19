public class Usuario {
    private String username;
    private String password;
    private String tipo; // "POLICIA", "HOSPITAL", "INAS", "ADMIN"
    private String nome;
    private String instituicao;
    private String responsabilidade;
    private boolean ativo;
    
    public Usuario(String username, String password, String tipo, String nome, String instituicao, String responsabilidade) {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.nome = nome;
        this.instituicao = instituicao;
        this.responsabilidade = responsabilidade;
        this.ativo = true;
    }
    
    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getInstituicao() { return instituicao; }
    public void setInstituicao(String instituicao) { this.instituicao = instituicao; }
    public String getResponsabilidade() { return responsabilidade; }
    public void setResponsabilidade(String responsabilidade) { this.responsabilidade = responsabilidade; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}