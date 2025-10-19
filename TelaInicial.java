import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {
    private JCheckBox anonimatoCheckBox;
    
    public TelaInicial() {
        setTitle("Sistema de Denúncia de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        // No método initComponents() da TelaInicial, adicione:

JButton loginAgentesBtn = new JButton("Login Agentes");
loginAgentesBtn.setFont(new Font("Arial", Font.BOLD, 14));
loginAgentesBtn.setBackground(new Color(108, 117, 125));
loginAgentesBtn.setForeground(Color.WHITE);
loginAgentesBtn.setPreferredSize(new Dimension(150, 40));

loginAgentesBtn.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new TelaLogin().setVisible(true);
        dispose();
    }
});


        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 53, 69));
        headerPanel.setPreferredSize(new Dimension(600, 80));
        
        JLabel titleLabel = new JLabel("Bem-vindo ao Sistema de Denúncia Contra Feminicídio");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Painel de conteúdo
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextArea descricaoArea = new JTextArea(
            "Este sistema permite que você faça denúncias de casos de feminicídio " +
            "de forma segura e anônima se desejar. Sua denúncia será encaminhada " +
            "para os agentes da Policia, hospitais e INAS para as devidas providências.\n\n" +
            "Juntos podemos combater a violência contra a mulher!"
        );
        descricaoArea.setEditable(false);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        descricaoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descricaoArea.setBackground(new Color(240, 240, 240));
        descricaoArea.setPreferredSize(new Dimension(500, 120));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(descricaoArea, gbc);
        
        // Checkbox anonimato
        anonimatoCheckBox = new JCheckBox("Desejo fazer a denúncia de forma anônima");
        anonimatoCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        contentPanel.add(anonimatoCheckBox, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton fazerDenunciaBtn = new JButton("Fazer Denúncia");
        fazerDenunciaBtn.setFont(new Font("Arial", Font.BOLD, 14));
        fazerDenunciaBtn.setBackground(new Color(40, 167, 69));
        fazerDenunciaBtn.setForeground(Color.WHITE);
        fazerDenunciaBtn.setPreferredSize(new Dimension(150, 40));
        
        JButton verStatusBtn = new JButton("Ver Status");
        verStatusBtn.setFont(new Font("Arial", Font.BOLD, 14));
        verStatusBtn.setBackground(new Color(0, 123, 255));
        verStatusBtn.setForeground(Color.WHITE);
        verStatusBtn.setPreferredSize(new Dimension(150, 40));
        
        buttonPanel.add(fazerDenunciaBtn);
        buttonPanel.add(verStatusBtn);
        buttonPanel.add(loginAgentesBtn);
        
        gbc.gridy = 2;
        contentPanel.add(buttonPanel, gbc);
        
        // Adicionar action listeners
        fazerDenunciaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean anonimo = anonimatoCheckBox.isSelected();
                if (anonimo) {
                    new TelaDenunciaAnonima().setVisible(true);
                } else {
                    new TelaDenunciaIdentificada().setVisible(true);
                }
                dispose();
            }
        });
        
        verStatusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaVerStatus().setVisible(true);
                dispose();
            }
        });
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}