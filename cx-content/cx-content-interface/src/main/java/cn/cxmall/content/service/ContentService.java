package cn.cxmall.content.service;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.pojo.TbContent;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.22 22:12
 */
public interface ContentService {

    /**
     * 根据categoryId查询内容，带分页条件
     * @param categoryId
     * @return
     */
    DataGridResult getContentList(long categoryId, int page, int rows);

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    CxResult saveContent(TbContent tbContent);

    /**
     * 更新内容
     * @param tbContent
     * @return
     */
    CxResult updateContent(TbContent tbContent);

    /**
     * 根据id删除内容
     * @param id
     * @return
     */
    CxResult deleteContentById(long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    CxResult batchRemoveContent(long[] ids);

    /**
     * 根据categoryId查询内容
     * @param categoryId
     * @return
     */
    List<TbContent> getContentList(long categoryId);
}
