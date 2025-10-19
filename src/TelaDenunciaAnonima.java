import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TelaDenunciaAnonima extends JFrame {
    private JComboBox<String> tipoFeminicidioCombo;
    private JComboBox<String> classificacaoCombo;
    private JComboBox<String> provinciaCombo;
    private JTextField distritoField;
    private JTextField bairroField;
    private JTextArea detalhesArea;
    
    public TelaDenunciaAnonima() {
        setTitle("Denúncia Anônima - Sistema de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Formulário de Denúncia Anônima");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Tipo de Feminicídio
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tipo de Feminicídio:*"), gbc);
        
        tipoFeminicidioCombo = new JComboBox<>(new String[]{
            "Selecione...", "Íntimo (marido, ex-marido, namorado)", 
            "Não Íntimo (agressor desconhecido)"
        });
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(tipoFeminicidioCombo, gbc);
        
        // Classificação
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Classificação:*"), gbc);
        
        classificacaoCombo = new JComboBox<>(new String[]{
            "Selecione...", "Doméstica e Familiar", 
            "Menosprezo e Discriminação"
        });
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(classificacaoCombo, gbc);
        
        // Província
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Província:*"), gbc);
        
        provinciaCombo = new JComboBox<>(new String[]{
            "Selecione...", "Maputo Cidade", "Maputo Província", "Gaza", 
            "Inhambane", "Sofala", "Manica", "Tete", "Zambézia", 
            "Nampula", "Cabo Delgado", "Niassa"
        });
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(provinciaCombo, gbc);
        
        // Distrito
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Distrito:*"), gbc);
        
        distritoField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(distritoField, gbc);
        
        // Bairro
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Bairro:*"), gbc);
        
        bairroField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(bairroField, gbc);
        
        // Detalhes
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Detalhes da Denúncia:*"), gbc);
        
        detalhesArea = new JTextArea(5, 20);
        detalhesArea.setLineWrap(true);
        detalhesArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detalhesArea);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(scrollPane, gbc);
        
        // Botão Submeter
        JButton submeterBtn = new JButton("Submeter Denúncia");
        submeterBtn.setBackground(new Color(40, 167, 69));
        submeterBtn.setForeground(Color.WHITE);
        submeterBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        submeterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarFormulario()) {
                    submeterDenuncia();
                }
            }
        });
        
        // Botão Voltar
        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(voltarBtn);
        buttonPanel.add(submeterBtn);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private boolean validarFormulario() {
        if (tipoFeminicidioCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo de feminicídio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (classificacaoCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione a classificação!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (provinciaCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione a província!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (distritoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o distrito!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (bairroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o bairro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (detalhesArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os detalhes da denúncia!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void submeterDenuncia() {
        Denuncia denuncia = new Denuncia();
        denuncia.setAnonima(true);
        denuncia.setTipoFeminicidio(tipoFeminicidioCombo.getSelectedItem().toString());
        denuncia.setClassificacao(classificacaoCombo.getSelectedItem().toString());
        denuncia.setProvincia(provinciaCombo.getSelectedItem().toString());
        denuncia.setDistrito(distritoField.getText().trim());
        denuncia.setBairro(bairroField.getText().trim());
        denuncia.setDetalhes(detalhesArea.getText().trim());
        
        HashMap<String, Denuncia> denuncias = Main.getDenuncias();
        denuncias.put(denuncia.getId(), denuncia);
        
        JOptionPane.showMessageDialog(this, 
            "Denúncia submetida com sucesso!\nSeu código de acompanhamento: " + denuncia.getId() + 
            "\nGuarde este código para verificar o status da sua denúncia.",
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
        
        new TelaInicial().setVisible(true);
        dispose();
    }
}