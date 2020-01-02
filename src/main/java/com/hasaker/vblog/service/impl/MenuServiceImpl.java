package com.hasaker.vblog.service.impl;

import com.hasaker.vblog.base.impl.BaseServiceImpl;
import com.hasaker.vblog.entity.Menu;
import com.hasaker.vblog.mapper.MenuMapper;
import com.hasaker.vblog.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * @package com.hasaker.vblog.service.impl
 * @author 余天堂
 * @create 2020/1/2 20:09
 * @description MenuServiceImpl
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {
}
