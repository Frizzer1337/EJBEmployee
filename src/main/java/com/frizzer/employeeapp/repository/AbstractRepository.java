package com.frizzer.employeeapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractRepository<T> {

  @PersistenceContext
  private EntityManager entityManager;

  protected AbstractRepository() {}
  public T save(T t) {
    entityManager.persist(t);
    return t;
  }
  public T update(T t) {
    return entityManager.merge(t);
  }

  public T findById(Long id) {
    return entityManager.find(getEntityClass(), id);
  }

  public boolean delete(Long id) {
    T entity = findById(id);
    if (entity == null) {
      return false;
    } else {
      entityManager.remove(entity);
      return true;
    }
  }
  public abstract Class<T> getEntityClass();


}
