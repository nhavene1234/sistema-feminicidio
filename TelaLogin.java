import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TelaLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    
    static {
        // Criar usuário admin padrão
        usuarios.put("admin", new Usuario("admin", "admin123", "ADMIN", "Administrador", "Sistema", "Gestão Total"));
    }
    
    public TelaLogin() {
        setTitle("Login - Sistema de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Login do Agente");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        
        // Botões
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton voltarBtn = new JButton("Voltar ao Início");
        voltarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(voltarBtn);
        buttonPanel.add(loginBtn);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        // Action Listener para Login
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        // Enter para login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void fazerLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuario = usuarios.get(username);
        if (usuario == null || !usuario.getPassword().equals(password) || !usuario.isAtivo()) {
            JOptionPane.showMessageDialog(this, "Username ou password incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Login bem sucedido
        JOptionPane.showMessageDialog(this, "Login bem sucedido!\nBem-vindo, " + usuario.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        
        // Redirecionar conforme o tipo de usuário
        switch (usuario.getTipo()) {
            case "ADMIN":
                new TelaAdmin(usuario).setVisible(true);
                break;
            case "POLICIA":
                new TelaPolicia(usuario).setVisible(true);
                break;
            case "HOSPITAL":
                new TelaHospital(usuario).setVisible(true);
                break;
            case "INAS":
                new TelaINAS(usuario).setVisible(true);
                break;
        }
        
        dispose();
    }
    
    public static HashMap<String, Usuario> getUsuarios() {
        return usuarios;
    }
}