package cn.cxmall.controller;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.utils.TreeNode;
import cn.cxmall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 内容类别Controller
 * @author 王兴毅
 * @date 2018.08.22 13:26
 */
@Controller
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 全部的内容类别
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<TreeNode> getContentCatList(){
        List<TreeNode> contentCatList = contentCategoryService.getContentCatList();
        return contentCatList;
    }

    /**
     * 新增内容类别
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/content/category/add")
    @ResponseBody
    public CxResult addContentCat(@RequestParam("id") long id, @RequestParam("name") String name){
        try {
            name = new String(name.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CxResult cxResult = contentCategoryService.addContentCat(id, name);
        return cxResult;
    }

    /**
     * 更新内容类别
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public CxResult updateContentCat(@RequestParam("id") long id, @RequestParam("name") String name){
        try {
            name = new String(name.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CxResult cxResult = contentCategoryService.updateContentCat(id, name);
        return cxResult;
    }

    /**
     * 删除栏目
     * @param id
     * @return
     */
    @RequestMapping("/content/category/del")
    @ResponseBody
    public CxResult deleteContentCat(@RequestParam("id") long id){
        CxResult cxResult = contentCategoryService.deleteContentCat(id);
        return cxResult;
    }
}
