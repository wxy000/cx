package cn.cxmall.service;

import cn.cxmall.common.pojo.ItemInfo;
import cn.cxmall.common.result.CxResult;
import cn.cxmall.common.result.DataGridResult;
import cn.cxmall.pojo.TbItem;

import java.util.List;

/**
 * 商品服务接口
 * @author 王兴毅
 * @date 2018.08.16 15:34
 */
public interface ItemService {

    /**
     * 根据id查找商品信息
     * @param id
     * @return
     */
    TbItem getItemById(long id);

    /**
     * 取出所有商品
     * @return
     */
    DataGridResult getItemList(int page, int rows);

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    CxResult addItem(TbItem item, String desc);

    /**
     * 根据id查找完整商品信息
     * @param itemId
     * @return
     */
    ItemInfo getItemInfoById(long itemId);

    /**
     * 更新商品状态
     * @param itemId
     * @param status
     * @return
     */
    CxResult updateItemStatusById(long itemId, short status);

    /**
     * 更新商品信息
     * @param item
     * @param desc
     * @return
     */
    CxResult updateItemById(TbItem item, String desc);
}
