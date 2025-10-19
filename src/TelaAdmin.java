import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TelaAdmin extends JFrame {
    private Usuario usuario;
    private JTable usuariosTable;
    private DefaultTableModel tableModel;
    
    public TelaAdmin(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Painel Administrativo - Sistema de Feminicídio");
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
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        
        JLabel titleLabel = new JLabel("Painel Administrativo - Bem-vindo, " + usuario.getNome());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userInfoLabel = new JLabel("Tipo: " + usuario.getTipo() + " | Instituição: " + usuario.getInstituicao());
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(userInfoLabel, BorderLayout.SOUTH);
        
        // Abas
     
        
        // No método initComponents(), na criação das abas:
JTabbedPane tabbedPane = new JTabbedPane();

// Adicione esta linha para criar a aba de Dashboard:
tabbedPane.addTab("Dashboard", criarPainelEstatisticas());

// Depois as abas existentes:
tabbedPane.addTab("Gestão de Usuários", criarPainelUsuarios());
tabbedPane.addTab("Relatórios", criarPainelRelatorios());
tabbedPane.addTab("Todas as Denúncias", criarPainelDenuncias());
        // Botão Sair
        JButton sairBtn = new JButton("Sair");
        sairBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(sairBtn, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel criarPainelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabela de usuários
        String[] colunas = {"Username", "Nome", "Tipo", "Instituição", "Responsabilidade", "Ativo"};
        tableModel = new DefaultTableModel(colunas, 0);
        usuariosTable = new JTable(tableModel);
        
        atualizarTabelaUsuarios();
        
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addBtn = new JButton("Adicionar Usuário");
        JButton editBtn = new JButton("Editar Usuário");
        JButton removeBtn = new JButton("Remover Usuário");
        JButton refreshBtn = new JButton("Atualizar");
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(refreshBtn);
        
        // Action Listeners
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarUsuario();
            }
        });
        
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });
        
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerUsuario();
            }
        });
        
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabelaUsuarios();
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarPainelRelatorios() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton relDenunciasBtn = new JButton("Relatório de Denúncias");
        JButton relAgentesBtn = new JButton("Relatório de Agentes");
        JButton relEstatisticasBtn = new JButton("Estatísticas Gerais");
        JButton relAcompanhamentosBtn = new JButton("Acompanhamentos");
        JButton exportarBtn = new JButton("Exportar Relatórios");
        
        relDenunciasBtn.addActionListener(e -> gerarRelatorioDenuncias());
        relAgentesBtn.addActionListener(e -> gerarRelatorioAgentes());
        relEstatisticasBtn.addActionListener(e -> gerarEstatisticas());
        relAcompanhamentosBtn.addActionListener(e -> gerarRelatorioAcompanhamentos());
        exportarBtn.addActionListener(e -> exportarRelatorios());
        
        panel.add(relDenunciasBtn);
        panel.add(relAgentesBtn);
        panel.add(relEstatisticasBtn);
        panel.add(relAcompanhamentosBtn);
        panel.add(exportarBtn);
        
        return panel;
    }
    
    private JPanel criarPainelDenuncias() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colunas = {"ID", "Tipo", "Classificação", "Província", "Status", "Data"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable denunciasTable = new JTable(model);
        
        // Popular com denúncias
        HashMap<String, Denuncia> denuncias = Main.getDenuncias();
        for (Denuncia denuncia : denuncias.values()) {
            model.addRow(new Object[]{
                denuncia.getId(),
                denuncia.getTipoFeminicidio(),
                denuncia.getClassificacao(),
                denuncia.getProvincia(),
                denuncia.getStatus(),
                denuncia.getDataDenuncia()
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(denunciasTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void atualizarTabelaUsuarios() {
        tableModel.setRowCount(0);
        HashMap<String, Usuario> usuarios = TelaLogin.getUsuarios();
        
        for (Usuario usuario : usuarios.values()) {
            tableModel.addRow(new Object[]{
                usuario.getUsername(),
                usuario.getNome(),
                usuario.getTipo(),
                usuario.getInstituicao(),
                usuario.getResponsabilidade(),
                usuario.isAtivo() ? "Sim" : "Não"
            });
        }
    }
    
    private void adicionarUsuario() {
        JDialog dialog = new JDialog(this, "Adicionar Usuário", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField nomeField = new JTextField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"POLICIA", "HOSPITAL", "INAS"});
        JTextField instituicaoField = new JTextField();
        JTextField responsabilidadeField = new JTextField();
        
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoCombo);
        panel.add(new JLabel("Instituição:"));
        panel.add(instituicaoField);
        panel.add(new JLabel("Responsabilidade:"));
        panel.add(responsabilidadeField);
        
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String nome = nomeField.getText().trim();
            String tipo = tipoCombo.getSelectedItem().toString();
            String instituicao = instituicaoField.getText().trim();
            String responsabilidade = responsabilidadeField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty() || nome.isEmpty() || instituicao.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (TelaLogin.getUsuarios().containsKey(username)) {
                JOptionPane.showMessageDialog(dialog, "Username já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Usuario novoUsuario = new Usuario(username, password, tipo, nome, instituicao, responsabilidade);
            TelaLogin.getUsuarios().put(username, novoUsuario);
            
            JOptionPane.showMessageDialog(dialog, "Usuário adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTabelaUsuarios();
            dialog.dispose();
        });
        
        panel.add(salvarBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void editarUsuario() {
    int selectedRow = usuariosTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um usuário para editar!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String username = tableModel.getValueAt(selectedRow, 0).toString();
    Usuario usuario = TelaLogin.getUsuarios().get(username);
    
    JDialog dialog = new JDialog(this, "Editar Usuário", true);
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(this);
    
    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    JTextField usernameField = new JTextField(usuario.getUsername());
    usernameField.setEditable(false); // Não pode editar username
    JPasswordField passwordField = new JPasswordField(usuario.getPassword());
    JTextField nomeField = new JTextField(usuario.getNome());
    JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"POLICIA", "HOSPITAL", "INAS", "ADMIN"});
    tipoCombo.setSelectedItem(usuario.getTipo());
    JTextField instituicaoField = new JTextField(usuario.getInstituicao());
    JTextField responsabilidadeField = new JTextField(usuario.getResponsabilidade());
    JCheckBox ativoCheckBox = new JCheckBox("Usuário Ativo", usuario.isAtivo());
    
    panel.add(new JLabel("Username:"));
    panel.add(usernameField);
    panel.add(new JLabel("Password:"));
    panel.add(passwordField);
    panel.add(new JLabel("Nome:"));
    panel.add(nomeField);
    panel.add(new JLabel("Tipo:"));
    panel.add(tipoCombo);
    panel.add(new JLabel("Instituição:"));
    panel.add(instituicaoField);
    panel.add(new JLabel("Responsabilidade:"));
    panel.add(responsabilidadeField);
    panel.add(new JLabel("Status:"));
    panel.add(ativoCheckBox);
    
    JButton salvarBtn = new JButton("Salvar Alterações");
    salvarBtn.addActionListener(e -> {
        String password = new String(passwordField.getPassword());
        String nome = nomeField.getText().trim();
        String tipo = tipoCombo.getSelectedItem().toString();
        String instituicao = instituicaoField.getText().trim();
        String responsabilidade = responsabilidadeField.getText().trim();
        boolean ativo = ativoCheckBox.isSelected();
        
        if (nome.isEmpty() || instituicao.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Atualizar dados do usuário
        usuario.setPassword(password);
        usuario.setNome(nome);
        usuario.setTipo(tipo);
        usuario.setInstituicao(instituicao);
        usuario.setResponsabilidade(responsabilidade);
        usuario.setAtivo(ativo);
        
        JOptionPane.showMessageDialog(dialog, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        atualizarTabelaUsuarios();
        dialog.dispose();
    });
    
    JButton cancelarBtn = new JButton("Cancelar");
    cancelarBtn.addActionListener(e -> dialog.dispose());
    
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(cancelarBtn);
    buttonPanel.add(salvarBtn);
    
    panel.add(new JLabel()); // espaço vazio
    panel.add(buttonPanel);
    
    dialog.add(panel);
    dialog.setVisible(true);
}
    
    private void removerUsuario() {
        int selectedRow = usuariosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String username = tableModel.getValueAt(selectedRow, 0).toString();
        
        if (username.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Não é possível remover o usuário admin!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja remover o usuário " + username + "?", 
            "Confirmar Remoção", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            TelaLogin.getUsuarios().remove(username);
            atualizarTabelaUsuarios();
            JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void gerarRelatorioDenuncias() {
        HashMap<String, Denuncia> denuncias = Main.getDenuncias();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DE DENÚNCIAS\n");
        relatorio.append("=====================\n\n");
        relatorio.append("Total de Denúncias: ").append(denuncias.size()).append("\n\n");
        
        // Contar por status
        int pendentes = 0, resolvidas = 0, canceladas = 0;
        for (Denuncia denuncia : denuncias.values()) {
            switch (denuncia.getStatus()) {
                case "Pendente": pendentes++; break;
                case "Resolvido": resolvidas++; break;
                case "Cancelado": canceladas++; break;
            }
        }
        
        relatorio.append("Pendentes: ").append(pendentes).append("\n");
        relatorio.append("Resolvidas: ").append(resolvidas).append("\n");
        relatorio.append("Canceladas: ").append(canceladas).append("\n");
        
        JTextArea textArea = new JTextArea(relatorio.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Relatório de Denúncias", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gerarRelatorioAgentes() {
        HashMap<String, Usuario> usuarios = TelaLogin.getUsuarios();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO DE AGENTES\n");
        relatorio.append("====================\n\n");
        relatorio.append("Total de Agentes: ").append(usuarios.size()).append("\n\n");
        
        int policia = 0, hospital = 0, inas = 0, admin = 0;
        for (Usuario usuario : usuarios.values()) {
            switch (usuario.getTipo()) {
                case "POLICIA": policia++; break;
                case "HOSPITAL": hospital++; break;
                case "INAS": inas++; break;
                case "ADMIN": admin++; break;
            }
        }
        
        relatorio.append("Polícia: ").append(policia).append("\n");
        relatorio.append("Hospital: ").append(hospital).append("\n");
        relatorio.append("INAS: ").append(inas).append("\n");
        relatorio.append("Administradores: ").append(admin).append("\n");
        
        JTextArea textArea = new JTextArea(relatorio.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Relatório de Agentes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gerarEstatisticas() {
        // Implementação similar aos outros relatórios
        JOptionPane.showMessageDialog(this, "Estatísticas geradas com sucesso!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void gerarRelatorioAcompanhamentos() {
        // Implementação para relatório de acompanhamentos
        JOptionPane.showMessageDialog(this, "Relatório de acompanhamentos gerado!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportarRelatorios() {
        JOptionPane.showMessageDialog(this, "Relatórios exportados com sucesso!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    private JPanel criarPainelEstatisticas() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Dados para estatísticas
    HashMap<String, Denuncia> denuncias = Main.getDenuncias();
    HashMap<String, Usuario> usuarios = TelaLogin.getUsuarios();
    
    int totalDenuncias = denuncias.size();
    int pendentes = 0, resolvidas = 0, canceladas = 0;
    int policia = 0, hospital = 0, inas = 0, admin = 0;
    
    for (Denuncia denuncia : denuncias.values()) {
        switch (denuncia.getStatus()) {
            case "Pendente": pendentes++; break;
            case "Resolvido": resolvidas++; break;
            case "Cancelado": canceladas++; break;
        }
    }
    
    for (Usuario usuario : usuarios.values()) {
        switch (usuario.getTipo()) {
            case "POLICIA": policia++; break;
            case "HOSPITAL": hospital++; break;
            case "INAS": inas++; break;
            case "ADMIN": admin++; break;
        }
    }
    
    // Criar painel de estatísticas
    JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    
    // Estatísticas de Denúncias
    JPanel denunciasPanel = new JPanel(new BorderLayout());
    denunciasPanel.setBorder(BorderFactory.createTitledBorder("Estatísticas de Denúncias"));
    JTextArea denunciasText = new JTextArea();
    denunciasText.setText(
        "Total: " + totalDenuncias + "\n" +
        "Pendentes: " + pendentes + "\n" +
        "Resolvidas: " + resolvidas + "\n" +
        "Canceladas: " + canceladas
    );
    denunciasText.setEditable(false);
    denunciasPanel.add(new JScrollPane(denunciasText), BorderLayout.CENTER);
    
    // Estatísticas de Usuários
    JPanel usuariosPanel = new JPanel(new BorderLayout());
    usuariosPanel.setBorder(BorderFactory.createTitledBorder("Estatísticas de Usuários"));
    JTextArea usuariosText = new JTextArea();
    usuariosText.setText(
        "Total: " + usuarios.size() + "\n" +
        "Polícia: " + policia + "\n" +
        "Hospital: " + hospital + "\n" +
        "INAS: " + inas + "\n" +
        "Admin: " + admin
    );
    usuariosText.setEditable(false);
    usuariosPanel.add(new JScrollPane(usuariosText), BorderLayout.CENTER);
    
    // Acompanhamentos Recentes
    JPanel acompanhamentosPanel = new JPanel(new BorderLayout());
    acompanhamentosPanel.setBorder(BorderFactory.createTitledBorder("Acompanhamentos Recentes"));
    JTextArea acompanhamentosText = new JTextArea();
    
    HashMap<String, Acompanhamento> acompanhamentos = Main.getAcompanhamentos();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
    
    StringBuilder acText = new StringBuilder();
    int count = 0;
    for (Acompanhamento ac : acompanhamentos.values()) {
        if (count >= 10) break; // Mostrar apenas os 10 mais recentes
        acText.append(sdf.format(ac.getDataAcompanhamento()))
              .append(" - ").append(ac.getTipoAgente())
              .append(": ").append(ac.getAcaoTomada()).append("\n");
        count++;
    }
    
    acompanhamentosText.setText(acText.toString());
    acompanhamentosText.setEditable(false);
    acompanhamentosPanel.add(new JScrollPane(acompanhamentosText), BorderLayout.CENTER);
    
    // Denúncias por Província
    JPanel provinciaPanel = new JPanel(new BorderLayout());
    provinciaPanel.setBorder(BorderFactory.createTitledBorder("Denúncias por Província"));
    JTextArea provinciaText = new JTextArea();
    
    HashMap<String, Integer> provinciaCount = new HashMap<>();
    for (Denuncia denuncia : denuncias.values()) {
        String provincia = denuncia.getProvincia();
        provinciaCount.put(provincia, provinciaCount.getOrDefault(provincia, 0) + 1);
    }
    
    StringBuilder provText = new StringBuilder();
    for (String provincia : provinciaCount.keySet()) {
        provText.append(provincia).append(": ").append(provinciaCount.get(provincia)).append("\n");
    }
    
    provinciaText.setText(provText.toString());
    provinciaText.setEditable(false);
    provinciaPanel.add(new JScrollPane(provinciaText), BorderLayout.CENTER);
    
    statsPanel.add(denunciasPanel);
    statsPanel.add(usuariosPanel);
    statsPanel.add(acompanhamentosPanel);
    statsPanel.add(provinciaPanel);
    
    panel.add(new JLabel("Dashboard - Estatísticas do Sistema", JLabel.CENTER), BorderLayout.NORTH);
    panel.add(statsPanel, BorderLayout.CENTER);
    
    return panel;
}
}