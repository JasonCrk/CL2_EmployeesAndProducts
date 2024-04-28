package com.cibertec.CL2.services.sessionToken;

import com.cibertec.CL2.models.EmployeeEntity;
import com.cibertec.CL2.models.SessionTokenEntity;
import com.cibertec.CL2.repositories.SessionTokenRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionTokenServiceImpl implements SessionTokenService {

    private final SessionTokenRepository sessionTokenRepository;

    @Override
    @Transactional
    public void saveEmployeeToken(EmployeeEntity employee, String accessToken) {
        SessionTokenEntity token = SessionTokenEntity.builder()
                .employee(employee)
                .token(accessToken)
                .build();

        this.sessionTokenRepository.save(token);
    }

    @Override
    @Transactional
    public void revokeAllEmployeeTokens(EmployeeEntity employee) {
        var validEmployeeSessionTokens = this.sessionTokenRepository
                .findAllValidSessionTokenByUser(employee.getId());

        if (validEmployeeSessionTokens.isEmpty()) return;

        validEmployeeSessionTokens.forEach(sessionToken -> {
            sessionToken.setExpired(true);
            sessionToken.setRevoked(true);
        });

        this.sessionTokenRepository.saveAll(validEmployeeSessionTokens);
    }
}
