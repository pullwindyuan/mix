package com.futuremap.resourceManager.service;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.resourceManager.bean.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void addChildMenu(Menu parent, Menu child) {
        DictionaryFactory.addChildDictionary(parent, child);
    }

    public static void removeChildMenu(Menu parent, Menu child) {
        DictionaryFactory.removeChildDictionary(parent, child);
    }

    public JSONObject getMenu(String id) {
        String objStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), String.class, "menu");
        JSONObject object = JSONObject.parseObject(objStr);
        return object;
    }

    public JSONObject getConfig() {
        JSONObject jsonObject = new JSONObject();
        /**
         * 获取菜单资源
         */
        List<JSONObject> menu = mongoTemplate.findAll(JSONObject.class, "menu");
        JSONObject menuMap = new JSONObject();
        menu.forEach(e->{
            menuMap.put(e.getString("id"), e);
        });
        jsonObject.put("MENU", menuMap);

        /**
         * 获取场景资源
         */
        List<JSONObject> scene = mongoTemplate.findAll(JSONObject.class, "scene");
        JSONObject sceneMap = new JSONObject();
        scene.forEach(e->{
            sceneMap.put(e.getString("id"), e);
        });
        jsonObject.put("SCENE", sceneMap);

        /**
         * 获取细胞功能资源
         */
        List<JSONObject> cell = mongoTemplate.findAll(JSONObject.class, "cell");
        JSONObject cellMap = new JSONObject();
        cell.forEach(e->{
            cellMap.put(e.getString("id"), e);
        });
        jsonObject.put("CELL", cellMap);

        /**
         * 获取基础功能资源
         */
        List<JSONObject> function = mongoTemplate.findAll(JSONObject.class, "function");
        JSONObject functionMap = new JSONObject();
        function.forEach(e->{
            functionMap.put(e.getString("id"), e);
        });
        jsonObject.put("FUNCTION", functionMap);

        /**
         * 获取可视化UI组件资源
         */
        List<JSONObject> view = mongoTemplate.findAll(JSONObject.class, "UIComponent");
        JSONObject viewMap = new JSONObject();
        view.forEach(e->{
            viewMap.put(e.getString("id"), e);
        });
        jsonObject.put("VIEW", viewMap);

        /**
         * 获取可视化元素资源
         */
        List<JSONObject> element = mongoTemplate.findAll(JSONObject.class, "UIElement");
        JSONObject elementMap = new JSONObject();
        element.forEach(e->{
            elementMap.put(e.getString("id"), e);
        });
        jsonObject.put("ELEMENT", elementMap);

        /**
         * 获取基础资源
         */
        List<JSONObject> resource = mongoTemplate.findAll(JSONObject.class, "resource");
        JSONObject resourceMap = new JSONObject();
        resource.forEach(e->{
            resourceMap.put(e.getString("id"), e);
        });
        jsonObject.put("RESOURCE", resourceMap);

        /**
         * 获取API资源
         */
        List<JSONObject> API = mongoTemplate.findAll(JSONObject.class, "api");
        JSONObject APIMap = new JSONObject();
        API.forEach(e->{
            APIMap.put(e.getString("id"), e);
        });
        jsonObject.put("API", APIMap);
        return jsonObject;
    }
}
