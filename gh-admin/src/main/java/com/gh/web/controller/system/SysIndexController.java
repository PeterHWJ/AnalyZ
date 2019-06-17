package com.gh.web.controller.system;

import com.gh.common.config.Global;
import com.gh.common.core.controller.BaseController;
import com.gh.framework.util.ShiroUtils;
import com.gh.system.domain.SysMenu;
import com.gh.system.domain.SysUser;
import com.gh.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author gh
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus" , menus);
        mmap.put("user" , user);
        mmap.put("copyrightYear" , Global.getCopyrightYear());
        mmap.put("demoEnabled" , Global.isDemoEnabled());
        return "index" ;
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version" , Global.getVersion());
        return "main" ;
    }
}
