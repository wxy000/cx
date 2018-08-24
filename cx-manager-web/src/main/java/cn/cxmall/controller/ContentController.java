package cn.cxmall.controller;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.common.utils.JsonUtils;
import cn.cxmall.content.service.ContentService;
import cn.cxmall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容Controller
 * @author 王兴毅
 * @date 2018.08.22 21:44
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据id查询内容，带分页条件
     * @param id
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/list")
    @ResponseBody
    public DataGridResult getContentList(@RequestParam("id") long id, @RequestParam("page") Integer page, @RequestParam("limit") Integer rows){
        DataGridResult contentList = contentService.getContentList(id, page, rows);
        //System.out.println(JsonUtils.objectToJson(contentList));
        return contentList;
    }

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public CxResult saveContent(TbContent tbContent){
        CxResult cxResult = contentService.saveContent(tbContent);
        return cxResult;
    }

    /**
     * 更新内容
     * @param tbContent
     * @return
     */
    @RequestMapping("/content/update")
    @ResponseBody
    public CxResult updateContent(TbContent tbContent){
        CxResult cxResult = contentService.updateContent(tbContent);
        return cxResult;
    }

    /**
     * 根据id删除内容
     * @param id
     * @return
     */
    @RequestMapping("/content/del")
    @ResponseBody
    public CxResult deleteContentById(@RequestParam("id") long id){
        CxResult cxResult = contentService.deleteContentById(id);
        return cxResult;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/content/remove")
    @ResponseBody
    public CxResult batchRemove(long[] ids){
        CxResult cxResult = contentService.batchRemoveContent(ids);
        return cxResult;
    }
}
