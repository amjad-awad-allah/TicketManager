package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import exceptions.InvalidDataException;
import models.Kunde;
import models.Priority;
import models.Status;
import models.Ticket;

public class EditTicketDialog extends JDialog {
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JComboBox<Priority> priorityBox;
    private final JComboBox<Status> statusBox;
    private final JComboBox<Kunde> customerBox;
    private final RoundedButton cancelButton;
    private final RoundedButton saveButton;
    private boolean confirmed = false;
    private Ticket ticket;

    // Define colors
    private static final Color BACKGROUND_COLOR = Color.decode("#F9FAFB");
    private static final Color SURFACE_COLOR = Color.decode("#FFFFFF");
    private static final Color PRIMARY_COLOR = Color.decode("#4F46E5");
    private static final Color TEXT_COLOR = Color.decode("#111827");
    private static final Color SECONDARY_TEXT_COLOR = Color.decode("#6B7280");
    private static final Color BORDER_COLOR = Color.decode("#E5E7EB");
    private static final Color CANCEL_BUTTON_COLOR = Color.decode("#F3F4F6");

    public EditTicketDialog(JFrame parent, Ticket ticketToEdit, List<Kunde> customers, boolean isViewOnly) {
        super(parent, isViewOnly ? "Ticket Details" : "Ticket Details / Edit", true);
        this.ticket = ticketToEdit;
        
        setSize(550, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Main panel with form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(SURFACE_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 8, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        formPanel.add(titleLabel, gbc);

        // Title Field
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 16, 0);
        titleField = new JTextField(ticket.getTitel());
        titleField.setEditable(!isViewOnly);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setForeground(TEXT_COLOR);
        titleField.setBackground(SURFACE_COLOR);
        titleField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        formPanel.add(titleField, gbc);

        // Description Label
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel descLabel = new JLabel("Description");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        descLabel.setForeground(TEXT_COLOR);
        formPanel.add(descLabel, gbc);

        // Description Area
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = new JTextArea(ticket.getBeschreibung(), 5, 20);
        descriptionArea.setEditable(!isViewOnly);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setForeground(TEXT_COLOR);
        descriptionArea.setBackground(SURFACE_COLOR);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(BorderFactory.createEmptyBorder());
        formPanel.add(descScroll, gbc);

        // Priority and Status Panel
        JPanel comboPanel = new JPanel(new GridBagLayout());
        comboPanel.setBackground(SURFACE_COLOR);
        GridBagConstraints cGbc = new GridBagConstraints();
        cGbc.fill = GridBagConstraints.HORIZONTAL;
        cGbc.weightx = 1.0;
        
        JLabel priorityLabel = new JLabel("Priority");
        priorityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priorityLabel.setForeground(TEXT_COLOR);
        cGbc.gridx = 0; cGbc.gridy = 0; cGbc.insets = new Insets(0, 0, 5, 10);
        comboPanel.add(priorityLabel, cGbc);
        
        priorityBox = new JComboBox<>(Priority.values());
        priorityBox.setEnabled(!isViewOnly);
        priorityBox.setSelectedItem(ticket.getPriority());
        priorityBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priorityBox.setBackground(SURFACE_COLOR);
        cGbc.gridy = 1; cGbc.insets = new Insets(0, 0, 0, 10);
        comboPanel.add(priorityBox, cGbc);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(TEXT_COLOR);
        cGbc.gridx = 1; cGbc.gridy = 0; cGbc.insets = new Insets(0, 0, 5, 0);
        comboPanel.add(statusLabel, cGbc);
        
        statusBox = new JComboBox<>(Status.values());
        statusBox.setEnabled(!isViewOnly);
        statusBox.setSelectedItem(ticket.getStatus());
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusBox.setBackground(SURFACE_COLOR);
        cGbc.gridy = 1; cGbc.insets = new Insets(0, 0, 0, 0);
        comboPanel.add(statusBox, cGbc);

        gbc.gridy = 4;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 16, 0);
        formPanel.add(comboPanel, gbc);

        // Customer Label
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel customerLabel = new JLabel("Customer");
        customerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        customerLabel.setForeground(TEXT_COLOR);
        formPanel.add(customerLabel, gbc);

        // Customer ComboBox
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 24, 0);
        customerBox = new JComboBox<>(customers.toArray(new Kunde[0]));
        // Select current customer
        if (ticket.getKunde() != null) {
            for (int i = 0; i < customerBox.getItemCount(); i++) {
                if (customerBox.getItemAt(i).getId() == ticket.getKunde().getId()) {
                    customerBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        customerBox.setEnabled(!isViewOnly);
        customerBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        customerBox.setForeground(TEXT_COLOR);
        customerBox.setBackground(SURFACE_COLOR);
        customerBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        formPanel.add(customerBox, gbc);

        // Center content wrapped
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(BACKGROUND_COLOR);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        centerWrapper.add(formPanel, BorderLayout.CENTER);
        
        add(centerWrapper, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 20, 24));

        JPanel buttonSubPanel = new JPanel();
        buttonSubPanel.setBackground(BACKGROUND_COLOR);

        cancelButton = new RoundedButton(isViewOnly ? "Close" : "Cancel", 16);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setForeground(SECONDARY_TEXT_COLOR);
        cancelButton.setBackground(CANCEL_BUTTON_COLOR);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        cancelButton.addActionListener(e -> setVisible(false));
        buttonSubPanel.add(cancelButton);

        if (!isViewOnly) {
            saveButton = new RoundedButton("Save Changes", 16);
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            saveButton.setForeground(Color.WHITE);
            saveButton.setBackground(PRIMARY_COLOR);
            saveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            saveButton.addActionListener(e -> {
                try {
                    updateTicket();
                    confirmed = true;
                    setVisible(false);
                } catch (InvalidDataException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonSubPanel.add(saveButton);
        } else {
            saveButton = null;
        }

        buttonPanel.add(buttonSubPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateTicket() throws InvalidDataException {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        Priority priority = (Priority) priorityBox.getSelectedItem();
        Status status = (Status) statusBox.getSelectedItem();
        Kunde customer = (Kunde) customerBox.getSelectedItem();

        if (title.isEmpty()) {
            throw new InvalidDataException("Title cannot be empty");
        }

        ticket.setTitel(title);
        ticket.setBeschreibung(description);
        ticket.setPriority(priority);
        ticket.setStatus(status);
        ticket.setKunde(customer);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
