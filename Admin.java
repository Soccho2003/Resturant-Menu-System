import java.nio.file.StandardCopyOption;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.StringTokenizer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Admin extends JFrame implements ActionListener {

    private JTable table;
    private JButton backButton, editButton, deleteButton;

    public Admin() {
        super("Admin Panel");
        this.setSize(750, 510);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Serif", Font.BOLD, 14));
        backButton.setForeground(Color.blue);
        backButton.setBackground(new Color(240, 240, 240));
        backButton.setBorder(null);
        backButton.setBounds(20, 400, 80, 20);
        backButton.addActionListener(this);
        backButton.setFocusable(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        editButton = new JButton("Edit User");
        editButton.setFont(new Font("Serif", Font.BOLD, 14));
        editButton.setForeground(Color.blue);
        editButton.setBackground(new Color(240, 240, 240));
        editButton.setBorder(null);
        editButton.setBounds(220, 400, 100, 20);
        editButton.addActionListener(this);
        editButton.setFocusable(false);
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        deleteButton = new JButton("Delete User");
        deleteButton.setFont(new Font("Serif", Font.BOLD, 14));
        deleteButton.setForeground(Color.red);
        deleteButton.setBackground(new Color(240, 240, 240));
        deleteButton.setBorder(null);
        deleteButton.setBounds(350, 400, 100, 20);
        deleteButton.addActionListener(this);
        deleteButton.setFocusable(false);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createTable();

        this.setLayout(null);
        this.add(backButton);
        this.add(editButton);
        this.add(deleteButton);
        setVisible(true);
    }

    private void createTable() {
        String[] columnNames = {"Name", "Password", "Mobile", "Email", "Gender"};
	    
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
	    
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data\\data.txt"));
            String line;
	    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name")) {
                    String name = line.substring(line.indexOf(":") + 1).trim();
                    String password = reader.readLine().substring(line.indexOf(":") + 1).trim();
                    String mobile = reader.readLine().substring(line.indexOf(":") + 1).trim();
                    String email = reader.readLine().substring(line.indexOf(":") + 1).trim();
                    String gender = reader.readLine().substring(line.indexOf(":") + 1).trim();
	    
                    String[] userData = {name, password, mobile, email, gender};
                    tableModel.addRow(userData);
                }
            }
	    
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 700, 350);
	    
        this.add(scrollPane); 
    }



    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            Login login = new Login();
            this.setVisible(false);
            login.setVisible(true);
        } else if (ae.getSource() == editButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) table.getValueAt(selectedRow, 0);
                String password = (String) table.getValueAt(selectedRow, 1);
                String mobile = (String) table.getValueAt(selectedRow, 2);
                String email = (String) table.getValueAt(selectedRow, 3);
                String gender = (String) table.getValueAt(selectedRow, 4);

                EditUserDialog editUserDialog = new EditUserDialog(name, password, mobile, email, gender);
                editUserDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to edit.");
            }
        } else if (ae.getSource() == deleteButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this user?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(selectedRow);

                    deleteUserData(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a user to delete.");
            }
        }
    }

    private void deleteUserData(int row) {
        try {
            File file = new File("data\\data.txt");
            File tempFile = new File("data\\temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int currentRow = 0;

            while ((currentLine = reader.readLine()) != null) {
                if (currentRow != row) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                currentRow++;
            }

            writer.close();
            reader.close();

            Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Admin admin = new Admin();
    }
}
