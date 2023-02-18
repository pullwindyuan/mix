package com.futuremap.base.dictionary;

import com.futuremap.base.util.BeanUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 使用分组字典来操作文件夹、分组、分类、目录、菜单树等的工厂类
 * @author dell
 */
public class DictionaryFactory {
    protected static Map<String, Dictionary> dictionaryMap = new HashMap<>();
    protected static Map<String, DictionaryTree> dictionaryTreeMap = new HashMap<>();

    public static void addChildDictionary(Dictionary parent, Dictionary child) {
        parent.getGd().put(child.getId(), child);
        child.setParentId(parent.getParentId());
        child.setTreeId(parent.getTreeId());
        child.setLevel(parent.getLevel() + 1);
        TreeMap gd = dictionaryTreeMap.get(child.getTreeId()).getNodeMap();
        updateTreeFromRoot(child, gd);
    }

    public static void removeChildDictionary(Dictionary parent, Dictionary child) {
        parent.getGd().remove(child.getId());
        child.setParentId(null);
        child.setLevel(0);
        dictionaryTreeMap.get(child.getTreeId()).getNodeMap().remove(child.getId());
    }

    public static DictionaryTree createDictionaryTree( Dictionary root) {
        DictionaryTree dictionaryTree = BeanUtil.copyProperties(root, DictionaryTree.class);
        dictionaryTreeMap.put(root.getId(), dictionaryTree);
        TreeMap<String, Dictionary> gd = new TreeMap<>();
        dictionaryTree.setNodeMap(gd);
        if(root != null) {
            updateTreeFromRoot(root, gd);
        }
        return dictionaryTree;
    }

    public static DictionaryTree createDictionaryTree(String id,
                                                      String externalId,
                                                      String desc,
                                                      String name) {
        DictionaryTree dictionaryTree = dictionaryTreeMap.get(id);
        if(dictionaryTree == null) {
            dictionaryTree = new DictionaryTree();
        }
        dictionaryTreeMap.put(id, dictionaryTree);
        dictionaryTree.setId(id);
        dictionaryTree.setExternalId(externalId);
        dictionaryTree.setDesc(desc);
        dictionaryTree.setName(name);
        //dictionaryTree.setRoot(root);
        TreeMap<String, Dictionary> gd = new TreeMap<>();
        dictionaryTree.setNodeMap(gd);
        return dictionaryTree;
    }

    public static boolean setDictionaryTreeRoot(String treeId, Dictionary root) {
        DictionaryTree dictionaryTree = dictionaryTreeMap.get(treeId);
        if(dictionaryTree == null) {
            return false;
        }
        root.setLevel(0);
        //dictionaryTree.setRoot(root);

        dictionaryTree = BeanUtil.copyProperties(root, DictionaryTree.class);
        dictionaryTreeMap.put(treeId, dictionaryTree);
        dictionaryTree.getNodeMap().put(root.getId(), root);
        dictionaryTree.setNodeMap(new TreeMap<>());
        root.setTreeId(dictionaryTree.getId());
        updateTreeFromRoot(root, dictionaryTree.getNodeMap());
        return true;
    }

    public static  void setDictionaryTreeNodeMapFromRoot(Dictionary dictionary, DictionaryTree tree) {
        TreeMap<String, Dictionary> nodeMap = tree.getNodeMap();
        setDictionaryTreeNodeMapFromRoot(dictionary, nodeMap);
    }
    public static  void setDictionaryTreeNodeMapFromRoot(Dictionary dictionary, TreeMap<String, Dictionary> out) {
        out.put(dictionary.getId(), dictionary);
        TreeMap<String, Dictionary> gd = dictionary.getGd();
        if(gd == null || gd.size() == 0) {
            return;
        }
        gd.forEach((k, item)->{
            Dictionary temp = item;
            out.put(temp.getId(), temp);
            setDictionaryTreeNodeMapFromRoot(item, out);
        });
    }

    public static  void updateTreeFromRoot(Dictionary root, DictionaryTree tree) {
        TreeMap<String, Dictionary> nodeMap = tree.getNodeMap();
        updateTreeFromRoot(root, nodeMap);
    }

    public static  void updateTreeFromRoot(Dictionary root, TreeMap<String, Dictionary> nodeMap) {
        nodeMap.put(root.getId(), root);
        TreeMap<String, Dictionary> gd = root.getGd();
        if(gd == null || gd.size() == 0) {
            return;
        }
        gd.forEach((k, item)->{
            item.setTreeId(root.getTreeId());
            item.setLevel(root.getLevel() + 1);
            nodeMap.put(item.getId(), item);
            updateTreeFromRoot(item, nodeMap);
        });
    }
}
