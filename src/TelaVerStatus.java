import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TelaVerStatus extends JFrame {
    private JTextField codigoField;
    private JLabel statusLabel;
    private JLabel detalhesLabel;
    
    public TelaVerStatus() {
        setTitle("Ver Status da Denúncia - Sistema de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Verificar Status da Denúncia");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Código
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código da Denúncia:"), gbc);
        
        codigoField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(codigoField, gbc);
        
        // Botões
        JButton verStatusBtn = new JButton("Ver Estado");
        verStatusBtn.setBackground(new Color(0, 123, 255));
        verStatusBtn.setForeground(Color.WHITE);
        
        JButton cancelarBtn = new JButton("Cancelar Denúncia");
        cancelarBtn.setBackground(new Color(220, 53, 69));
        cancelarBtn.setForeground(Color.WHITE);
        
        JButton voltarBtn = new JButton("Voltar");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(verStatusBtn);
        buttonPanel.add(cancelarBtn);
        buttonPanel.add(voltarBtn);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        // Painel de resultados
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Informações da Denúncia"));
        resultPanel.setPreferredSize(new Dimension(450, 200));
        
        statusLabel = new JLabel("Status: -");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        detalhesLabel = new JLabel("<html>Detalhes: -</html>");
        detalhesLabel.setVerticalAlignment(SwingConstants.TOP);
        
        resultPanel.add(statusLabel, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(detalhesLabel), BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(resultPanel, gbc);
        
        // Action Listeners
        verStatusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarStatus();
            }
        });
        
        cancelarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarDenuncia();
            }
        });
        
        voltarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void verificarStatus() {
    String codigo = codigoField.getText().trim();
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Digite o código da denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    HashMap<String, Denuncia> denuncias = Main.getDenuncias();
    Denuncia denuncia = denuncias.get(codigo);
    
    if (denuncia == null) {
        JOptionPane.showMessageDialog(this, "Código não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("Status: -");
        detalhesLabel.setText("<html>Detalhes: -</html>");
        return;
    }
    
    // Atualizar labels com informações da denúncia
    statusLabel.setText("Status: " + denuncia.getStatus());
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    StringBuilder detalhes = new StringBuilder("<html>");
    
    detalhes.append("<b>Informações da Denúncia:</b><br>");
    detalhes.append("<b>ID:</b> ").append(denuncia.getId()).append("<br>");
    detalhes.append("<b>Tipo:</b> ").append(denuncia.getTipoFeminicidio()).append("<br>");
    detalhes.append("<b>Classificação:</b> ").append(denuncia.getClassificacao()).append("<br>");
    detalhes.append("<b>Localização:</b> ").append(denuncia.getProvincia()).append(", ")
            .append(denuncia.getDistrito()).append(", ").append(denuncia.getBairro()).append("<br>");
    detalhes.append("<b>Data da Denúncia:</b> ").append(sdf.format(denuncia.getDataDenuncia())).append("<br>");
    detalhes.append("<b>Status Atual:</b> ").append(denuncia.getStatus()).append("<br><br>");
    
    // Adicionar histórico de acompanhamentos
    detalhes.append("<b>Histórico de Acompanhamento:</b><br>");
    
    HashMap<String, Acompanhamento> acompanhamentos = Main.getAcompanhamentos();
    boolean temAcompanhamentos = false;
    
    for (Acompanhamento ac : acompanhamentos.values()) {
        if (ac.getIdDenuncia().equals(codigo)) {
            temAcompanhamentos = true;
            detalhes.append("<br><b>").append(ac.getTipoAgente()).append(" - ").append(ac.getNomeAgente()).append("</b><br>");
            detalhes.append("Data: ").append(sdf.format(ac.getDataAcompanhamento())).append("<br>");
            detalhes.append("Ação: ").append(ac.getAcaoTomada()).append("<br>");
            if (ac.getDescricao() != null && !ac.getDescricao().isEmpty()) {
                detalhes.append("Descrição: ").append(ac.getDescricao()).append("<br>");
            }
            if (ac.getStatusAnterior() != null && ac.getStatusNovo() != null && 
                !ac.getStatusAnterior().equals(ac.getStatusNovo())) {
                detalhes.append("Status: ").append(ac.getStatusAnterior()).append(" → ").append(ac.getStatusNovo()).append("<br>");
            }
        }
    }
    
    if (!temAcompanhamentos) {
        detalhes.append("<br>Nenhum acompanhamento registrado ainda.<br>");
    }
    
    detalhes.append("</html>");
    
    detalhesLabel.setText(detalhes.toString());
}
    
    private void cancelarDenuncia() {
        String codigo = codigoField.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código da denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        HashMap<String, Denuncia> denuncias = Main.getDenuncias();
        Denuncia denuncia = denuncias.get(codigo);
        
        if (denuncia == null) {
            JOptionPane.showMessageDialog(this, "Código não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (denuncia.getStatus().equals("Cancelado")) {
            JOptionPane.showMessageDialog(this, "Esta denúncia já foi cancelada!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja cancelar esta denúncia?", 
            "Confirmar Cancelamento", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            denuncia.setStatus("Cancelado");
            JOptionPane.showMessageDialog(this, "Denúncia cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            verificarStatus(); // Atualizar a exibição
        }
    }
}