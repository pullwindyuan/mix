package com.futuremap.base.tree;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * 树形结构数据操作工具类
 * @author pullwind
 */
public class TreeUtil {

    private static final String TAG = "TreeDataConvert";
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeUtil.class);

    public static Tree jsonList2TreeMap(List<NodeBean> rootList, boolean isDirect) {
        if (rootList == null || rootList.size() == 0) {
            return null;
        }
        JSONArray treeArray = (JSONArray) JSONObject.toJSON(rootList);
        Tree tree = sortByFamily(sortLevelMap(rootList, isDirect), new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return Integer.parseInt((String) o1) - Integer.parseInt((String) o2);
            }
        });
        if (tree != null) {
            tree.setTreeJSONArray(treeArray);
        }
        return tree;
    }


    /**
     * 生成原始TreeMap，这里是按代数排序的，同代内数据一个无序List
     * 按代数抽取Map,key代数，value每代所有成员List集合
     */
    private static TreeMap<String, List<NodeBean>> sortLevelMap(List<NodeBean> rootList, boolean isDirect) {
        int count = rootList.size();
        TreeMap<String, List<NodeBean>> map = new TreeMap<>((o, t1) -> {
            if (o == null || t1 == null) {
                return 0;
            }
            return Integer.parseInt(o) - Integer.parseInt(t1);
        });

        for (int i = 0; i < count; i++) {
            NodeBean temp = rootList.get(i);
            String key = String.valueOf(temp.getCurrentLevel());
            List<NodeBean> tempList = map.get(key);
            if (tempList != null) {
                tempList.add(temp);
            } else {
                tempList = new ArrayList<>();
                tempList.add(temp);
                map.put(key, tempList);
            }
        }
        return map;
    }


    /**
     * 对原始TreeMap数据结构进行高级整理：
     * 1、完成按fatherId对节点进行家庭分类；
     * 2、完成对家庭按照排行进行排序（默认优先按照父辈排行进行家庭排序，如果父辈排行缺失，就按照父辈的
     * 数据处理先后顺序对后代家庭进行排序，其排行用的orderId是通过父节点的orderId+本节点的列表下标组合
     * 生成，由于这个orderId会随着代数的增长而位数增加，所以还进行了一次orderId转换成0-n的操作过程）
     * 3、完成对夫妻关系的绑定private FamilyMemberBean spouse;//配偶；
     * 4、完成对父子关系的双向绑定private FamilyMemberBean parent;//父节点；private List<FamilyMemberBean> children;//儿女
     * 5、完成同代节点的双向绑定private FamilyMemberBean pre;//前一个成员；private FamilyMemberBean next;//后一个成员
     * 6、完成家庭内节点双向绑定private FamilyMemberBean familyPre;//前一个家庭成员；private  FamilyMemberBean familyNext;后一个家庭成员
     *
     * @param map
     * @param comparator
     * @return
     */
    public static Tree sortByFamily(TreeMap<String, List<NodeBean>> map, Comparator comparator) {
        Tree tree = new Tree();
        //定义返回的MAP
        //按代数划分，存储的每个小家庭，第一层key取代树，第二层treemap,取父母ID划分一个小家庭，每代：1号、2号、3号家庭。。。。
        TreeMap<String, TreeMap<String, List<NodeBean>>> familySets = tree.getTreeMap(comparator);
//        TreeMap<String, TreeMap<String, List<FamilyMemberBean>>> familyOrderSets = familyTree.getTreeOrderMap();
        HashMap<String, NodeBean> memberMap = tree.getMemberMap();
        //遍历输入分代MAP数据
        for (Map.Entry<String, List<NodeBean>> entry : map.entrySet()) {
            //读取每代的所有成员列表
            List<NodeBean> generationList = entry.getValue();
            //遍历单代所有成员，提取相同fatherId的成员存储到同一个map中去
            for (int i = 0; i < generationList.size(); i++) {
                //获取单个成员
                NodeBean familyMember = generationList.get(i);
                //map集合根据成员存储每个成员
                //如果是第一代成员(顶层节点)，把父母ID设置成-1代表初始根节点
                if (entry.getKey().equals("1")) {
                    familyMember.setFatherId("-1");
                    familyMember.setMotherId("-1");
                    familyMember.setParentId("-1");
                }
                memberMap.put(familyMember.getId(), familyMember);
                //取father和mother
                NodeBean father = memberMap.get(familyMember.getFatherId());
                NodeBean mother = memberMap.get(familyMember.getMotherId());
                if (father != null & mother != null) {//父母亲都有
                    if (!father.isBloodRelation()) {//代表当前成员的父亲在当前家族直系亲属
                        setMemberParent(father.getId(), father, familyMember);
                    } else {
                        setMemberParent(mother.getId(), mother, familyMember);
                    }
                } else if (father != null) {//只有父亲
                    setMemberParent(father.getId(), father, familyMember);
                } else if (mother != null) {//只有母亲
                    setMemberParent(mother.getId(), mother, familyMember);
                }
//这里暂时不做容错处理，因为如果这里容错了，可能会导致其他地方出现未知问题，反而麻烦，所以要求数据准确
//                else {
//                    familyMember.setFatherId("-1");
//                    familyMember.setMotherId("-1");
//                    familyMember.setParentId("-1");
//                }

//                if(!"-1".equals(fatherId)) {//非根节点,设置父节点
//                    familyMember.setParent(memberMap.get(fatherId));
//                }

                String parentId = familyMember.getParentId();
                //根据代数读取本代对应的所有家庭数据
                TreeMap<String, List<NodeBean>> tempMap = familySets.get(entry.getKey());
                //定义单个家庭孩子成员列表
                List<NodeBean> familyList;
                //tempMap不为空表示已经存在数据
                if (tempMap != null) {
                    //取得对应fatherId的单个家庭的数据
                    familyList = tempMap.get(parentId);
                    //family不为空表示以及存在数据
                    if (familyList != null) {
                        //添加本成员到对应的家庭孩子成员列表
                        familyList.add(familyMember);
                    } else {
                        //新建一个家庭 孩子成员列表
                        familyList = new ArrayList<>();
                        //添加本成员到对应的家庭孩子成员列表
                        familyList.add(familyMember);
                        //将新建的家庭孩子成员列表以fatherId为key存储
                        tempMap.put(parentId, familyList);
                    }
                } else {
                    //全部新建数据
                    tempMap = new TreeMap<>(comparator);
                    familyList = new ArrayList<>();
                    familyList.add(familyMember);
                    tempMap.put(parentId, familyList);
                    familySets.put(entry.getKey(), tempMap);
                    LOGGER.info(TAG + ":sortByFamily:Key的代数——>{}", entry.getKey());
                }
            }
        }
        tree.setTreeListMap(map);
        tree.setTreeMap(familySets);
//        familyTree.setTreeOrderMap(familyOrderSets);
        tree.setMemberMap(memberMap);
        return sortFamilyByOrder(tree, comparator);
    }


    private static void setMemberParent(String parentId, NodeBean parentMember, NodeBean childMember) {
        childMember.setParentId(parentId);
        childMember.setParent(parentMember);
    }


    /**
     * 根据排行对数据进行排序，这样父节点在前的节点，其子节点在下代map中也相对靠前，不会错乱，在进行
     * 数据处理的时候就可以直接顺序处理即可。
     * 1、组合夫妻关系，同时按照父辈orderId + 本节点数据处理顺序来生成一个初步orderId
     * 2、每分组完一代就马上进行排行orderId分组
     * 3、全部分组完成后再对orderId进行0-n的转换
     *
     * @param tree
     * @param comparator
     * @return
     */
    public static Tree sortFamilyByOrder(Tree tree, Comparator comparator) {
        TreeMap<String, TreeMap<String, List<NodeBean>>> treeMap = tree.getTreeMap(comparator);
        TreeMap<String, TreeMap<String, List<NodeBean>>> treeOrderMap = new TreeMap<>(comparator);
//        TreeMap<String, TreeMap<String, List<FamilyMemberBean>>> treeOrderMap = new TreeMap<>();
//        HashMap<String, FamilyMemberBean> memberMap = familyTree.getMemberMap();

        //首代必然只有一个节点
        TreeMap<String, List<NodeBean>> firstGeneration = treeMap.get("1");
        NodeBean root = null;
        //找到根节点
        root = ((List<NodeBean>)(tree.getTreeListMap().get("1"))).get(0);//给根节点赋值一个默认节点,默认节点可能不是原生节点，所以在下一个循环中会处理
        if (firstGeneration != null) {
            for (Map.Entry<String, List<NodeBean>> entry : firstGeneration.entrySet()) {
                //读取一个家庭
                List<NodeBean> family = entry.getValue();
                for (int i = 0; i < family.size(); i++) {
                    if (!family.get(i).isBloodRelation()) {
                        root = family.get(i);
                        tree.setRoot(root);
                        break;
                    }
                }
//                root = family.get(0);
//                familyTree.setRoot(root);
//                break;
            }
        }

        if (root == null) {
            return tree;
        }
//        String rootOrderId = root.getOrderId();
        String orderId = null;
        //没有排行数据，将按数据处理顺序来排列
//        if(rootOrderId != null) {  //开放此处会当再次点击显示时，不能还原隐藏之前的结构
//            //如果系统已经有排行，就略过
//            return familyTree;
//        }
        int familyOrder = 1;
        String rootOrderId = String.valueOf(familyOrder);
        root.setFamilyOrder(familyOrder);
        root.setOrderId(rootOrderId);
//        FamilyMemberBean pre = null;
        int genSize = treeMap.size();
        int parentIndex = 0;
        for (int i = 1; i <= genSize; i++) {
            TreeMap<String, List<NodeBean>> generation = treeMap.get(String.valueOf(i));
            //遍历每代
            TreeMap<String, List<NodeBean>> newGeneration = new TreeMap<>(comparator);

            if (generation != null) {
                for (Map.Entry<String, List<NodeBean>> entry : generation.entrySet()) {
                    //读取一个家庭
                    List<NodeBean> familySrc = entry.getValue();
                    List<NodeBean> family = new ArrayList<>(familySrc);
                    List<NodeBean> newList = new ArrayList<>();
                    NodeBean parent = null;
                    //对夫妻进行组合
                    for (int n = 0; family.size() > 0; ) {
                        LOGGER.info(TAG, "sortFamilyByOrder->familySrc大小:" + familySrc.size() + "||family大小:" + family.size());
                        NodeBean one = family.get(n);
                        one.setChildren(null);
                        parent = one.getParent();//memberMap.get(one.getFatherId());
                        if (one.isHidden()) {
                            if (parent != null) {
                                if (parent.isHidden() || parent.isChildrenHidden()) {
                                    family.remove(one);
                                    LOGGER.info(TAG, "sortFamilyByOrder删除隐藏的成员ID:" + one.getId());
                                    continue;
                                }
                            }
                        }
                        String spouseId = one.getSpouseId();
                        String memberId = one.getId();
                        family.remove(one);
                        LOGGER.info(TAG, "sortFamilyByOrder删除隐藏的成员ID:" + one.getId());
                        if (spouseId != null) {
                            //有配偶的判断是否有血缘关系
                            if (!one.isBloodRelation()) {
                                final NodeBean other = findCouple(family, memberId, spouseId);
//                            other.setChildren(null);
                                if (other != null) {
                                    one.setSpouse(other);
                                    other.setSpouse(one);
                                    newList.add(one);
                                    other.setChildren(null);
                                } else {
                                    newList.add(one);
                                }
                            } else {
                                //先取出了嫁入者
                                final NodeBean other = findCouple(family, memberId, spouseId);
//                            other.setChildren(null);
                                if (other != null) {
                                    one.setSpouse(other);
                                    other.setSpouse(one);
                                    newList.add(other);
                                    one = other;
                                    other.setChildren(null);
                                } else {
                                    newList.add(one);
                                }
                            }
                        } else {
                            newList.add(one);
                        }

                        //给父母添加孩子,孩子添加父母
                        if (parent != null) {
                            if (one.getParentId().equals(parent.getId())) {
                                List<NodeBean> children = parent.getChildren();
                                if (children == null) {
                                    children = new ArrayList<>();
                                }
                                one.setParent(parent);
                                children.add(one);
                                parent.setChildren(children);
                                if (parent.getHiddenChildren() == null) {
                                    parent.setHiddenChildren(children);
                                }
                                NodeBean couple = parent.getSpouse();
                                if (couple != null) {
                                    List<NodeBean> coupleChildren = couple.getChildren();
                                    if (coupleChildren == null) {
                                        coupleChildren = new ArrayList<>();
                                    }
                                    coupleChildren.add(one);
                                    couple.setChildren(coupleChildren);
                                }
                            }
                            orderId = parent.getId();
                        } else {
                            orderId = rootOrderId;
                        }
                    }
//                    for (int j = 0; j < newList.size(); j++) {
//                        FamilyMemberBean fm = newList.get(j);
//                        parent = fm.getParent();
//                        if (parent == null) {//根节点Sort
//                            orderId = rootOrderId;
//                            continue;
//                        }
//                        int parentFamilyOrder = parent.getFamilyOrder();
//                        familyOrder = parentFamilyOrder * 10 + j;//一个人生30个
//                        orderId = String.valueOf(familyOrder);//生成新的排序
//                        fm.setOrderId(orderId);
//                        fm.setFamilyOrder(familyOrder);
//                        LoggerUtil.d(TAG, "sortFamilyByOrder: ||parent.getFamilyOrder():" + parentFamilyOrder + "||familyOrder:" + familyOrder);
//                    }
                    if (newList.size() > 0) {
                        newGeneration.put(orderId, newList);
                    }
                }

            }

            treeOrderMap.put(String.valueOf(i), newGeneration);
        }

        //默认把第一个成员作为定位参考,增强容错
//        if(manage.getUserMember() != null) {
//            manage.setUserMember(familyTree.getRoot());
//        }
        tree.setCurrentSelectMember(tree.getRoot());
        for (int i = 1; i <= genSize; i++) {
            TreeMap<String, List<NodeBean>> newGeneration = treeOrderMap.get(String.valueOf(i));
            //遍历每代
//            TreeMap<String, List<FamilyMemberBean>> newSortGeneration = new TreeMap<>();
            TreeMap<String, List<NodeBean>> newSortGeneration = new TreeMap<String, List<NodeBean>>(comparator);
            //重新生成order放置排序
            int order = 0;
            List<NodeBean> newList = new ArrayList<>();

            if (newGeneration != null && newGeneration.size() > 0) {
                for (Map.Entry<String, List<NodeBean>> newEntry : newGeneration.entrySet()) {
                    //读取一个家庭
                    List<NodeBean> family = newEntry.getValue();
                    int size = family.size();
                    NodeBean fm;
                    for (int k = 0; k < size; k++) {
                        fm = family.get(k);
                        newList.add(fm);
                    }
                    //newSortGeneration.put(String.valueOf(order), family);
                    order++;
                }
                tree.getTreeListMap().put(String.valueOf(i), newList);
                //将list中的成员的孩子集合取出，然后存入treeOrderMap，达到后代顺序和自己的顺序保持一致
                int newOrder = 0;
                int length = newList.size();
                //TreeMap<String, List<FamilyMemberBean>> newFamilies = new TreeMap<String, List<FamilyMemberBean>>(comparator);
                //同代首个成员pre = null
                newList.get(0).setPre(null);
                //同代末个成员next = null
                newList.get(length - 1).setNext(null);
                NodeBean pre = null;
                //首代特殊处理
                newSortGeneration.put(String.valueOf(0), (List<NodeBean>) tree.getTreeListMap().get("1"));
                treeOrderMap.put(String.valueOf(1), newSortGeneration);
                newSortGeneration = new TreeMap<String, List<NodeBean>>(comparator);
                for (int n = 0; n < length; n++) {
                    NodeBean temp = newList.get(n);
                    //同代收尾相连
                    if (pre != null) {
                        pre.setNext(temp);
                        temp.setPre(pre);
                        if (pre.getParentId().equals(temp.getParentId())) {
                            //同家庭
                            pre.setFamilyNext(temp);
                            temp.setFamilyPre(pre);
                        } else {
                            pre.setFamilyNext(null);
                            temp.setFamilyPre(null);
                        }
                    } else {
                        temp.setPre(null);
                        temp.setFamilyPre(null);
                    }
                    pre = temp;
                    if (temp.getChildren() != null) {
                        //将孩子按照自己的排序先后存入后一代的map
                        temp.setFamilyOrder(newOrder);
                        temp.setOrderId(String.valueOf(n));
                        newSortGeneration.put(String.valueOf(newOrder), temp.getChildren());
                        newOrder++;
                    }
                }
                //将重新排序的后代存入map
                if (i < genSize) {
                    treeOrderMap.put(String.valueOf(i + 1), newSortGeneration);
                }
            }
        }
        tree.setTreeOrderMap(treeOrderMap);
        return tree;
    }

    //比对目标元素的配偶
    private static NodeBean findCouple(List<NodeBean> list, String memberId, String spouseId) {
        for (int i = 0; i < list.size(); i++) {
            NodeBean other = list.get(i);
            if (spouseId.equals(other.getId()) && memberId.equals(other.getSpouseId())) {
                list.remove(i);
                return other;
            }
        }
        return null;
    }
}
