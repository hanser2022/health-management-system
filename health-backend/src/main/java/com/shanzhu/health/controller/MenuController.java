package com.shanzhu.health.controller;

import com.shanzhu.health.config.Unification;
import com.shanzhu.health.entity.Menu;
import com.shanzhu.health.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shanzhu
 * @since 2026-02-20
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 查询所有菜单数据
     *
     * @return 返回Unification对象，包含查询到的所有菜单数据
     */
    @GetMapping
    public Unification<List<Menu>> getAllMenu() {
        List<Menu> menuList = menuService.getAllMenu();
        return Unification.success(menuList);
    }
}

