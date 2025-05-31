package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.util.JPAUtil;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public abstract class AbstractRepository<T, ID> {
    private static final Logger logger = Logger.getLogger(AbstractRepository.class.getName());

    static {
        try {
            Logger rootLogger = Logger.getLogger("");
            Handler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.INFO);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to configure file logging", e);
        }
    }

    private final Class<T> entityClass;

    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long start = System.nanoTime();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logException("Create failed", e);
            throw new RuntimeException("Failed to create entity", e);
        } finally {
            em.close();
            logExecution("create()", start);
        }
    }

    public T findById(ID id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        long start = System.nanoTime();
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            logException("Find by ID failed", e);
            return null;
        } finally {
            em.close();
            logExecution("findById()", start);
        }
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        long start = System.nanoTime();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } catch (Exception e) {
            logException("Find all failed", e);
            return List.of();
        } finally {
            em.close();
            logExecution("findAll()", start);
        }
    }

    public List<T> findByName(String namePattern) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        long start = System.nanoTime();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.name LIKE :name", entityClass)
                    .setParameter("name", namePattern)
                    .getResultList();
        } catch (Exception e) {
            logException("Find by name failed", e);
            return List.of();
        } finally {
            em.close();
            logExecution("findByName()", start);
        }
    }

    public T update(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long start = System.nanoTime();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logException("Update failed", e);
            throw new RuntimeException("Failed to update entity", e);
        } finally {
            em.close();
            logExecution("update()", start);
        }
    }

    public void delete(ID id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long start = System.nanoTime();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logException("Delete failed", e);
            throw new RuntimeException("Failed to delete entity", e);
        } finally {
            em.close();
            logExecution("delete()", start);
        }
    }

    protected void logExecution(String operation, long startTime) {
        long duration = System.nanoTime() - startTime;
        logger.info(operation + " executed in " + (duration / 1_000_000.0) + " ms");
    }

    protected void logException(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}