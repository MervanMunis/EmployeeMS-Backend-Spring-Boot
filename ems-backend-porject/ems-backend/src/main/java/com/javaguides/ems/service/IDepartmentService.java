package com.javaguides.ems.service;

import com.javaguides.ems.dto.DepartmentDto;
import java.util.List;


public interface IDepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto); // We will add a department all inf are needed
    DepartmentDto getDepartmentById(Long departmentId); // We need the department so we need Department ID
    List<DepartmentDto> getAllDepartments(); // We get all departments so no parameters needed
    DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment); // To update we need ID and all inf of department
    // No inf needed therefore there is nothing to return after deleting.
    void deleteDepartment(Long departmentId); // We will remove the department so just id is enough

}
