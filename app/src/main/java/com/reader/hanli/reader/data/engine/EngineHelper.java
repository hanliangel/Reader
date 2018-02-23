package com.reader.hanli.reader.data.engine;

/**
 * Created by hanli on 2018/2/11.
 */

public class EngineHelper {

    private static final String DEFAULT_ENGINE_NAME = "biquge";

    private static EngineHelper mEngineHelper;

    private BookEngine mCurrentEngine;

    private String mCurrentEngineName;

    private EngineHelper(){

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
        if(!engineName.equals(mCurrentEngineName)){
            mCurrentEngineName = engineName;
            mCurrentEngine = EngineFactory.getInstance().create(engineName);
        }
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
}
