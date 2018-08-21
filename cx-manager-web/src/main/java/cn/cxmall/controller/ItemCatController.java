package cn.cxmall.controller;

import cn.cxmall.common.result.EasyUITreeNode;
import cn.cxmall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类信息controller
 * @author 王兴毅
 * @date 2018.08.19 14:08
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {

        List<EasyUITreeNode> list = itemCatService.getCatList(parentId);
        return list;
    }

}
