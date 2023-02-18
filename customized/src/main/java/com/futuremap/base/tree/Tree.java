package com.futuremap.base.tree;

import com.alibaba.fastjson.JSONArray;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * 一个完整树形结构的数据包装类
 * @author pullwind
 */
public class Tree <T> {
    private JSONArray treeJSONArray = null;
    private TreeMap<String, List<NodeBean<T>>> treeListMap;
    private TreeMap<String, TreeMap<String, List<NodeBean<T>>>> treeOrderMap;
    private TreeMap<String, TreeMap<String, List<NodeBean<T>>>> treeMap;
    private HashMap<String, NodeBean<T>> memberMap;
    private ViewSpace viewSpace;//整颗树的显示空间（相对）
    private NodeBean<T> root;
    private NodeBean<T> currSelectMember ;//当前选中成员
    private boolean currSelectMemberAsChild = false;//当前选中成员是否作为孩子被选中,默认为否
    private NodeBean<T> referenceMember;//当前参考成员
    public void clear(){
        if (treeListMap!=null){
            treeListMap.clear();
        }
        if (treeOrderMap!=null){
            treeOrderMap.clear();
        }

        if (treeMap!=null){
            treeMap.clear();
        }

        if (memberMap!=null){
            memberMap.clear();
        }
        treeListMap = null;
        treeOrderMap = null;
        treeMap = null;
        memberMap = null;
        currSelectMember= null;
    }

    public NodeBean<T> setSelectedMemberFromLast(NodeBean<T> member, boolean isSelectedAsChild) {
        NodeBean<T> temp = getMemberMap().get(member.getId());
        NodeBean<T> selected = temp;
        if(temp == null) {//成员不存在，可能已经被删除
            NodeBean<T> parent = member.getParent();
            if(parent != null) {
                selected = getMemberMap().get(parent.getId());
                assert selected != null;
                selected.setSelectAsChild(false);
            }else {
                selected = getRoot();
                selected.setSelectAsChild(false);
            }
        }else {
            selected.setSelectAsChild(isSelectedAsChild);
        }
        setCurrentSelectMember(selected);
        return selected;
    }
    public NodeBean<T> getReferenceMember() {
        return referenceMember;
    }

    public void setReferenceMember(NodeBean<T> referenceMember) {
        this.referenceMember = referenceMember;
    }

    public boolean isCurrSelectMemberAsChild() {
        return this.currSelectMemberAsChild;
    }

    public void setCurrSelectMemberAsChild(boolean currSelectMemberAsChild) {
        this.currSelectMemberAsChild = currSelectMemberAsChild;
        this.currSelectMember.setSelectAsChild(currSelectMemberAsChild);
    }

    public void setCurrentSelectMember(NodeBean<T> currSelectMember) {
        if(this.currSelectMember != null) {
            this.currSelectMember.setSelect(false);
        }
        this.currSelectMember  =null;
        this.currSelectMember = currSelectMember;
        this.currSelectMember.setSelect(true);
    }

    public void setCurrentSelectMember(NodeBean<T> currSelectMember, boolean isSelectedAsChild) {
        setCurrentSelectMember(currSelectMember);
        this.currSelectMember.setSelectAsChild(isSelectedAsChild);
    }

    public NodeBean<T> getCurrentSelectMember() {
        return currSelectMember;
    }


    public Tree() {

    }

    public JSONArray getTreeJSONArray() {
        return treeJSONArray;
    }

    public void setTreeJSONArray(JSONArray treeJSONArray) {
        this.treeJSONArray = treeJSONArray;
    }

    public Tree getTreeFromJSONArray() {
        if(this.treeJSONArray != null) {
            return TreeUtil.jsonList2TreeMap(this.treeJSONArray.toJavaList(NodeBean.class), false);
        }else {
            return null;
        }
    }

    public TreeMap<String, List<NodeBean<T>>> getTreeListMap() {
        return treeListMap;
    }

    public void setTreeListMap(TreeMap<String, List<NodeBean<T>>> treeListMap) {
        this.treeListMap = treeListMap;
    }

    public NodeBean<T> getRoot() {
        return root;
    }

    public void setRoot(NodeBean<T> root) {
        this.root = root;
    }

    public ViewSpace getViewSpace() {
        if (viewSpace == null) {
            viewSpace = new ViewSpace();
        }
        return viewSpace;
    }

    public void setViewSpace(ViewSpace viewSpace) {
        this.viewSpace = viewSpace;
    }

    public TreeMap<String, TreeMap<String, List<NodeBean<T>>>> getTreeOrderMap(Comparator comparator) {
        if (treeOrderMap == null) {
            treeOrderMap = new TreeMap<String, TreeMap<String, List<NodeBean<T>>>>(comparator);
        }
        return treeOrderMap;
    }

    public TreeMap<String, TreeMap<String, List<NodeBean<T>>>> getTreeOrderMap() {
        return treeOrderMap;
    }

    void setTreeOrderMap(TreeMap<String, TreeMap<String, List<NodeBean<T>>>> treeOrderMap) {
        this.treeOrderMap = treeOrderMap;
    }

    public TreeMap<String, TreeMap<String, List<NodeBean<T>>>> getTreeMap(Comparator comparator) {
        if (treeMap == null) {
            treeMap = new TreeMap<String, TreeMap<String, List<NodeBean<T>>>>(comparator);
        }
        return treeMap;
    }

    public TreeMap<String, TreeMap<String, List<NodeBean<T>>>> getTreeMap() {
        return treeMap;
    }

    void setTreeMap(TreeMap<String, TreeMap<String, List<NodeBean<T>>>> treeMap) {
        this.treeMap = treeMap;
    }

    public HashMap<String, NodeBean<T>> getMemberMap() {
        if (memberMap == null) {
            memberMap = new HashMap<String, NodeBean<T>>();
        }
        return memberMap;
    }

    void setMemberMap(HashMap<String, NodeBean<T>> memberMap) {
        this.memberMap = memberMap;
    }

    public void hiddenItem(NodeBean<T> member) {
        //设置节点本身的隐藏状态
        if (member != null) {
            member.setHidden(true);
            //只有当前节点才设置本身的隐藏状态
            member.setSelfHidden(true);
            //single.setHiddenChildren(hidden);
            List<NodeBean<T>> children;
            children = member.getHiddenChildren();
            member.setChildren(null);
            NodeBean<T> couple = member.getSpouse();
            if (couple != null) {
                couple.setHidden(true);
                member.setSelfHidden(true);
                //couple.setHiddenChildren(hidden);
                couple.setChildren(null);
                couple.setHiddenChildren(member.getHiddenChildren());
            }
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    NodeBean<T> child = children.get(i);
                    hiddenItem(child, true);
                }
            }
        }
    }

    public void showItem(NodeBean<T> member) {
        //设置节点本身的隐藏状态
        if (member != null) {
            member.setHidden(false);
            //只有当前节点才设置本身的隐藏状态
            member.setSelfHidden(false);
            //single.setHiddenChildren(hidden);
            List<NodeBean<T>> children;
            children = member.getHiddenChildren();
            member.setChildren(null);
            NodeBean<T> couple = member.getSpouse();
            if (couple != null) {
                couple.setHidden(false);
                member.setSelfHidden(false);
                //couple.setHiddenChildren(hidden);
                couple.setChildren(null);
                couple.setHiddenChildren(member.getHiddenChildren());
            }
            if (member.isChildrenHidden() == false) {
                //如果其子节点是设置的展开，那么子节点的子节点状态要跟随
                if (children != null) {
                    for (int i = 0; i < children.size(); i++) {
                        NodeBean<T> child = children.get(i);
                        if (child.isSelfHidden() == false) {
                            //子节点本身是展开状态的话，那么才会跟随父节点
                            hiddenItem(child, false);
                        }
                    }
                }
            }
        }
    }

    public void hiddenItemChildren(NodeBean<T> member) {
        hiddenItemChildren(member, true);
    }

    public void showItemChildren(NodeBean<T> member) {
        hiddenItemChildren(member, false);
    }

    private void hiddenItemChildren(NodeBean<T> member, boolean hidden) {
        if (member != null) {
            List<NodeBean<T>> children =  member.getHiddenChildren();
            member.setChildren(null);
            member.setChildrenHidden(hidden);
            NodeBean<T> couple = member.getSpouse();
            if (couple != null) {
                couple.setChildren(null);
                couple.setChildrenHidden(hidden);
                couple.setHiddenChildren(member.getHiddenChildren());
            }
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    NodeBean<T> child = children.get(i);
                    if (!child.isSelfHidden()) {
                        //子节点本身是展开状态的话，才进入后续的操作
                        hiddenItem(child, hidden);
                    }
                }
            }
        }
    }

    public void ignoreBranchItemChildren(NodeBean<T> member) {
        if (member != null) {
            //single.setChildren(null);
            //single.setIsSpouse(null);
            NodeBean<T> familyPre = member.getFamilyPre();
            NodeBean<T> familyNext = member.getFamilyNext();
            int preLen = 0;
            int nextLen = 0;
            if(familyPre != null) {
                while (true) {
                    familyPre.setChildren(null);
                    familyPre.setSpouse(null);
                    if (familyPre.getFamilyPre() != null) {
                        familyPre = familyPre.getFamilyPre();
                        preLen++;
                    } else {
                        break;
                    }
                }
            }
            if(familyNext != null) {
                while (true) {
                    familyNext.setChildren(null);
                    familyNext.setSpouse(null);
                    if (familyNext.getFamilyNext() != null) {
                        familyNext = familyNext.getFamilyNext();
                        nextLen++;
                    } else {
                        break;
                    }
                }
            }
            int deltaPre = (preLen - nextLen)/2;
            int deltaNext = (nextLen - preLen)/2;
            NodeBean<T> tempPre = null;
            if(deltaPre >= 1) {
                tempPre = member.getFamilyPre();
                for( ;deltaPre > 0; deltaPre --) {
                    tempPre = member.getFamilyPre();
                }
            }else if(deltaNext >= 1) {
                tempPre = member.getFamilyNext();
                for( ;deltaNext > 0; deltaNext --) {
                    tempPre = member.getFamilyNext();
                }
            }
            if(tempPre != null) {
                NodeBean<T> temp = member.getFamilyPre();
                if(temp != null) {
                    //取出prent节点
                    temp.setFamilyNext(member.getFamilyNext());
                }
                member.getFamilyNext().setFamilyPre(member.getFamilyPre());
                //将paren插入到新的位置
                NodeBean<T> tempNext = tempPre.getFamilyNext();
                tempPre.setFamilyNext(member);
                tempNext.setFamilyPre(member);
                member.setFamilyPre(tempPre);
                member.setFamilyNext(tempNext);
            }
            ignoreBranchItemChildren(member.getParent());
        }
    }

    /**
     * 递归处理隐藏状态
     */
    private void hiddenItem(NodeBean<T> member, boolean hidden) {
        if (member != null) {
            member.setHidden(hidden);
            List<NodeBean<T>> children;
            children = member.getHiddenChildren();
            member.setChildren(null);
            NodeBean<T> couple = member.getSpouse();
            if (couple != null) {
                couple.setHidden(hidden);
                couple.setChildren(null);
                couple.setHiddenChildren(member.getHiddenChildren());
            }
            if (!member.isChildrenHidden()) {
                //节点展开的时候，如果其子节点也是设置的展开，那么子节点的子节点也要设置为展开
                if (children != null) {
                    for (int i = 0; i < children.size(); i++) {
                        NodeBean<T> child = children.get(i);
                        if (!child.isSelfHidden()) {
                            //子节点本身是展开状态的话，才进入后续的操作
                            hiddenItem(child, hidden);
                        }
                    }
                }
            }
        }
    }

    public boolean haveAnyBinedUser(NodeBean<T> member) {
        if (member.getExternalId() != null) {
            return true;
        }
        List<NodeBean<T>> children = member.getChildren();
        if (children == null || children.size() == 0) {
            return false;
        }
        for (NodeBean<T> m : children) {
            if (m.getExternalId() != null) {
                return true;
            } else {
                return haveAnyBinedUser(m);
            }
        }
        return false;
    }


}
