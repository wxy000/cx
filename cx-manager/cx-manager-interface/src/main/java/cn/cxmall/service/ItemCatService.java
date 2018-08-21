package cn.cxmall.service;

import cn.cxmall.common.result.EasyUITreeNode;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.19 14:03
 */
public interface ItemCatService {

    List<EasyUITreeNode> getCatList(long parentId);
}
