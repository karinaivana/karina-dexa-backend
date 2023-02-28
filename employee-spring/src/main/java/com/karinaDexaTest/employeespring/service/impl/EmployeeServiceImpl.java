package com.karinaDexaTest.employeespring.service.impl;

import com.karinaDexaTest.employeespring.converter.EmployeeConverter;
import com.karinaDexaTest.employeespring.dto.*;
import com.karinaDexaTest.employeespring.model.Employee;
import com.karinaDexaTest.employeespring.repository.EmployeeRepository;
import com.karinaDexaTest.employeespring.service.EmployeeService;
import com.karinaDexaTest.employeespring.utils.EmailUtils;
import com.karinaDexaTest.employeespring.utils.PhoneNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private final PhoneNumberUtils phoneNumberUtils;
    private final EmailUtils emailUtils;

    private final int MAX_TOTAL_EMPLOYEE_LIST_SHOW = 1000;

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeConverter employeeConverter,
            PhoneNumberUtils phoneNumberUtils,
            EmailUtils emailUtils
    ){
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
        this.phoneNumberUtils = phoneNumberUtils;
        this.emailUtils = emailUtils;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        if(dto.getEmail() == null || dto.getEmail().isEmpty() || dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Login employee is failed because email or password is null")
                    .build();
        }

        Employee employee = employeeRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if(employee == null) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Login employee is failed because data doesn't exist")
                    .build();
        }

        return LoginResponseDTO.builder()
                .isSuccess(true)
                .message("Login employee is succeed")
                .employeeDTO(employeeConverter.toDTO(employee))
                .build();
    }

    @Override
    @Transactional
    public UpdateEmployeePersonalDataResponseDTO updateEmployeePersonalData(UpdateEmployeePersonalDataRequestDTO dto) {
        boolean isPhoneNumberValid = phoneNumberUtils.isValidPhoneNumber(dto.getPhoneNumber());
        String normalizePhoneNumber = phoneNumberUtils.normalizePhoneNumber(dto.getPhoneNumber());

        if(!isPhoneNumberValid || normalizePhoneNumber == null) {
            return UpdateEmployeePersonalDataResponseDTO.builder()
                    .message("Updating employee data has failed because the phone is not valid")
                    .isSuccess(false)
                    .build();
        }

        Employee employee = employeeRepository.findExistingEmployee(dto.getEmployeeId());
        if(employee == null) {
            return UpdateEmployeePersonalDataResponseDTO.builder()
                    .message("Updating employee data has failed because the employee does not exist")
                    .isSuccess(false)
                    .build();
        }

        if(dto.getPassword() != null) employee.setPassword(dto.getPassword());
        if(dto.getPhoneNumber() != null) employee.setPhoneNumber(normalizePhoneNumber);
        if(dto.getPhotoLink() != null) employee.setPhotoLink(dto.getPhotoLink());

        employeeRepository.saveAndFlush(employee);

        return UpdateEmployeePersonalDataResponseDTO.builder()
                .isSuccess(true)
                .message("Update employee data is succeed")
                .employeeDTO(employeeConverter.toDTO(employee))
                .build();
    }

    @Override
    public boolean validateEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findExistingEmployee(employeeId);

        if(employee != null) return true;
        else return false;
    }

    @Override
    @Transactional
    public CreateOrUpdateEmployeeByAdminResponseDTO createOrUpdateEmployeeByAdmin(CreateOrUpdateEmployeeByAdminRequestDTO dto) {
        boolean isPhoneNumberValid = phoneNumberUtils.isValidPhoneNumber(dto.getPhoneNumber());
        String normalizePhoneNumber = phoneNumberUtils.normalizePhoneNumber(dto.getPhoneNumber());
        if(!isPhoneNumberValid || normalizePhoneNumber == null) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating new employee data has failed because the phone number is not valid")
                    .isSuccess(false)
                    .build();
        }

        boolean isEmailValid = emailUtils.isValidEmail(dto.getEmail());
        if(!isEmailValid) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating employee data has failed because the email is not valid")
                    .isSuccess(false)
                    .build();
        }

        Employee existingEmployeeByEmail = null;

        if(dto.getId() == null) existingEmployeeByEmail = employeeRepository.findExistingEmployeeByEmail(dto.getEmail());
        else if(dto.getId() != null) existingEmployeeByEmail = employeeRepository.findExistingEmployeeByEmailAndId(dto.getId(), dto.getEmail());

        if(existingEmployeeByEmail != null) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating new employee has failed because email already exist")
                    .isSuccess(false)
                    .build();
        }

        Employee employeeSaved = null;

        if(dto.getId() != null) {
            employeeSaved = employeeRepository.findExistingEmployee(dto.getId());
        } else {
            employeeSaved = new Employee();
        }

        employeeSaved.setName(dto.getName());
        employeeSaved.setEmail(dto.getEmail());
        employeeSaved.setPassword(dto.getPassword());
        employeeSaved.setRole(dto.getRole());
        employeeSaved.setPhoneNumber(normalizePhoneNumber);
        employeeSaved.setPhotoLink(dto.getPhotoLink());

        employeeRepository.save(employeeSaved);

        return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                .isSuccess(true)
                .message("A employee has been successfully created or updated")
                .employeeDTO(employeeConverter.toDTO(employeeSaved))
                .build();
    }

    @Override
    public GetAllEmployeeDataResponseDTO getAllEmployeeData() {
        Page<Employee> employeeList = employeeRepository.getAllWithPageable(Pageable.ofSize(MAX_TOTAL_EMPLOYEE_LIST_SHOW));

        return GetAllEmployeeDataResponseDTO.builder()
                .isSuccess(true)
                .employeeDTOList(employeeConverter.convertToDtoList(employeeList))
                .build();
    }
}
