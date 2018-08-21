package cn.cxmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 根据输入的页面url返回逻辑视图
 * @author 王兴毅
 * @date 2018.08.16 16:39
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/views/{a:\\w+}/{page}")
    public String showPage2(@PathVariable String a, @PathVariable String page){
        return "views/" + a + "/" + page;
    }

    @RequestMapping("/views/{a:\\w+}/{b:\\w+}/{page}")
    public String showPage3(@PathVariable String a, @PathVariable String b, @PathVariable String page){
        return "views/" + a + "/" + b + "/" + page;
    }
}
