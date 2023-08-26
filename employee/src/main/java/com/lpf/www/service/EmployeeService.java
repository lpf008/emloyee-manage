package com.lpf.www.service;

import com.lpf.www.entities.Employee;
import com.lpf.www.entities.Result;

import java.util.Map;

/**
 * @auther zzyy
 * @create 2023-06-28 16:08
 */
public interface EmployeeService {
    void addEmployee(Map map);

    void deleteEmployee(int id);

    boolean updateEmployee(Employee employee);

    Employee searchEmployee(int id);

    Result searchEmployeeByPage(Map map);
}
