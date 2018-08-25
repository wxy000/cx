package cn.cxmall.search.service.impl;

import cn.cxmall.common.pojo.SearchResult;
import cn.cxmall.search.dao.SearchDao;
import cn.cxmall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品搜索
 * @author 王兴毅
 * @date 2018.08.08 10:16
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws Exception {
        //创建一个solrquery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyword);
        //设置分页条件
        if (page <= 0){
            page = 1;
        }
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", "item_title");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //调用dao执行查询
        SearchResult searchResult = searchDao.searchResult(query);
        //计算总页数
        long recordCount = searchResult.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if (recordCount % rows > 0){
            totalPage++;
        }
        //添加到返回结果
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
