package cn.cxmall.search.mapper;

import cn.cxmall.common.pojo.SearchItem;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.07 19:33
 */
public interface ItemMapper {
    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
