package com.lpf.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpf.www.entities.Employee;
import com.lpf.www.entities.Result;
import com.lpf.www.mapper.EmployeeMapper;
import com.lpf.www.service.EmployeeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther 飞
 * @create
 */
@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public void addEmployee(Map map) {
        employeeMapper.addEmployee(map);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeMapper.deleteEmployee(id);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            employeeMapper.updateEmployee(employee);
            return true;
        } catch (Exception e) {
            log.info("更新错误");
        }
        return false;

    }

    @Override
    public Employee searchEmployee(int id) {
        Employee employee = employeeMapper.searchEmployee(id);
        return employee;
    }

    @Override
    public Result searchEmployeeByPage(Map map) {
        PageHelper.startPage((int) map.get("currentPage"), (int) map.get("size"));
        List<Employee> employees = employeeMapper.searchEmployeeByPage(map);
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employees);
        long total = employeePageInfo.getTotal();
        List<Employee> list = employeePageInfo.getList();
        Result ok = Result.ok(list, total);
        return ok;
    }
}
