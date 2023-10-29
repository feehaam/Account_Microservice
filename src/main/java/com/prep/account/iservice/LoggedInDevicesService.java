package com.prep.account.iservice;

import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.DuplicateEntityException;
import com.prep.account.model.LoggedDeviceDTO;

import java.util.List;
import java.util.UUID;

public interface LoggedInDevicesService {
    List<LoggedDeviceDTO> getLoggedInDevices(UUID userId) throws AccountNotFoundException;
    void insertLoggedDevice(UUID userId, LoggedDeviceDTO loggedDeviceDTO)
            throws AccountNotFoundException, DuplicateEntityException;
    void removeLoggedDevice(UUID userId, UUID deviceId) throws AccountNotFoundException;
}
