package com.lpf.www.controller;

import com.lpf.www.entities.Employee;
import com.lpf.www.entities.Result;
import com.lpf.www.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther 飞
 * @create
 */
@RestController
@Slf4j
@Tag(name = "EmployeeController", description = "员工操作类")
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;


    @PostMapping("/addEmployee")
    @Operation(summary = "新增员工",
            description = "新增员工",
            parameters = {
                    @Parameter(name = "employee", description = "员工信息")
            }
    )
    public Result addEmployee(@RequestBody Map map) {
        employeeService.addEmployee(map);
        return Result.ok();
    }

    @GetMapping("/deleteEmployee")
    @Operation(summary = "删除员工", description = "删除员工", parameters = {
            @Parameter(name = "id", description = "删除员工id")
    })
    public Result deleteEmployee(@RequestParam int id) {
        employeeService.deleteEmployee(id);
        return Result.ok();
    }

    @PostMapping("/updateEmployee")
    @Operation(summary = "修改员工", description = "修改员工", parameters = {
            @Parameter(name = "employee", description = "员工信息")
    })
    public Result updateEmployee(@RequestBody Employee employee) {

        boolean b = employeeService.updateEmployee(employee);
        return Result.ok(b);
    }

    @GetMapping("/searchEmployeeById/{id}")
    @Operation(summary = "查询单个员工", description = "查询单个员工", parameters = {
            @Parameter(name = "id", description = "查询的员工单个id"),
    },
            responses = {
                    @ApiResponse(description = "返回单个员工信息", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
            }
    )
    public Result searchEmployee(@PathVariable int id) {
        Employee employee = employeeService.searchEmployee(id);
        return Result.ok(employee);
    }

    @PostMapping("/searchEmployeeByPage")
    @Operation(summary = "查询员工列表", description = "查询员工列表", parameters = {
            @Parameter(name = "map", description = "条件查询字段"),
    },
            responses = {
                    @ApiResponse(description = "返回员工列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
            })
    public Result searchEmployee(@RequestBody Map map) {
        Result result = employeeService.searchEmployeeByPage(map);
        return result;
    }
}
