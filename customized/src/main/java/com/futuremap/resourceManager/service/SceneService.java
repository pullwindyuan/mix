package com.futuremap.resourceManager.service;

import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.resourceManager.bean.Scene;

public class SceneService extends DictionaryFactory {
    public static void addChildScene(Scene parent, Scene child) {
        DictionaryFactory.addChildDictionary(parent, child);
    }

    public static void removeChildScene(Scene parent, Scene child) {
        DictionaryFactory.removeChildDictionary(parent, child);
    }
}
