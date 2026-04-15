package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.Ticket;

public class TicketTableModel extends AbstractTableModel {
    private List<Ticket> allTickets;
    private List<Ticket> filteredTickets;
    private String[] columnNames = { "ID", "Title", "Description", "Priority", "Status", "Customer", "Date" };

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
            filteredTickets = allTickets.stream()
                    .filter(ticket -> ticket.getTitel().toLowerCase().contains(filter.toLowerCase()) ||
                            ticket.getBeschreibung().toLowerCase().contains(filter.toLowerCase()) ||
                            (ticket.getKunde() != null
                                    && ticket.getKunde().getName().toLowerCase().contains(filter.toLowerCase())))
                    .toList();
        }
        fireTableDataChanged();
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
        switch (columnIndex) {
            case 0:
                return ticket.getId();
            case 1:
                return ticket.getTitel();
            case 2:
                return ticket.getBeschreibung();
            case 3:
                return ticket.getPriority();
            case 4:
                return ticket.getStatus();
            case 5:
                return ticket.getKunde() != null ? ticket.getKunde().getName() : "";
            case 6:
                return ticket.getDatum();
            default:
                return null;
        }
    }
}