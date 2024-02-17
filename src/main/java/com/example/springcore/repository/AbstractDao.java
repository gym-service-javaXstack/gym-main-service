package com.example.springcore.repository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractDao<T, S> {
    protected final S storage;

    public Optional<T> get(Integer id) {
        return Optional.ofNullable(getStorageMap().get(id));
    }

    public T save(T entity) {
        int newId = getStorageMap().size() + 1;
        setId(entity, newId);
        getStorageMap().put(newId, entity);
        return getStorageMap().get(newId);
    }

    public T update(T entity, Integer id) {
        getStorageMap().put(id, entity);
        return getStorageMap().get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(getStorageMap().values());
    }

    public void delete(Integer id) {
        getStorageMap().remove(id);
    }

    protected abstract void setId(T entity, Integer id);

    protected abstract Map<Integer, T> getStorageMap();
}