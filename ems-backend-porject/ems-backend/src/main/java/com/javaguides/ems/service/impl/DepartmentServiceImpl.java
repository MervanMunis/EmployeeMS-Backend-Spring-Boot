package com.javaguides.ems.service.impl;

import com.javaguides.ems.dto.DepartmentDto;
import com.javaguides.ems.entity.Department;
import com.javaguides.ems.exception.ResourceNotFoundException;
import com.javaguides.ems.mapper.DepartmentMapper;
import com.javaguides.ems.repository.IDepartmentRepository;
import com.javaguides.ems.service.IDepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    // First Lets take control of database (İmplementing CRUD operations)
    IDepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        // First we need to map to the Department from DepartmentDto.
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        // Then we need to add and save it into database. save() command will return the Department object.
        Department savedDepartment = departmentRepository.save(department);

        // After saving and we map the department object to department dto object.
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        // First we get all the departments from the database.
        List<Department> departments = departmentRepository.findAll();
        // Then we map the department objects to department dto objects. After that we need to return as a list
        return departments.stream().map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        // First we find the department with ID number. If it is not exist we throw an exception.
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department does not exist with the given id: " + departmentId)
        );
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {
        // First we find the department with ID number. If it is not exist we throw an exception.
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with the given id " + departmentId)
                );
        // If it exists then we update the department database by entering the updateDepartment object to Department object.
        department.setDepartmentName(updatedDepartment.getDepartmentName());
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        // After that we need to save the changes and return the department object.
        Department updatedDepartmentObj = departmentRepository.save(department);
        // Then we need to map the department object to department dto object
        return DepartmentMapper.mapToDepartmentDto(updatedDepartmentObj);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        // First we find the department with ID number. If it is not exist we throw an exception.
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with the given id " + departmentId)
                );
        // If the department exists we can delete the department.
        departmentRepository.delete(department);
    }
}
