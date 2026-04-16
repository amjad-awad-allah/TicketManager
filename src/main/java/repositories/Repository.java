package repositories;

import java.util.ArrayList;
import java.util.List;

import exceptions.TicketNotFoundException;
import models.Identifiable;

public class Repository<T extends Identifiable> {

    public List<T> items;

    public Repository() {
        items = new ArrayList<>();
    }

    public boolean add(T item) {
        return items.add(item);
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(items);
    }

    public T getById(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }

        throw new TicketNotFoundException("Item with ID " + id + " not found");

    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public boolean update(T item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
                return true;
            }
        }
        return false; // or throw an exception
    }

    public void clear() {
        items.clear();
    }

    public boolean exists(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        for (T item : items) {
            System.out.println(item);
        }
    }
}
