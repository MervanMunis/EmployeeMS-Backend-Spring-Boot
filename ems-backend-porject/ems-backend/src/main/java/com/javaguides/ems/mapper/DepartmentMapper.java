package com.javaguides.ems.mapper;

import com.javaguides.ems.dto.DepartmentDto;
import com.javaguides.ems.entity.Department;

public class DepartmentMapper {

    // convert department jpa entity into department dto
    public static DepartmentDto mapToDepartmentDto(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription()
        );
    }

    public static Department mapToDepartment(DepartmentDto departmentDto) {
        return new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription()
        );
    }
}
