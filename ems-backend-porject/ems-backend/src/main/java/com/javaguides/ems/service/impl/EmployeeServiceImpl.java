package com.javaguides.ems.service.impl;

import com.javaguides.ems.dto.EmployeeDto;
import com.javaguides.ems.entity.Department;
import com.javaguides.ems.entity.Employee;
import com.javaguides.ems.exception.ResourceNotFoundException;
import com.javaguides.ems.mapper.EmployeeMapper;
import com.javaguides.ems.repository.IDepartmentRepository;
import com.javaguides.ems.repository.IEmployeeRepository;
import com.javaguides.ems.service.IEmployeeService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeRepository employeeRepository;

    private IDepartmentRepository departmentRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() ->
                new ResourceNotFoundException("Department is not exists with id: " + employeeDto.getDepartmentId()));

        employee.setDepartment(department);
        Employee savedEmployee =  employeeRepository.save(employee); // it returns the saved Employee object
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) { // when we enter a ıd if there is none then we need to throw exception
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee is not exist with the given id " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees  = employeeRepository.findAll();
        return  employees.stream().map(EmployeeMapper::mapToEmployeeDto) // Learn this
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id: " + employeeId)
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department is not exists with id: " + updatedEmployee.getDepartmentId()));

        employee.setDepartment(department);

        Employee updateEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updateEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with the given id " + employeeId)
        );
        employeeRepository.deleteById(employee.getId());
    }
}
