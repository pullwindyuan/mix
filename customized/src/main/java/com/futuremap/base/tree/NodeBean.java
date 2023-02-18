package com.futuremap.base.tree;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 节点
 * @author pullwind
 */
@Data
@Accessors(chain = true)
public class NodeBean <T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String title;
    private String memberImg;
    private String type;
    private String createTime;
    private String fatherId;
    private String motherId;
    private String spouseId;
    private int currentLevel;
    private String treeId;
    private String externalId;
    private int stillLiving;
    private  T data;

    private boolean bloodRelation;
    private Visualization visualization;

    private NodeBean<T> spouse;

    private NodeBean<T> pre;
    private NodeBean<T> next;

    private NodeBean<T> familyPre;

    private NodeBean<T> familyNext;

    private NodeBean<T> parent;

    private List<NodeBean<T>> historySpouse;

    private List<NodeBean<T>> hiddenChildren;

    private List<NodeBean<T>> children;

    private ViewSpace childrenViewSpace;

    private ViewSpace viewSpace;

    private ViewSpace coreViewSpace;


    private String orderId;
    private int familyOrder;

    private int preGap;

    private boolean isAlign;

    private boolean isSelect = false;

    private boolean isSelectAsChild = false;

    private boolean isHidden;

    private boolean isSelfHidden;

    private boolean isChildrenHidden;

    private boolean switchBranch = false;

    private boolean isHideBranch = false;

    private boolean isSpouse = true;

    private boolean isYxBranch = false;

    private String parentId;
}
