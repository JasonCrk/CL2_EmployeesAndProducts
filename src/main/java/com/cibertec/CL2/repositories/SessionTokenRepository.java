package com.cibertec.CL2.repositories;

import com.cibertec.CL2.models.SessionTokenEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionTokenEntity, Integer> {
    @Query(value = """
            SELECT t FROM SessionTokenEntity t INNER JOIN EmployeeEntity e\s
            ON t.employee.id = e.id\s
            WHERE e.id = :id and (t.expired = false and t.revoked = false)\s
            """)
    List<SessionTokenEntity> findAllValidSessionTokenByUser(Integer id);

    Optional<SessionTokenEntity> findByToken(String token);
}
