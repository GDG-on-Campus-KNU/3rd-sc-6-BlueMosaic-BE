package com.gdsc.knu.util;

import com.gdsc.knu.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ... 다른 import문 ...

@Service
public class DummyDataCreator {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void createDummyData() {
        entityManager.createNativeQuery(
                        "INSERT INTO user (id, nickname, name, email, is_Login, region, deleted) " +
                                "VALUES (100, 'Friend', 'Best Friend', 'friend@example.com', false, 'kr', false)")
                .executeUpdate();
    }
}
