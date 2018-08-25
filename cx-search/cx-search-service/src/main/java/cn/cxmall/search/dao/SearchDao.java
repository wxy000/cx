package cn.cxmall.search.dao;

import cn.cxmall.common.pojo.SearchItem;
import cn.cxmall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 与索引库的数据操作
 * @author 王兴毅
 * @date 2018.08.08 09:45
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询索引库
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult searchResult(SolrQuery query) throws Exception{
        //根据query查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取总记录数
        long numFound = solrDocumentList.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        //取商品列表，并高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList){
            SearchItem item = new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title;
            if (list != null && list.size() > 0){
                title = list.get(0);
            }else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            //添加到商品列表
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }
}
