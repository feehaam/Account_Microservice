//package com.prep.account.service;
//
//import com.prep.account.exception.AccountNotFoundException;
//import com.prep.account.exception.DuplicateEntityException;
//import com.prep.account.iservice.LoggedInDevicesService;
//import com.prep.account.model.LoggedDeviceDTO;
//import com.prep.account.entity.Account;
//import com.prep.account.repository.AccountRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service @RequiredArgsConstructor
//public class LoggedInDevicesServiceImpl implements LoggedInDevicesService {
//
//    private final AccountRepository accountRepository;
//
//    @Override
//    public List<LoggedDeviceDTO> getLoggedInDevices(UUID userId) throws AccountNotFoundException {
//        // Check if the account exists
//        Account account = accountRepository.findByUserId(userId)
//                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));
//
//        // Retrieve the logged-in devices for the account
//        List<LoggedDevice> loggedDevices = loggedDeviceRepository.findByAccount(account);
//
//        // Convert and return the DTOs
//        // You can map the entities to DTOs and return them
//        // Example: return loggedDevices.stream().map(LoggedDeviceDTO::new).collect(Collectors.toList());
//
//        return null; // Replace with the actual logic
//    }
//
//    @Override
//    public void insertLoggedDevice(UUID userId, LoggedDeviceDTO loggedDeviceDTO)
//            throws AccountNotFoundException, DuplicateEntityException {
//        // Check if the account exists
//        Account account = accountRepository.findByUserId(userId)
//                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));
//
//        // Check if the device is already logged in
//        if (loggedDeviceRepository.findByDeviceIdAndAccount(loggedDeviceDTO.getDeviceId(), account).isPresent()) {
//            throw new DuplicateEntityException("Logged Device", "device ID", loggedDeviceDTO.getDeviceId());
//        }
//
//        // Create a new LoggedDevice entity and save it to the database
//        LoggedDevice loggedDevice = new LoggedDevice();
//        loggedDevice.setDeviceId(loggedDeviceDTO.getDeviceId());
//        loggedDevice.setDeviceName(loggedDeviceDTO.getDeviceName());
//        loggedDevice.setAccount(account);
//
//        loggedDeviceRepository.save(loggedDevice);
//    }
//
//    @Override
//    public void removeLoggedDevice(UUID userId, UUID deviceId) throws AccountNotFoundException {
//        // Check if the account exists
//        Account account = accountRepository.findByUserId(userId)
//                .orElseThrow(() -> new AccountNotFoundException("user ID " + userId));
//
//        // Check if the device exists and belongs to the account
//        LoggedDevice device = loggedDeviceRepository.findByDeviceIdAndAccount(deviceId, account)
//                .orElseThrow(() -> new AccountNotFoundException("device ID " + deviceId));
//
//        // Remove the device
//        loggedDeviceRepository.delete(device);
//    }
//}
