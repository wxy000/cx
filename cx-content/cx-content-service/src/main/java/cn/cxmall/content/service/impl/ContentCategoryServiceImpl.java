package cn.cxmall.content.service.impl;

import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.utils.TreeBuilder;
import cn.cxmall.common.utils.TreeNode;
import cn.cxmall.content.service.ContentCategoryService;
import cn.cxmall.mapper.TbContentCategoryMapper;
import cn.cxmall.pojo.TbContentCategory;
import cn.cxmall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理服务实现
 * @author 王兴毅
 * @date 2018.08.22 12:26
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 查询内容类别
     * @return
     */
    @Override
    public List<TreeNode> getContentCatList() {
        //查询全部子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        //转换成TreeNode
        List<TreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory category : list){
            TreeNode treeNode = new TreeNode();
            treeNode.setId(category.getId());
            treeNode.setParentId(category.getParentId());
            treeNode.setName(category.getName());
            treeNode.setStatus(category.getStatus());
            //添加到列表
            nodeList.add(treeNode);
        }
        return TreeBuilder.bulid(nodeList);
    }

    /**
     * 新增内容类别
     * @param id
     * @return
     */
    @Override
    public CxResult addContentCat(long id, String name) {
        //创建TbContentCategory pojo
        TbContentCategory tbContentCategory = new TbContentCategory();
        //补全对象
        tbContentCategory.setParentId(id);
        tbContentCategory.setName(name);
        //1-正常,2-删除
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //保存
        tbContentCategoryMapper.insert(tbContentCategory);
        //更改父栏目状态
        TbContentCategory select = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (!select.getIsParent()){
            select.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(select);
        }
        return CxResult.ok();
    }

    /**
     * 更新内容类别
     * @param id
     * @param name
     * @return
     */
    @Override
    public CxResult updateContentCat(long id, String name) {
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        category.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(category);
        return CxResult.ok();
    }

    /**
     * 删除栏目
     * @param id
     * @return
     */
    @Override
    public CxResult deleteContentCat(long id) {
        //创建TbContentCategory对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        //1、正常，2、删除
        tbContentCategory.setStatus(2);
        tbContentCategory.setUpdated(new Date());
        //做出修改
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        deleteRecursion(id);
        return CxResult.ok();
    }

    /**
     * 递归查询修改
     * @param id
     */
    private void deleteRecursion(long id){
        //查询子栏目，并修改
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        //查询
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if (!"".equals(list) && list != null){
            for (TbContentCategory category : list){
                category.setStatus(2);
                category.setUpdated(new Date());
                tbContentCategoryMapper.updateByPrimaryKeySelective(category);
                deleteRecursion(category.getId());
            }
        }
    }
}
