package com.futuremap.resourceManager.service;

import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.resourceManager.bean.Cell;

public class CellService extends DictionaryFactory {
    public static void addChildCell(Cell parent, Cell child) {
        DictionaryFactory.addChildDictionary(parent, child);
    }

    public static void removeChildCell(Cell parent, Cell child) {
        DictionaryFactory.removeChildDictionary(parent, child);
    }
}
