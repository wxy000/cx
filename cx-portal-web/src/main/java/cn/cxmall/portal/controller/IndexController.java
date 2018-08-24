package cn.cxmall.portal.controller;

import cn.cxmall.common.utils.JsonUtils;
import cn.cxmall.content.service.ContentService;
import cn.cxmall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 显示主页
 * @author 王兴毅
 * @date 2018.08.21 17:58
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @Value("${CONTENT_BIGAD_ID}")
    private long CONTENT_BIGAD_ID;

    @RequestMapping("/commons/{page}")
    public String showPage(@PathVariable String page){
        return "commons/"+page;
    }

    @RequestMapping({"/", "/index"})
    public String getContentList(Model model){
        //大广告位——轮播图
        List<TbContent> contentList = contentService.getContentList(CONTENT_BIGAD_ID);
        model.addAttribute("bigadList", contentList);
        //System.out.println("测试");
        //System.out.println(JsonUtils.objectToJson(contentList));
        return "index";
    }
}
