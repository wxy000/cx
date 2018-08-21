package cn.cxmall.controller;

import cn.cxmall.common.pojo.ItemInfo;
import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.pojo.TbItem;
import cn.cxmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品controller
 * @author 王兴毅
 * @date 2018.08.16 15:45
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据id查找商品信息
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * 分页返回商品列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public DataGridResult getItemList(Integer page, Integer rows){
        DataGridResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    /**
     * 新增商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public CxResult saveItem(TbItem item, String desc){
        CxResult result = itemService.addItem(item, desc);
        return result;
    }

    /**
     * 商品详细信息
     * @param itemId
     * @return
     */
    @RequestMapping("/item/details/{itemId}")
    @ResponseBody
    public ItemInfo itemDetails(@PathVariable long itemId){
        ItemInfo itemInfo = itemService.getItemInfoById(itemId);
        return itemInfo;
    }

    /**
     * 更新商品状态
     * @param itemId
     * @param status
     * @return
     */
    @RequestMapping("/item/updateStatus")
    @ResponseBody
    public CxResult updateItemStatusById(long itemId, short status){
        CxResult cxResult = itemService.updateItemStatusById(itemId, status);
        return cxResult;
    }

    /**
     * 更新商品信息
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    @ResponseBody
    public CxResult updateItemById(TbItem item, String desc){
        CxResult cxResult = itemService.updateItemById(item, desc);
        return cxResult;
    }
}
