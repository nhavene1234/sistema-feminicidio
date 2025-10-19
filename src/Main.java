import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

public class Main {
    private static HashMap<String, Denuncia> denuncias = new HashMap<>();
    private static HashMap<String, Acompanhamento> acompanhamentos = new HashMap<>();
    private static int acompanhamentoCounter = 1;
    public static void main(String[] args) {
        // Configurar look and feel
        try {
          //  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Criar e mostrar a tela inicial
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }
    
    public static HashMap<String, Denuncia> getDenuncias() {
        return denuncias;
    }
    public static HashMap<String, Acompanhamento> getAcompanhamentos() {
        return acompanhamentos;
    }
    public static void addAcompanhamento(Acompanhamento acompanhamento) {
        String id = "ACO" + String.format("%06d", acompanhamentoCounter++);
        acompanhamentos.put(id, acompanhamento);
    }
}