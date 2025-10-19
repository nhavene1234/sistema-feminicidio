import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TelaDenunciaIdentificada extends JFrame {
    private JComboBox<String> tipoFeminicidioCombo;
    private JComboBox<String> classificacaoCombo;
    private JComboBox<String> provinciaCombo;
    private JTextField distritoField;
    private JTextField bairroField;
    private JTextArea detalhesArea;
    private JTextField nomeField;
    private JTextField contactoField;
    private JTextField emailField;
    private JTextField dataOcorrenciaField;
    
    public TelaDenunciaIdentificada() {
        setTitle("Denúncia Identificada - Sistema de Feminicídio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Formulário de Denúncia Identificada");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Nome Completo:*"), gbc);
        nomeField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(nomeField, gbc);
        
        // Contacto
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Contacto:*"), gbc);
        contactoField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(contactoField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:*"), gbc);
        emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(emailField, gbc);
        
        // Data Ocorrência
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Data da Ocorrência (dd/mm/aaaa):*"), gbc);
        dataOcorrenciaField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(dataOcorrenciaField, gbc);
        
        // Tipo de Feminicídio
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tipo de Feminicídio:*"), gbc);
        tipoFeminicidioCombo = new JComboBox<>(new String[]{
            "Selecione...", "Íntimo (marido, ex-marido, namorado)", 
            "Não Íntimo (agressor desconhecido)"
        });
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(tipoFeminicidioCombo, gbc);
        
        // Classificação
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Classificação:*"), gbc);
        classificacaoCombo = new JComboBox<>(new String[]{
            "Selecione...", "Doméstica e Familiar", 
            "Menosprezo e Discriminação"
        });
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(classificacaoCombo, gbc);
        
        // Província
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Província:*"), gbc);
        provinciaCombo = new JComboBox<>(new String[]{
            "Selecione...", "Maputo Cidade", "Maputo Província", "Gaza", 
            "Inhambane", "Sofala", "Manica", "Tete", "Zambézia", 
            "Nampula", "Cabo Delgado", "Niassa"
        });
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(provinciaCombo, gbc);
        
        // Distrito
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Distrito:*"), gbc);
        distritoField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(distritoField, gbc);
        
        // Bairro
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Bairro:*"), gbc);
        bairroField = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++;
        formPanel.add(bairroField, gbc);
        
        // Detalhes
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Detalhes da Denúncia:*"), gbc);
        detalhesArea = new JTextArea(5, 20);
        detalhesArea.setLineWrap(true);
        detalhesArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detalhesArea);
        gbc.gridx = 1; gbc.gridy = row;
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
        if (nomeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (contactoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o contacto!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o email!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dataOcorrenciaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a data da ocorrência!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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
        denuncia.setAnonima(false);
        denuncia.setNomeDenunciante(nomeField.getText().trim());
        denuncia.setContacto(contactoField.getText().trim());
        denuncia.setEmail(emailField.getText().trim());
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataOcorrencia = sdf.parse(dataOcorrenciaField.getText().trim());
            denuncia.setDataOcorrencia(dataOcorrencia);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use dd/mm/aaaa", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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