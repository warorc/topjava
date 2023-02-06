package ru.javawebinar.topjava.dao;

import java.util.List;

public interface CrudItems<T> {
    List<T> getAllItems();

    void addItem(T item);

    void deleteItem(int itemId);

    void editItem(T editedItem);

    T getItemById(Integer id);

}
