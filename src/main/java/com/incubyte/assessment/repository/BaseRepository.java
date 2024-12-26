package com.incubyte.assessment.repository;
import java.util.List;

public interface BaseRepository<T> {

    void add(T item);

    T getById(String id);

    List<T> getAll();

    boolean existsById(String id);
}