package br.edu.ifto.gestorfrotaapi.shared.repository;

import java.util.List;

public interface BaseRepository<T, ID> {

    List<T> findAll();
    T findById(ID id);
    void save(T t);

}
