import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class GUI {
    private JPanel panel;
    private JTextField serviceField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private JButton generatePasswordButton;
    private JTextField generatedPasswordField;  // Câmp pentru parola generată

    public GUI() {
        panel = new JPanel();
        serviceField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        generatedPasswordField = new JTextField(20);
        generatePasswordButton = new JButton("Generate Secure Password");
        saveButton = new JButton("Save Password");

        panel.add(new JLabel("Service:"));
        panel.add(serviceField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(generatePasswordButton);
        panel.add(new JLabel("Generated Password:"));
        panel.add(generatedPasswordField);
        panel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String service = serviceField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                String encryptedPassword = encryptPassword(password);

                FileStorage.savePasswordToFile(service, username, encryptedPassword);
            }
        });

        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generatedPassword = generateSecurePassword();
                generatedPasswordField.setText(generatedPassword);
            }
        });
    }

    private String encryptPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateSecurePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public static void createWindow() {
        JFrame frame = new JFrame("Password Manager");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
