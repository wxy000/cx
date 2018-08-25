package cn.cxmall.item.controller;

import cn.cxmall.common.pojo.ItemInfo;
import cn.cxmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情页面展示
 * @author 王兴毅
 * @date 2018.08.25 15:44
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //取全部商品信息
        ItemInfo itemInfo = itemService.getItemInfoById(itemId);
        //把信息传递给页面
        model.addAttribute("item", itemInfo);
        return "item";
    }
}
