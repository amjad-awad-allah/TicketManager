package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import exceptions.InvalidDataException;
import models.Kunde;
import models.Priority;
import models.Ticket;

public class CreateTicketDialog extends JDialog {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JComboBox<Priority> priorityBox;
    private final JComboBox<Kunde> customerBox;
    private final JButton okButton;
    private final JButton cancelButton;
    private boolean confirmed = false;
    private Ticket ticket;

    public CreateTicketDialog(JFrame parent, List<Kunde> customers) {
        super(parent, "Create Ticket", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Set Nimbus look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Fallback
        }

        // Main panel with form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        titleField = new JTextField(25);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(titleField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descLabel.setForeground(Color.BLACK);
        formPanel.add(descLabel, gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 25);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        formPanel.add(descScroll, gbc);

        // Priority
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priorityLabel.setForeground(Color.BLACK);
        formPanel.add(priorityLabel, gbc);
        gbc.gridx = 1;
        priorityBox = new JComboBox<>(Priority.values());
        priorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(priorityBox, gbc);

        // Customer
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel customerLabel = new JLabel("Customer:");
        customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerLabel.setForeground(Color.BLACK);
        formPanel.add(customerLabel, gbc);
        gbc.gridx = 1;
        customerBox = new JComboBox<>(customers.toArray(new Kunde[customers.size()]));
        customerBox.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(customerBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        okButton = new JButton("OK");
        okButton.setBackground(new Color(34, 139, 34));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.addActionListener(e -> {
            try {
                createTicket();
                confirmed = true;
                setVisible(false);
            } catch (InvalidDataException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.addActionListener(e -> setVisible(false));
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createTicket() throws InvalidDataException {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        Priority priority = (Priority) priorityBox.getSelectedItem();
        Kunde customer = (Kunde) customerBox.getSelectedItem();

        if (title.isEmpty()) {
            throw new InvalidDataException("Title cannot be empty");
        }

        ticket = new Ticket(title, description, priority, customer);
        // Status and date are set in constructor
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Ticket getTicket() {
        return ticket;
    }
}