package cn.cxmall.service.mapper;

import cn.cxmall.common.pojo.ItemInfo;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.20 14:14
 */
public interface ItemInfoMapper {

    /**
     * 根据id取完整商品信息
     * @param itemId
     * @return
     */
    ItemInfo getItemInfoById(long itemId);

    /**
     * 取全部完整商品信息
     * @return
     */
    List<ItemInfo> getItemInfoList();
}
