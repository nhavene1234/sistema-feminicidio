import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TelaHospital extends JFrame {
    private Usuario usuario;
    private JTable denunciasTable;
    private DefaultTableModel tableModel;
    
    public TelaHospital(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Painel do Hospital - Sistema de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Cabeçalho
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(40, 167, 69));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        
        JLabel titleLabel = new JLabel("Painel do Hospital - " + usuario.getNome());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userInfoLabel = new JLabel("Centro de Saúde: " + usuario.getInstituicao() + " | Responsabilidade: " + usuario.getResponsabilidade());
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(userInfoLabel, BorderLayout.SOUTH);
        
        // Tabela de denúncias
        String[] colunas = {"ID", "Tipo", "Classificação", "Província", "Distrito", "Status", "Data"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        denunciasTable = new JTable(tableModel);
        
        atualizarTabelaDenuncias();
        
        JScrollPane scrollPane = new JScrollPane(denunciasTable);
        
        // Botões específicos do hospital
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton verDetalhesBtn = new JButton("Ver Detalhes");
        JButton registrarAtendimentoBtn = new JButton("Registrar Atendimento");
        JButton encaminharINASBtn = new JButton("Encaminhar para INAS");
        JButton refreshBtn = new JButton("Atualizar");
        JButton sairBtn = new JButton("Sair");
        
        buttonPanel.add(verDetalhesBtn);
        buttonPanel.add(registrarAtendimentoBtn);
        buttonPanel.add(encaminharINASBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(sairBtn);
        
        // Action Listeners
        verDetalhesBtn.addActionListener(e -> verDetalhesDenuncia());
        registrarAtendimentoBtn.addActionListener(e -> registrarAtendimento());
        encaminharINASBtn.addActionListener(e -> encaminharParaINAS());
        refreshBtn.addActionListener(e -> atualizarTabelaDenuncias());
        sairBtn.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void atualizarTabelaDenuncias() {
        tableModel.setRowCount(0);
        HashMap<String, Denuncia> denuncias = Main.getDenuncias();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Denuncia denuncia : denuncias.values()) {
            tableModel.addRow(new Object[]{
                denuncia.getId(),
                denuncia.getTipoFeminicidio(),
                denuncia.getClassificacao(),
                denuncia.getProvincia(),
                denuncia.getDistrito(),
                denuncia.getStatus(),
                sdf.format(denuncia.getDataDenuncia())
            });
        }
    }
    
    private void verDetalhesDenuncia() {
    int selectedRow = denunciasTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String idDenuncia = tableModel.getValueAt(selectedRow, 0).toString();
    Denuncia denuncia = Main.getDenuncias().get(idDenuncia);
    
    if (denuncia == null) {
        JOptionPane.showMessageDialog(this, "Denúncia não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    StringBuilder detalhes = new StringBuilder();
    detalhes.append("DETALHES DA DENÚNCIA - ").append(usuario.getInstituicao()).append("\n");
    detalhes.append("===================================\n\n");
    detalhes.append("ID: ").append(denuncia.getId()).append("\n");
    detalhes.append("Tipo: ").append(denuncia.getTipoFeminicidio()).append("\n");
    detalhes.append("Classificação: ").append(denuncia.getClassificacao()).append("\n");
    detalhes.append("Localização: ").append(denuncia.getProvincia()).append(", ")
             .append(denuncia.getDistrito()).append(", ").append(denuncia.getBairro()).append("\n");
    detalhes.append("Status: ").append(denuncia.getStatus()).append("\n");
    detalhes.append("Data da Denúncia: ").append(sdf.format(denuncia.getDataDenuncia())).append("\n");
    detalhes.append("Anônima: ").append(denuncia.isAnonima() ? "Sim" : "Não").append("\n");
    
    if (!denuncia.isAnonima()) {
        detalhes.append("Denunciante: ").append(denuncia.getNomeDenunciante()).append("\n");
        detalhes.append("Contacto: ").append(denuncia.getContacto()).append("\n");
        detalhes.append("Email: ").append(denuncia.getEmail()).append("\n");
    }
    
    detalhes.append("\nDetalhes da Denúncia:\n").append(denuncia.getDetalhes()).append("\n");
    
    // Adicionar histórico de acompanhamentos
    detalhes.append("\n\nHISTÓRICO DE ACOMPANHAMENTOS:\n");
    detalhes.append("==============================\n");
    
    // Buscar acompanhamentos desta denúncia
    HashMap<String, Acompanhamento> acompanhamentos = Main.getAcompanhamentos();
    boolean temAcompanhamentos = false;
    
    for (Acompanhamento ac : acompanhamentos.values()) {
        if (ac.getIdDenuncia().equals(idDenuncia)) {
            temAcompanhamentos = true;
            detalhes.append("\n--- ").append(ac.getTipoAgente()).append(" ---\n");
            detalhes.append("Agente: ").append(ac.getNomeAgente()).append("\n");
            detalhes.append("Data: ").append(sdf.format(ac.getDataAcompanhamento())).append("\n");
            detalhes.append("Ação: ").append(ac.getAcaoTomada()).append("\n");
            if (ac.getDescricao() != null && !ac.getDescricao().isEmpty()) {
                detalhes.append("Descrição: ").append(ac.getDescricao()).append("\n");
            }
            if (ac.getStatusAnterior() != null && ac.getStatusNovo() != null) {
                detalhes.append("Status: ").append(ac.getStatusAnterior()).append(" → ").append(ac.getStatusNovo()).append("\n");
            }
        }
    }
    
    if (!temAcompanhamentos) {
        detalhes.append("Nenhum acompanhamento registrado ainda.\n");
    }
    
    JTextArea textArea = new JTextArea(detalhes.toString());
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(600, 500));
    
    JOptionPane.showMessageDialog(this, scrollPane, "Detalhes Completos da Denúncia", JOptionPane.INFORMATION_MESSAGE);
}
    
   private void registrarAtendimento() {
    int selectedRow = denunciasTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String idDenuncia = tableModel.getValueAt(selectedRow, 0).toString();
    Denuncia denuncia = Main.getDenuncias().get(idDenuncia);
    
    JPanel panel = new JPanel(new BorderLayout());
    
    JComboBox<String> tipoAtendimentoCombo = new JComboBox<>(new String[]{
        "Consulta Médica", "Apoio Psicológico", "Tratamento", "Observação", "Outro"
    });
    
    JTextArea descricaoArea = new JTextArea(5, 30);
    descricaoArea.setLineWrap(true);
    descricaoArea.setWrapStyleWord(true);
    
    JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
    fieldsPanel.add(new JLabel("Tipo de Atendimento:"));
    fieldsPanel.add(tipoAtendimentoCombo);
    fieldsPanel.add(new JLabel("Descrição do Atendimento:"));
    fieldsPanel.add(new JScrollPane(descricaoArea));
    
    panel.add(fieldsPanel, BorderLayout.CENTER);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Atendimento", 
                                              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        String descricao = descricaoArea.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descreva o atendimento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Registrar atendimento no sistema de acompanhamentos
        Acompanhamento acompanhamento = new Acompanhamento(
            idDenuncia, usuario.getUsername(), usuario.getNome(), usuario.getTipo()
        );
        acompanhamento.setDescricao(descricao);
        acompanhamento.setAcaoTomada("Atendimento médico: " + tipoAtendimentoCombo.getSelectedItem());
        
        Main.addAcompanhamento(acompanhamento);
        
        JOptionPane.showMessageDialog(this, "Atendimento registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
    
  private void encaminharParaINAS() {
    int selectedRow = denunciasTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String idDenuncia = tableModel.getValueAt(selectedRow, 0).toString();
    Denuncia denuncia = Main.getDenuncias().get(idDenuncia);
    
    JTextArea justificativaArea = new JTextArea(5, 30);
    justificativaArea.setLineWrap(true);
    justificativaArea.setWrapStyleWord(true);
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Justificativa para encaminhamento ao INAS:"), BorderLayout.NORTH);
    panel.add(new JScrollPane(justificativaArea), BorderLayout.CENTER);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Encaminhar para INAS", 
                                              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        String justificativa = justificativaArea.getText().trim();
        if (justificativa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descreva a justificativa!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Registrar encaminhamento no sistema de acompanhamentos
        Acompanhamento acompanhamento = new Acompanhamento(
            idDenuncia, usuario.getUsername(), usuario.getNome(), usuario.getTipo()
        );
        acompanhamento.setDescricao(justificativa);
        acompanhamento.setAcaoTomada("Encaminhado para INAS");
        
        Main.addAcompanhamento(acompanhamento);
        
        JOptionPane.showMessageDialog(this, 
            "Caso encaminhado para o INAS com sucesso!\n" +
            "O INAS entrará em contacto com a vítima para assistência social.",
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}  
    
}