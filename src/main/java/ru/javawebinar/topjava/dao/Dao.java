package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    T add(T item);

    void delete(int id);

    T update(T updatedItem);

    T getById(int id);
}
