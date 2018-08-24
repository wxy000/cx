package cn.cxmall.content.service;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.utils.TreeNode;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.22 12:24
 */
public interface ContentCategoryService {

    /**
     * 获取内容分类列表
     * @return
     */
    List<TreeNode> getContentCatList();

    /**
     * 新增内容类别
     * @param id
     * @param name
     * @return
     */
    CxResult addContentCat(long id, String name);

    /**
     * 更新栏目
     * @param id
     * @param name
     * @return
     */
    CxResult updateContentCat(long id, String name);

    /**
     * 删除栏目
     * @param id
     * @return
     */
    CxResult deleteContentCat(long id);
}
