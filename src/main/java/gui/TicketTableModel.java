package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.Ticket;

public class TicketTableModel extends AbstractTableModel {
    private List<Ticket> allTickets;
    private List<Ticket> filteredTickets;
    private final String[] columnNames = { "ID", "Title", "Description", "Priority", "Status", "Customer", "Date", "Actions" };

    public TicketTableModel(List<Ticket> tickets) {
        this.allTickets = tickets;
        this.filteredTickets = tickets;
    }

    public void updateData(List<Ticket> tickets) {
        this.allTickets = tickets;
        applyFilter("");
    }

    public void applyFilter(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            filteredTickets = allTickets;
        } else {
            String lowerFilter = filter.toLowerCase();
            filteredTickets = allTickets.stream()
                    .filter(ticket -> String.valueOf(ticket.getId()).contains(lowerFilter) ||
                            ticket.getTitel().toLowerCase().contains(lowerFilter) ||
                            ticket.getBeschreibung().toLowerCase().contains(lowerFilter) ||
                            (ticket.getKunde() != null
                                    && ticket.getKunde().getName().toLowerCase().contains(lowerFilter)))
                    .toList();
        }
        fireTableDataChanged();
    }

    public Ticket getTicketAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < filteredTickets.size()) {
            return filteredTickets.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return filteredTickets.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = filteredTickets.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> ticket.getId();
            case 1 -> ticket.getTitel();
            case 2 -> ticket.getBeschreibung();
            case 3 -> ticket.getPriority();
            case 4 -> ticket.getStatus();
            case 5 -> ticket.getKunde() != null ? ticket.getKunde().getName() : "";
            case 6 -> ticket.getDatum();
            case 7 -> ""; // Action column
            default -> null;
        };
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7;
    }
}