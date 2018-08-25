package cn.cxmall.service.impl;

import cn.cxmall.common.jedis.JedisClient;
import cn.cxmall.common.pojo.ItemInfo;
import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.common.utils.IDUtils;
import cn.cxmall.common.utils.JsonUtils;
import cn.cxmall.mapper.TbItemDescMapper;
import cn.cxmall.mapper.TbItemMapper;
import cn.cxmall.pojo.TbItem;
import cn.cxmall.pojo.TbItemDesc;
import cn.cxmall.pojo.TbItemExample;
import cn.cxmall.service.ItemService;
import cn.cxmall.service.mapper.ItemInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品服务接口实现
 * @author 王兴毅
 * @date 2018.08.16 15:36
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JedisClient jedisClient;

    @Resource
    private Destination topicDestination;

    @Override
    public TbItem getItemById(long id) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(id);
        return tbItem;
    }

    /**
     * 分页操作
     * @param page
     * @param rows
     * @return
     */
    @Override
    public DataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        //创建返回结果对象
        DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;

    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    @Override
    public CxResult addItem(TbItem item, String desc) {
        //生成商品id
        final long itemId = IDUtils.genItemId();
        //补全item属性
        item.setId(itemId);
        //商品状态：1、正常，2、下架，3、删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建itemDesc（商品描述表）的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全itemDesc属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //发送商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("" + itemId);
                return message;
            }
        });
        //返回成功
        return CxResult.ok();
    }

    /**
     * 根据id查找完整商品信息
     * @param itemId
     * @return
     */
    @Override
    public ItemInfo getItemInfoById(long itemId) {
        ItemInfo itemInfo = itemInfoMapper.getItemInfoById(itemId);
        return itemInfo;
    }

    /**
     * 更新商品状态
     * @param itemId
     * @param status
     * @return
     */
    @Override
    public CxResult updateItemStatusById(long itemId, short status) {
        //创建tbitem的pojo
        TbItem tbItem = new TbItem();
        tbItem.setId(itemId);
        tbItem.setStatus((byte) status);
        tbItem.setUpdated(new Date());
        int update = itemMapper.updateByPrimaryKeySelective(tbItem);
        if (update == 1){
            return CxResult.ok();
        }
        return CxResult.build(501, "更新状态失败");
    }

    /**
     * 更新商品信息
     * @param item
     * @param desc
     * @return
     */
    @Override
    public CxResult updateItemById(TbItem item, String desc) {
        //补全TbItem信息
        item.setUpdated(new Date());
        //更新TbItem
        int i = itemMapper.updateByPrimaryKeySelective(item);
        //创建TbItemDesc
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全信息
        tbItemDesc.setItemId(item.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        //更新
        int i1 = itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        //返回
        if (i == 1 && i1 == 1){
            return CxResult.ok();
        }
        return CxResult.build(501, "更新失败，未知错误");
    }

}
