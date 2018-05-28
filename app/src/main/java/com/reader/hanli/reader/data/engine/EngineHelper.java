package com.reader.hanli.reader.data.engine;

import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanli on 2018/2/11.
 */

public class EngineHelper {

    private static final String DEFAULT_ENGINE_NAME = "bequge";

    private static EngineHelper mEngineHelper;

    /**
     * 当前的搜索引擎
     */
    private BookEngine mCurrentEngine;

    /**
     * 存储所有加载了的引擎的map
     */
    private Map<String , BookEngine> engineMap;


    /**
     * 缓存的当前搜索引擎名称
     */
    private static final String CACHE_KEY_SEARCH_CUR_ENGINE_NAME = "search_cur_engine_name";

    private EngineHelper(){
        engineMap = EngineFactory.getInstance().getAllBookEngine();
    }

    public static EngineHelper getInstance(){
        if(mEngineHelper == null){
            mEngineHelper = new EngineHelper();
        }
        return mEngineHelper;
    }

    /**
     * 切换书籍引擎
     * @param engineName
     * @return
     */
    public BookEngine switchEngine(String engineName){
        mCurrentEngine = getBookEngine(engineName);
        CacheUtils.getInstance().put(CACHE_KEY_SEARCH_CUR_ENGINE_NAME , engineName);
        return mCurrentEngine;
    }

    /**
     * 获得当前使用的书籍引擎
     * @return
     */
    public BookEngine getBookEngine(){
        if(mCurrentEngine == null){
            String lastEngineName = CacheUtils.getInstance().getString(CACHE_KEY_SEARCH_CUR_ENGINE_NAME , "");
            if(TextUtils.isEmpty(lastEngineName)){
                switchEngine(DEFAULT_ENGINE_NAME);
            }else{
                switchEngine(lastEngineName);
            }
        }
        return mCurrentEngine;
    }

    /**
     * 获得一个书籍引擎
     * @param engineName
     * @return
     */
    public BookEngine getBookEngine(String engineName){
        BookEngine engine = engineMap.get(engineName);
        if(ObjectUtils.isEmpty(engine)){
            engine = EngineFactory.getInstance().create(engineName);
            engineMap.put(engineName , engine);
        }
        return engine;
    }

    /**
     * 获得配置的所有的书籍引擎
     * @return
     */
    public List<BookEngine> getAllBookEngine(){
        return new ArrayList<>(engineMap.values());
    }
}
