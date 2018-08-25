package cn.cxmall.controller;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 导入商品信息到solr索引库
 * @author 王兴毅
 * @date 2018.08.24 17:47
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public CxResult importItemList(){
        CxResult cxResult = searchItemService.importAllItem();
        return cxResult;
    }
}
