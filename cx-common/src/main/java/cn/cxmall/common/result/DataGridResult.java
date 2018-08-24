package cn.cxmall.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.18 12:54
 */
public class DataGridResult implements Serializable {

    private int code;

    private String msg;

    private Long total;

    private List<?> rows;

    public DataGridResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataGridResult(Long total, List<?> rows) {
        this.total = total.longValue();
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
