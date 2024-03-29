package com.gh.web.controller.system;

import com.gh.common.annotation.Log;
import com.gh.common.core.controller.BaseController;
import com.gh.common.core.domain.AjaxResult;
import com.gh.common.core.page.TableDataInfo;
import com.gh.common.enums.BusinessType;
import com.gh.common.utils.poi.ExcelUtil;
import com.gh.framework.util.ShiroUtils;
import com.gh.system.domain.SysConfig;
import com.gh.system.service.ISysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数配置 信息操作处理
 *
 * @author gh
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    private String prefix = "system/config" ;

    @Autowired
    private ISysConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String config() {
        return prefix + "/config" ;
    }

    /**
     * 查询参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "参数管理" , businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        return util.exportExcel(list, "参数数据");
    }

    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "参数管理" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysConfig config) {
        config.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
        mmap.put("config" , configService.selectConfigById(configId));
        return prefix + "/edit" ;
    }

    /**
     * 修改保存参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysConfig config) {
        config.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(configService.deleteConfigByIds(ids));
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(SysConfig config) {
        return configService.checkConfigKeyUnique(config);
    }
}
