package cn.cxmall.search.service.impl;

import cn.cxmall.common.pojo.SearchItem;
import cn.cxmall.common.result.CxResult;
import cn.cxmall.search.mapper.ItemMapper;
import cn.cxmall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.24 15:51
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    /**
     * 导入全部商品到索引库
     * @return
     */
    @Override
    public CxResult importAllItem() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //遍历商品列表
            for (SearchItem searchItem : itemList){
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                solrServer.add(document);
                //把文档对象写入索引库solr
            }
            //提交
            solrServer.commit();
            return CxResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return CxResult.build(500, "数据导入失败");
        }
    }
}
