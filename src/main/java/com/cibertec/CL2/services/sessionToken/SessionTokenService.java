package com.cibertec.CL2.services.sessionToken;

import com.cibertec.CL2.models.EmployeeEntity;

public interface SessionTokenService {
    void saveEmployeeToken(EmployeeEntity employee, String accessToken);
    void revokeAllEmployeeTokens(EmployeeEntity employee);
}
