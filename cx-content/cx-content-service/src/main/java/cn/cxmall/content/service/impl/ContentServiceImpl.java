package cn.cxmall.content.service.impl;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.content.service.ContentService;
import cn.cxmall.mapper.TbContentMapper;
import cn.cxmall.pojo.TbContent;
import cn.cxmall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.22 22:15
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 根据categoryid查询内容
     * @param categoryId
     * @return
     */
    @Override
    public DataGridResult getContentList(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
        //取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);

        //创建返回结果对象
        DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contentList);

        return result;
    }

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    @Override
    public CxResult saveContent(TbContent tbContent) {
        //补全信息
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        contentMapper.insert(tbContent);
        return CxResult.ok();
    }

    /**
     * 更新内容
     * @param tbContent
     * @return
     */
    @Override
    public CxResult updateContent(TbContent tbContent) {
        //补全信息
        tbContent.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(tbContent);
        return CxResult.ok();
    }

    /**
     * 根据id删除内容
     * @param id
     * @return
     */
    @Override
    public CxResult deleteContentById(long id) {
        contentMapper.deleteByPrimaryKey(id);
        return CxResult.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public CxResult batchRemoveContent(long[] ids) {
        for (int i = 0; i < ids.length; i++){
            deleteContentById(ids[i]);
        }
        return CxResult.ok();
    }

    /**
     * 根据categoryid查询内容
     * @param categoryId
     * @return
     */
    @Override
    public List<TbContent> getContentList(long categoryId) {
        //创建example
        TbContentExample example = new TbContentExample();
        //设置查询条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        return list;
    }
}
