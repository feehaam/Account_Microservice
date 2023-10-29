package com.prep.account.iservice;

import com.prep.account.exception.AccountNotFoundException;
import java.util.UUID;

public interface AccountStatusService {
    // User controllable
    void toggleTwoFactor(UUID userId, Boolean status) throws AccountNotFoundException;
    void toggleDeactivation(UUID userId, Boolean status) throws AccountNotFoundException;

    // Controlled by server
    void suspendAccount(UUID userId) throws AccountNotFoundException;
    void addBanHour(UUID userId, Integer hour) throws AccountNotFoundException;
    void toggleLockout(UUID userId, Boolean action) throws AccountNotFoundException;
    void toggleEnabling(UUID userId, Boolean action) throws AccountNotFoundException;
}
