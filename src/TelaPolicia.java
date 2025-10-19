import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TelaPolicia extends JFrame {
    private Usuario usuario;
    private JTable denunciasTable;
    private DefaultTableModel tableModel;
    
    public TelaPolicia(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Painel da Polícia - Sistema de Feminicídio");
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
        headerPanel.setBackground(new Color(0, 123, 255));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        
        JLabel titleLabel = new JLabel("Painel da Polícia - " + usuario.getNome());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userInfoLabel = new JLabel("Posto: " + usuario.getInstituicao() + " | Responsabilidade: " + usuario.getResponsabilidade());
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
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton verDetalhesBtn = new JButton("Ver Detalhes");
        JButton acompanharBtn = new JButton("Acompanhar Caso");
        JButton resolverBtn = new JButton("Resolver Caso");
        JButton refreshBtn = new JButton("Atualizar");
        JButton sairBtn = new JButton("Sair");
        
        buttonPanel.add(verDetalhesBtn);
        buttonPanel.add(acompanharBtn);
        buttonPanel.add(resolverBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(sairBtn);
        
        // Action Listeners
        verDetalhesBtn.addActionListener(e -> verDetalhesDenuncia());
        acompanharBtn.addActionListener(e -> acompanharCaso());
        resolverBtn.addActionListener(e -> resolverCaso());
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
        detalhes.append("DETALHES DA DENÚNCIA\n");
        detalhes.append("===================\n\n");
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
        }
        
        detalhes.append("\nDetalhes:\n").append(denuncia.getDetalhes());
        
        JTextArea textArea = new JTextArea(detalhes.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Detalhes da Denúncia", JOptionPane.INFORMATION_MESSAGE);
    }
    private void acompanharCaso() {
    int selectedRow = denunciasTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String idDenuncia = tableModel.getValueAt(selectedRow, 0).toString();
    Denuncia denuncia = Main.getDenuncias().get(idDenuncia);
    
    JTextArea acompanhamentoArea = new JTextArea(10, 40);
    acompanhamentoArea.setLineWrap(true);
    acompanhamentoArea.setWrapStyleWord(true);
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Registro de Acompanhamento:"), BorderLayout.NORTH);
    panel.add(new JScrollPane(acompanhamentoArea), BorderLayout.CENTER);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Acompanhamento", 
                                              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        String descricao = acompanhamentoArea.getText().trim();
        if (!descricao.isEmpty()) {
            // Criar registro de acompanhamento
            Acompanhamento acompanhamento = new Acompanhamento(
                idDenuncia, usuario.getUsername(), usuario.getNome(), usuario.getTipo()
            );
            acompanhamento.setDescricao(descricao);
            acompanhamento.setStatusAnterior(denuncia.getStatus());
            acompanhamento.setStatusNovo(denuncia.getStatus()); // Status não muda
            acompanhamento.setAcaoTomada("Acompanhamento registrado por " + usuario.getInstituicao());
            
            Main.addAcompanhamento(acompanhamento);
            
            JOptionPane.showMessageDialog(this, "Acompanhamento registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

private void resolverCaso() {
    int selectedRow = denunciasTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String idDenuncia = tableModel.getValueAt(selectedRow, 0).toString();
    Denuncia denuncia = Main.getDenuncias().get(idDenuncia);
    
    if (denuncia.getStatus().equals("Resolvido")) {
        JOptionPane.showMessageDialog(this, "Este caso já foi resolvido!", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    JTextArea resolucaoArea = new JTextArea(10, 40);
    resolucaoArea.setLineWrap(true);
    resolucaoArea.setWrapStyleWord(true);
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Descrição da Resolução:"), BorderLayout.NORTH);
    panel.add(new JScrollPane(resolucaoArea), BorderLayout.CENTER);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Resolver Caso", 
                                              JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        String resolucao = resolucaoArea.getText().trim();
        if (resolucao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descreva a resolução do caso!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Atualizar status e criar acompanhamento
        String statusAnterior = denuncia.getStatus();
        denuncia.setStatus("Resolvido");
        
        Acompanhamento acompanhamento = new Acompanhamento(
            idDenuncia, usuario.getUsername(), usuario.getNome(), usuario.getTipo()
        );
        acompanhamento.setDescricao(resolucao);
        acompanhamento.setStatusAnterior(statusAnterior);
        acompanhamento.setStatusNovo("Resolvido");
        acompanhamento.setAcaoTomada("Caso resolvido por " + usuario.getInstituicao());
        
        Main.addAcompanhamento(acompanhamento);
        
        JOptionPane.showMessageDialog(this, "Caso marcado como resolvido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        atualizarTabelaDenuncias();
    }
}

    
}