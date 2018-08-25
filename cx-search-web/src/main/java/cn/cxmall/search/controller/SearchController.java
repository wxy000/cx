package cn.cxmall.search.controller;

import cn.cxmall.common.pojo.SearchResult;
import cn.cxmall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 王兴毅
 * @date 2018.08.24 19:41
 */
@Controller
public class SearchController {

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/commons/{page}")
    public String showPage(@PathVariable String page){
        return "commons/"+page;
    }

    @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        //查询商品列表
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        return "search";
    }

}
