package com.smart.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smart.entity.SysMenu;
import com.smart.service.SysMenuService;
import com.smart.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "文件操作管理",tags = "文件操作管理")
@RequestMapping("/fileRecord")
public class FileRecordController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("exportExcel")
    @ApiOperation(value = "导出数据",tags = "导出数据")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        List<SysMenu> list = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().isNull(SysMenu::getDeFlag));
        ExcelUtils.createExcel2(request,response,SysMenu.class,list,"菜单.xls");
    }

    @GetMapping("exportExcelModule")
    @ApiOperation(value = "导出模板",tags = "导出模板")
    public void exportExcelModule(HttpServletRequest request, HttpServletResponse response){
        List<SysMenu> list= new ArrayList<>();
        ExcelUtils.createExcel(request,response,SysMenu.class,list,"菜单模板.xls");
    }

    @PostMapping("/importExcel")
    @ApiOperation(value = "导入数据",tags = "导入数据")
    public void importExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        List<SysMenu> list = (List<SysMenu>) ExcelUtils.importExcel2(multipartFile, SysMenu.class);
        System.out.println(list);
    }
}
