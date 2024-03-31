package Vehicle_Inventory_Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SignInPage extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5955499553350855979L;
	private Map<String, String> credentials;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignInPage() {
        setTitle("VIC - Vehicle Inventory Control");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Load credentials from file
        credentials = loadCredentials();

        // Creating components
        JLabel nameLabel = new JLabel("VIC", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JButton signInButton = new JButton("Sign In");
        JButton createAccountButton = new JButton("Create Account");

        // Creating panels
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.add(nameLabel, BorderLayout.CENTER);

        JPanel signInPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        signInPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        signInPanel.add(usernameField, gbc);
        gbc.gridy++;
        signInPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        signInPanel.add(passwordField, gbc);
        gbc.gridy++;
        signInPanel.add(signInButton, gbc);
        gbc.gridy++;
        signInPanel.add(createAccountButton, gbc);

        // Adding components to the frame
        setLayout(new BorderLayout());
        add(logoPanel, BorderLayout.NORTH);
        add(signInPanel, BorderLayout.CENTER);

        // Action listener for sign in button
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
                    // Successful sign-in, open the VehicleOrganization page
                    openVehicleOrganizationPage();
                } else {
                    JOptionPane.showMessageDialog(SignInPage.this, "Invalid username or password. Please try again.");
                }
            }
        });

        // Action listener for create account button
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Create Account page
                openCreateAccountPage();
            }
        });

        // Display the frame
        setVisible(true);
    }

    private Map<String, String> loadCredentials() {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // Handle file IO exception
        }
        return credentials;
    }

    private void saveCredentials(Map<String, String> credentials) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
            for (Map.Entry<String, String> entry : credentials.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle file IO exception
        }
    }

    private void openVehicleOrganizationPage() {
        // Close the sign-in page
        dispose();
        // Open the VehicleOrganization page
        new VehicleOrganization();
    }

    private void openCreateAccountPage() {
        // Close the sign-in page
        dispose();
        // Open the CreateAccount page
        new CreateAccountPage();
    }

    public static void main(String[] args) {
        // Create and display the Sign In page
        SwingUtilities.invokeLater(SignInPage::new);
    }
}

class CreateAccountPage extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField employeeNumberField;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;

    public CreateAccountPage() {
        setTitle("Create Account");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Creating components
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel employeeNumberLabel = new JLabel("Employee Number:");
        employeeNumberField = new JTextField(20);
        JLabel newUsernameLabel = new JLabel("Username:");
        newUsernameField = new JTextField(20);
        JLabel newPasswordLabel = new JLabel("Password:");
        newPasswordField = new JPasswordField(20);

        JButton createAccountButton = new JButton("Create Account");

        // Creating panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(firstNameLabel, gbc);
        gbc.gridy++;
        panel.add(firstNameField, gbc);
        gbc.gridy++;
        panel.add(lastNameLabel, gbc);
        gbc.gridy++;
        panel.add(lastNameField, gbc);
        gbc.gridy++;
        panel.add(employeeNumberLabel, gbc);
        gbc.gridy++;
        panel.add(employeeNumberField, gbc);
        gbc.gridy++;
        panel.add(newUsernameLabel, gbc);
        gbc.gridy++;
        panel.add(newUsernameField, gbc);
        gbc.gridy++;
        panel.add(newPasswordLabel, gbc);
        gbc.gridy++;
        panel.add(newPasswordField, gbc);
        gbc.gridy++;
        panel.add(createAccountButton, gbc);

        // Action listener for create account button
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String employeeNumber = employeeNumberField.getText();
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                // Validation (You can add more validation as per your requirement)
                if (firstName.isEmpty() || lastName.isEmpty() || employeeNumber.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateAccountPage.this, "Please fill in all fields.");
                } else {
                    // Save the new account credentials
                    saveNewAccount(newUsername, newPassword);

                    // Inform the user about successful account creation
                    JOptionPane.showMessageDialog(CreateAccountPage.this, "Account created successfully. You can now sign in.");

                    // Go back to the sign-in page
                    openSignInPage();
                }
            }
        });

        // Adding panel to the frame
        add(panel);

        // Display the frame
        setVisible(true);
    }

    private void saveNewAccount(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt", true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            // Handle file IO exception
        }
    }

    private void openSignInPage() {
        // Close the create account page
        dispose();
        // Open the sign-in page
        new SignInPage();
    }
}