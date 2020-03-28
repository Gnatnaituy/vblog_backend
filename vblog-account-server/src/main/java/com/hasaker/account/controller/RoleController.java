package com.hasaker.account.controller;

import com.hasaker.account.service.RoleService;
import com.hasaker.account.vo.request.RequestRoleVo;
import com.hasaker.common.vo.Ajax;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.account.controller
 * @author 余天堂
 * @create 2020/3/26 13:32
 * @description RoleController
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Create a role")
    @PostMapping(value = "/create")
    public Ajax createRole(@RequestBody RequestRoleVo roleVo) {
        roleService.create(roleVo.getRoleName());

        return Ajax.success();
    }

    @ApiOperation(value = "Delete a role")
    @PostMapping(value = "/delete")
    public Ajax deleteRole(@RequestBody RequestRoleVo roleVo) {
        roleService.delete(roleVo.getRoleName());

        return Ajax.success();
    }
}
