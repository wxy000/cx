package cn.cxmall.search.service;

import cn.cxmall.common.result.CxResult;

/**
 * @author 王兴毅
 * @date 2018.08.24 15:49
 */
public interface SearchItemService {

    /**
     * 导入全部商品到索引库
     * @return
     */
    CxResult importAllItem();
}
