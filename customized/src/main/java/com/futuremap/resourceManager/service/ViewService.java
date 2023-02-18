package com.futuremap.resourceManager.service;

import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.resourceManager.bean.UIComponent;

public class ViewService extends DictionaryFactory {
    public static void addChildView(UIComponent parent, UIComponent child) {
        DictionaryFactory.addChildDictionary(parent, child);
    }

    public static void removeChildView(UIComponent parent, UIComponent child) {
        DictionaryFactory.removeChildDictionary(parent, child);
    }
}
