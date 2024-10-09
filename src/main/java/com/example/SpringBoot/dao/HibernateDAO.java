package com.example.SpringBoot.dao;
import com.example.SpringBoot.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class HibernateDAO implements DAO {

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
        entityManager.close();
    }

    @Override
    @Transactional
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    @Transactional
    public void removeUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }
}
