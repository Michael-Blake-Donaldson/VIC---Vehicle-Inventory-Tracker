package Vehicle_Inventory_Control;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VehicleOrganization extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9076467126267524932L;
	private List<Vehicle> vehicles;
    private JTable table;
    private DefaultTableModel model;

    public VehicleOrganization() {
        setTitle("VIC - Vehicle Inventory Control");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Load vehicles from file
        vehicles = loadVehicles();

        // Create table model
        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Filing Number");
        model.addColumn("Damaged");
        model.addColumn("Damage Reason");
        model.addColumn("Usage");
        model.addColumn("Notes");
        model.addColumn("Resolved");

        // Create table
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add sorting functionality to table headers
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Create footer panel
        JPanel footerPanel = new JPanel();
        JButton addButton = new JButton("Add Vehicle");
        JButton resolveButton = new JButton("Resolve");
        JButton deleteResolvedButton = new JButton("Delete Resolved");
        JButton deleteByFilingButton = new JButton("Delete by Filing Number");
        footerPanel.add(addButton);
        footerPanel.add(resolveButton);
        footerPanel.add(deleteResolvedButton);
        footerPanel.add(deleteByFilingButton);

        // Add components to frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVehicle();
            }
        });

        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolveVehicle();
            }
        });

        deleteResolvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteResolvedVehicles();
            }
        });

        deleteByFilingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteVehicleByFilingNumber();
            }
        });

        // Display the frame
        setVisible(true);

        // Add window listener to save vehicles when the program is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveVehicles();
            }
        });
    }

    private void addVehicle() {
        // Create a dialog for adding a new vehicle
        JDialog dialog = new JDialog(this, "Add Vehicle", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Create components for the dialog
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel filingNumberLabel = new JLabel("Filing Number:");
        JTextField filingNumberField = new JTextField(20);
        JLabel damagedLabel = new JLabel("Damaged:");
        JCheckBox damagedCheckbox = new JCheckBox();
        JLabel damageReasonLabel = new JLabel("Damage Reason:");
        JTextField damageReasonField = new JTextField(20);
        JLabel usageLabel = new JLabel("Usage:");
        JTextField usageField = new JTextField(20);
        JLabel notesLabel = new JLabel("Notes:");
        JTextArea notesArea = new JTextArea(5, 20);

        // Create button
        JButton addButton = new JButton("Add");

        // Create panel
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(filingNumberLabel);
        panel.add(filingNumberField);
        panel.add(damagedLabel);
        panel.add(damagedCheckbox);
        panel.add(damageReasonLabel);
        panel.add(damageReasonField);
        panel.add(usageLabel);
        panel.add(usageField);
        panel.add(notesLabel);
        panel.add(new JScrollPane(notesArea));
        panel.add(new JLabel());
        panel.add(addButton);

        // Add action listener to the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new vehicle object
                Vehicle vehicle = new Vehicle(
                        nameField.getText(),
                        filingNumberField.getText(),
                        damagedCheckbox.isSelected(),
                        damagedCheckbox.isSelected() ? damageReasonField.getText() : "",
                        usageField.getText(),
                        notesArea.getText(),
                        false // Initially not resolved
                );

                // Add the vehicle to the list
                vehicles.add(vehicle);

                // Add the vehicle to the table
                addToTable(vehicle);

                // Close the dialog
                dialog.dispose();
            }
        });

        // Add panel to the dialog
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void resolveVehicle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            Vehicle vehicle = vehicles.get(modelRow);
            vehicle.setResolved(true);
            model.setValueAt(true, modelRow, 6); // Update "Resolved" column in the table
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to resolve.");
        }
    }

    private void deleteResolvedVehicles() {
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.isResolved()) {
                iterator.remove();
            }
        }
        refreshTable();
    }

    private void deleteVehicleByFilingNumber() {
        String filingNumber = JOptionPane.showInputDialog(this, "Enter the filing number to delete:");
        if (filingNumber != null && !filingNumber.isEmpty()) {
            Iterator<Vehicle> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                Vehicle vehicle = iterator.next();
                if (vehicle.getFilingNumber().equalsIgnoreCase(filingNumber)) {
                    iterator.remove();
                }
            }
            refreshTable();
        }
    }

    private List<Vehicle> loadVehicles() {
        List<Vehicle> loadedVehicles = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("VIC_Records.txt"))) {
            loadedVehicles = (List<Vehicle>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
        }
        return loadedVehicles;
    }

    private void saveVehicles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("VIC_Records.txt"))) {
            oos.writeObject(vehicles);
        } catch (IOException e) {
            // Handle exception
        }
    }

    private void addToTable(Vehicle vehicle) {
        model.addRow(new Object[]{
                vehicle.getName(),
                vehicle.getFilingNumber(),
                vehicle.isDamaged(),
                vehicle.isDamaged() ? vehicle.getDamageReason() : "",
                vehicle.getUsage(),
                vehicle.getNotes(),
                vehicle.isResolved()
        });
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear the table
        for (Vehicle vehicle : vehicles) {
            addToTable(vehicle);
        }
    }


}