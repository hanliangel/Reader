package com.reader.hanli.reader.data.engine;

import android.content.res.AssetManager;

import com.reader.hanli.reader.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    public static EngineFactory getInstance(){
        if(mEngineFactory == null){
            mEngineFactory = new EngineFactory();
        }
        return mEngineFactory;
    }

    public BookEngine create(String engineName){
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

}
