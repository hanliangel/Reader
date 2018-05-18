package com.reader.hanli.reader.data.engine;

import android.content.res.AssetManager;

import com.reader.hanli.reader.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by hanli on 2018/2/11.
 */
public class EngineFactory {

    private static EngineFactory mEngineFactory;

    private Properties mProperties;

    private EngineFactory(){
        try {
            InputStream is = MyApplication.getInstance().getAssets().open("engine.properties");
            mProperties = new Properties();
            mProperties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static EngineFactory getInstance(){
        if(mEngineFactory == null){
            mEngineFactory = new EngineFactory();
        }
        return mEngineFactory;
    }

    BookEngine create(String engineName){
        String className = mProperties.getProperty(engineName);
        try {
            BookEngine engine = (BookEngine) Class.forName(className).newInstance();
            return engine;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得配置的所有的搜索引擎
     * @return
     */
    Map<String , BookEngine> getAllBookEngine(){
        Map<String , BookEngine> result = new HashMap<>();
        Iterator<Map.Entry<Object, Object>> iterator = mProperties.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Object, Object> next = iterator.next();
            String engineName = next.getKey().toString();
            BookEngine engine = create(engineName);
            result.put(engineName , engine);
        }
        return result;
    }

}
