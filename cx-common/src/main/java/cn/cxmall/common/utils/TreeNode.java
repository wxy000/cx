package cn.cxmall.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王兴毅
 * @date 2018.08.22 11:41
 */
public class TreeNode implements Serializable {

    private long id;

    private long parentId;

    private String name;

    private int status;

    private List<TreeNode> children;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TreeNode() {
    }

    public TreeNode(long id, String name, long parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
    public TreeNode(long id, String name, TreeNode parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
    }


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}