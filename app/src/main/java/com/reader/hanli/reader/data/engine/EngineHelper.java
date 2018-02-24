package com.reader.hanli.reader.data.engine;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanli on 2018/2/11.
 */

public class EngineHelper {

    private static final String DEFAULT_ENGINE_NAME = "biquge";

    private static EngineHelper mEngineHelper;

    private BookEngine mCurrentEngine;

    private Map<String , BookEngine> engineMap;

    private EngineHelper(){
        engineMap = new HashMap<>();
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
        return mCurrentEngine;
    }

    /**
     * 获得当前使用的书籍引擎
     * @return
     */
    public BookEngine getBookEngine(){
        if(mCurrentEngine == null){
            switchEngine(DEFAULT_ENGINE_NAME);
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
}
