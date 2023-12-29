import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EditUserDialog extends JFrame implements ActionListener {

    private JTextField nameField, passwordField, mobileField, emailField, genderField;
    private JButton saveButton, cancelButton;

    public EditUserDialog(String name, String password, String mobile, String email, String gender) {
        super("Edit User Information");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(name);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(password);
        JLabel mobileLabel = new JLabel("Mobile:");
        mobileField = new JTextField(mobile);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(email);
        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(gender);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        nameLabel.setBounds(20, 20, 80, 25);
        nameField.setBounds(120, 20, 200, 25);

        passwordLabel.setBounds(20, 50, 80, 25);
        passwordField.setBounds(120, 50, 200, 25);

        mobileLabel.setBounds(20, 80, 80, 25);
        mobileField.setBounds(120, 80, 200, 25);

        emailLabel.setBounds(20, 110, 80, 25);
        emailField.setBounds(120, 110, 200, 25);

        genderLabel.setBounds(20, 140, 80, 25);
        genderField.setBounds(120, 140, 200, 25);

        saveButton.setBounds(20, 180, 80, 25);
        cancelButton.setBounds(120, 180, 80, 25);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(mobileLabel);
        panel.add(mobileField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(genderLabel);
        panel.add(genderField);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            try {
                String name = nameField.getText();
                String password = passwordField.getText();
                String mobile = mobileField.getText();
                String email = emailField.getText();
                String gender = genderField.getText();

                java.util.List<String> lines = Files.readAllLines(Paths.get("data\\data.txt"));

                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("Name:" + name)) {
                        lines.set(i + 1, "Password:" + password);
                        lines.set(i + 2, "Mobile:" + mobile);
                        lines.set(i + 3, "Email:" + email);
                        lines.set(i + 4, "Gender:" + gender);
                        break;
                    }
                }

                Files.write(Paths.get("data\\data.txt"), lines);

                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditUserDialog("", "", "", "", "").setVisible(true);
        });
    }
}
