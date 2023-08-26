package com.lpf.www.mapper;

import com.lpf.www.entities.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description TODO
 * @auhor 飞
 * @date 2023/8/21 18:18
 */

public interface EmployeeMapper extends Mapper<Employee> {
    void addEmployee(Map map);

    void deleteEmployee(int id);

    void updateEmployee(Employee employee);

    Employee searchEmployee(int id);

    List<Employee> searchEmployeeByPage(Map map);
}
