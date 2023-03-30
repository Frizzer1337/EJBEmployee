package com.frizzer.employeeapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.WebApplicationException;
import java.util.Optional;

public abstract class AbstractRepository<T> {

  @PersistenceContext
  protected EntityManager entityManager;

  protected AbstractRepository() {
  }

  public T save(T t) {
    entityManager.persist(t);
    return t;
  }

  public T update(T t) {
    return entityManager.merge(t);
  }

  public Optional<T> findById(Long id) {
    return Optional.ofNullable(entityManager.find(getEntityClass(), id));
  }

  public void delete(Long id) {
    T entity = findById(id).orElseThrow(() -> new WebApplicationException("Employee not found"));
    entityManager.remove(entity);
  }

  public abstract Class<T> getEntityClass();


}
