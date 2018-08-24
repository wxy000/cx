package cn.cxmall.content.service.impl;

import cn.cxmall.common.jedis.JedisClient;
import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.common.utils.JsonUtils;
import cn.cxmall.content.service.ContentService;
import cn.cxmall.mapper.TbContentMapper;
import cn.cxmall.pojo.TbContent;
import cn.cxmall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

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
        //缓存同步
        jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
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
        //缓存同步
        jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
        return CxResult.ok();
    }

    /**
     * 根据id删除内容
     * @param id
     * @return
     */
    @Override
    public CxResult deleteContentById(long id) {
        //缓存同步
        TbContent tbContent = contentMapper.selectByPrimaryKey(id);
        jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
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
        //从redis缓存中取数据
        try {
            String hget = jedisClient.hget(CONTENT_LIST, categoryId + "");
            if (StringUtils.isNotBlank(hget)){
                List<TbContent> contents = JsonUtils.jsonToList(hget, TbContent.class);
                return contents;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //如果redis中没有则到数据库中查
        //创建example
        TbContentExample example = new TbContentExample();
        //设置查询条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        //将查询结果保存到redis中
        try {
            jedisClient.hset(CONTENT_LIST, categoryId + "", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
