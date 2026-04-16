package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class TicketFormDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<Priority> priorityBox;
    private JComboBox<Status> statusBox;
    private JComboBox<Kunde> customerBox;
    private RoundedButton saveButton;
    private RoundedButton cancelButton;
    
    private boolean confirmed = false;
    private Ticket ticket; // Will hold the edited ticket or the newly created one
    private final boolean isViewOnly;
    private final boolean isCreateMode; // true if creating a new ticket, false if editing an existing one

    public TicketFormDialog(JFrame parent, Ticket ticketToEdit, List<Kunde> customers, boolean isViewOnly) {
        super(parent, true); // true for modal
        this.ticket = ticketToEdit;
        this.isViewOnly = isViewOnly;
        this.isCreateMode = (ticketToEdit == null);

        setTitle(isCreateMode ? "Create Ticket" : (isViewOnly ? "Ticket Details" : "Edit Ticket"));
        setSize(550, isCreateMode ? 600 : 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BACKGROUND_COLOR);

        initComponents(customers);
        populateFieldsIfEditing();
    }

    private void initComponents(List<Kunde> customers) {
        // Main panel with form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Theme.SURFACE_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 8, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = UIFactory.createLabel("Title");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        formPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 16, 0);
        titleField = UIFactory.createTextField();
        titleField.setEditable(!isViewOnly);
        formPanel.add(titleField, gbc);

        // Description
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        formPanel.add(UIFactory.createLabel("Description"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 16, 0);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = UIFactory.createTextArea(5, 20);
        descriptionArea.setEditable(!isViewOnly);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(BorderFactory.createEmptyBorder());
        formPanel.add(descScroll, gbc);

        // Priority & Status Panel
        gbc.gridy = 4;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 16, 0);
        
        JPanel comboPanel = new JPanel(new GridBagLayout());
        comboPanel.setBackground(Theme.SURFACE_COLOR);
        GridBagConstraints cGbc = new GridBagConstraints();
        cGbc.fill = GridBagConstraints.HORIZONTAL;
        cGbc.weightx = 1.0;
        
        cGbc.gridx = 0; cGbc.gridy = 0; cGbc.insets = new Insets(0, 0, 5, 10);
        comboPanel.add(UIFactory.createLabel("Priority"), cGbc);
        
        priorityBox = UIFactory.createComboBox(Priority.values());
        priorityBox.setEnabled(!isViewOnly);
        cGbc.gridy = 1; cGbc.insets = new Insets(0, 0, 0, 10);
        comboPanel.add(priorityBox, cGbc);

        // Status field only makes sense when editing/viewing
        if (!isCreateMode) {
            cGbc.gridx = 1; cGbc.gridy = 0; cGbc.insets = new Insets(0, 0, 5, 0);
            comboPanel.add(UIFactory.createLabel("Status"), cGbc);
            
            statusBox = UIFactory.createComboBox(Status.values());
            statusBox.setEnabled(!isViewOnly);
            cGbc.gridy = 1; cGbc.insets = new Insets(0, 0, 0, 0);
            comboPanel.add(statusBox, cGbc);
        }

        formPanel.add(comboPanel, gbc);

        // Customer
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 5, 0);
        formPanel.add(UIFactory.createLabel("Customer"), gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 24, 0);
        customerBox = UIFactory.createComboBox(customers.toArray(new Kunde[0]));
        customerBox.setEnabled(!isViewOnly);
        formPanel.add(customerBox, gbc);

        // Center Wrapper
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(Theme.BACKGROUND_COLOR);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        centerWrapper.add(formPanel, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Theme.BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 20, 24));

        JPanel buttonSubPanel = new JPanel();
        buttonSubPanel.setBackground(Theme.BACKGROUND_COLOR);

        cancelButton = new RoundedButton(isViewOnly ? "Close" : "Cancel", 16);
        cancelButton.setFont(Theme.FONT_BOLD);
        cancelButton.setForeground(Theme.SECONDARY_TEXT_COLOR);
        cancelButton.setBackground(Theme.NEUTRAL_COMPONENT_COLOR);
        cancelButton.addActionListener(e -> setVisible(false));
        buttonSubPanel.add(cancelButton);

        if (!isViewOnly) {
            saveButton = new RoundedButton(isCreateMode ? "Create Ticket" : "Save Changes", 16);
            saveButton.setFont(Theme.FONT_BOLD);
            saveButton.setForeground(Color.WHITE);
            saveButton.setBackground(Theme.PRIMARY_COLOR);
            saveButton.addActionListener(e -> {
                try {
                    processSave();
                } catch (InvalidDataException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonSubPanel.add(saveButton);
        } else {
            // Visual cue for disabled inputs
            titleField.setBackground(Theme.BACKGROUND_COLOR);
            descriptionArea.setBackground(Theme.BACKGROUND_COLOR);
            titleField.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        }

        buttonPanel.add(buttonSubPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFieldsIfEditing() {
        if (!isCreateMode && ticket != null) {
            titleField.setText(ticket.getTitel());
            descriptionArea.setText(ticket.getBeschreibung());
            priorityBox.setSelectedItem(ticket.getPriority());
            statusBox.setSelectedItem(ticket.getStatus());
            
            if (ticket.getKunde() != null) {
                for (int i = 0; i < customerBox.getItemCount(); i++) {
                    if (customerBox.getItemAt(i).getId() == ticket.getKunde().getId()) {
                        customerBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    private void processSave() throws InvalidDataException {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        Priority priority = (Priority) priorityBox.getSelectedItem();
        Kunde customer = (Kunde) customerBox.getSelectedItem();

        if (title.isEmpty()) {
            throw new InvalidDataException("Title cannot be empty");
        }

        if (isCreateMode) {
            ticket = new Ticket(title, description, priority, customer);
        } else {
            ticket.setTitel(title);
            ticket.setBeschreibung(description);
            ticket.setPriority(priority);
            ticket.setStatus((Status) statusBox.getSelectedItem());
            ticket.setKunde(customer);
        }
        confirmed = true;
        setVisible(false);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
