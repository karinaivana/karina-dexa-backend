package com.karinaDexaTest.employeespring.service.impl;

import com.karinaDexaTest.employeespring.converter.EmployeeConverter;
import com.karinaDexaTest.employeespring.dto.*;
import com.karinaDexaTest.employeespring.model.Employee;
import com.karinaDexaTest.employeespring.model.Role;
import com.karinaDexaTest.employeespring.repository.EmployeeRepository;
import com.karinaDexaTest.employeespring.repository.RoleRepository;
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

    private final RoleRepository roleRepository;

    private final int MAX_TOTAL_EMPLOYEE_LIST_SHOW = 1000;

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeConverter employeeConverter,
            PhoneNumberUtils phoneNumberUtils,
            EmailUtils emailUtils,
            RoleRepository roleRepository
    ){
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
        this.phoneNumberUtils = phoneNumberUtils;
        this.emailUtils = emailUtils;
        this.roleRepository = roleRepository;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        if(dto.getEmail() == null || dto.getEmail().isEmpty() || dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Employee login has failed because the email or password is null")
                    .build();
        }

        Employee employee = employeeRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if(employee == null) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Employee login has failed because data doesn't exist")
                    .build();
        }

        EmployeeDTO employeeDTO = employeeConverter.toDTO(employee);

        return LoginResponseDTO.builder()
                .isSuccess(true)
                .message("Employee login was succeed")
                .employeeDTO(employeeDTO)
                .build();
    }

    @Override
    @Transactional
    public UpdateEmployeePersonalDataResponseDTO updateEmployeePersonalData(UpdateEmployeePersonalDataRequestDTO dto) {
        boolean isPhoneNumberValid = phoneNumberUtils.isValidPhoneNumber(dto.getPhoneNumber());
        String normalizePhoneNumber = phoneNumberUtils.normalizePhoneNumber(dto.getPhoneNumber());

        //validate phone number
        if(!isPhoneNumberValid || normalizePhoneNumber == null) {
            return UpdateEmployeePersonalDataResponseDTO.builder()
                    .message("The update of employee data has failed due to an invalid phone number.")
                    .isSuccess(false)
                    .build();
        }

        Employee employee = employeeRepository.findExistingEmployee(dto.getEmployeeId());
        if(employee == null) {
            return UpdateEmployeePersonalDataResponseDTO.builder()
                    .message("The update of employee data has failed because the employee does not exist.")
                    .isSuccess(false)
                    .build();
        }

        if(dto.getPassword() != null) employee.setPassword(dto.getPassword());
        if(dto.getPhoneNumber() != null) employee.setPhoneNumber(normalizePhoneNumber);
        if(dto.getPhotoLink() != null) employee.setPhotoLink(dto.getPhotoLink());

        employeeRepository.saveAndFlush(employee);

        return UpdateEmployeePersonalDataResponseDTO.builder()
                .isSuccess(true)
                .message("The update of employee data has succeed")
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
        //validate phone number value
        boolean isPhoneNumberValid = phoneNumberUtils.isValidPhoneNumber(dto.getPhoneNumber());
        String normalizePhoneNumber = phoneNumberUtils.normalizePhoneNumber(dto.getPhoneNumber());
        if(!isPhoneNumberValid || normalizePhoneNumber == null) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating new employee data has failed because the phone number is not valid")
                    .isSuccess(false)
                    .build();
        }

        //validate email value
        boolean isEmailValid = emailUtils.isValidEmail(dto.getEmail());
        if(!isEmailValid) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating employee data has failed because the email is not valid")
                    .isSuccess(false)
                    .build();
        }

        //Validate email is unique
        Employee existingEmployeeByEmail = null;

        if(dto.getId() == null) existingEmployeeByEmail = employeeRepository.findExistingEmployeeByEmail(dto.getEmail());
        else if(dto.getId() != null) existingEmployeeByEmail = employeeRepository.findExistingEmployeeByEmailAndId(dto.getId(), dto.getEmail());

        if(existingEmployeeByEmail != null) {
            return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                    .message("Creating/Updating the new employee has failed because the email already exists.")
                    .isSuccess(false)
                    .build();
        }

        //Check it create or update employee data
        Employee employeeSaved = null;
        if(dto.getId() != null) {
            employeeSaved = employeeRepository.findExistingEmployee(dto.getId());
        } else {
            employeeSaved = new Employee();
        }

        if(dto.getRole() != null) {
            Role newRole = roleRepository.getRoleByIdString(dto.getRole());

            if(newRole == null) {
                return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                        .message("Creating/Updating the new employee has failed because rulesnot valid")
                        .isSuccess(false)
                        .build();
            }

            employeeSaved.setRole(newRole);
        }

        employeeSaved.setName(dto.getName());
        employeeSaved.setEmail(dto.getEmail());
        employeeSaved.setPassword(dto.getPassword());
        employeeSaved.setPhoneNumber(normalizePhoneNumber);
        employeeSaved.setPhotoLink(dto.getPhotoLink());

        employeeRepository.save(employeeSaved);

        return CreateOrUpdateEmployeeByAdminResponseDTO.builder()
                .isSuccess(true)
                .message("A employee has been successfully created or updated")
                .employeeDTO(employeeConverter.toDTO(employeeSaved))
                .build();
    }

    //Used for admins to display all employee data before editing.
    @Override
    public GetAllEmployeeDataResponseDTO getAllEmployeeData() {
        Page<Employee> employeeList = employeeRepository.getAllWithPageable(Pageable.ofSize(MAX_TOTAL_EMPLOYEE_LIST_SHOW));

        return GetAllEmployeeDataResponseDTO.builder()
                .isSuccess(true)
                .employeeDTOList(employeeConverter.convertToDtoList(employeeList))
                .build();
    }
}
