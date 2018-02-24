package com.reader.hanli.reader.data.engine.impl;

import android.util.Log;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.BookEngine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by hanli on 2018/2/11.
 */

public class BiqugeEngineImpl implements BookEngine {

    private static final String SEARCH_URL = "http://zhannei.baidu.com/cse/search?s=920895234054625192&q=";

    private static final String ENGINE_NAME = "biquge";

    @Override
    public List<Book> getBookshelf() {
        return null;
    }

    @Override
    public List<Book> searchBook(String search) {
        //获取整个页面文件
        List<Book> list = new ArrayList<>();
        search = EncodeUtils.urlDecode(search);
        try {
            Document document = Jsoup.connect(SEARCH_URL + search)
                    //需要加上userAgent才能伪装成浏览器而不会被网站屏蔽IP
                    //(这种做法可能也会被某些网站拉黑IP一段时间，由于不太稳定到底是不是代码的问题，还在测试中...)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                    //加上cookie信息
                    .cookie("auth", "token")
                    //设置超时
                    .timeout(30000)
                    //用get()方式请求网址，也可以post()方式
                    .get();
            Elements result_list = document.getElementsByClass("result-list");
            if(ObjectUtils.isEmpty(result_list)){
                return list;
            }
            for(Element element : result_list.get(0).children()){
                Book book = new Book();
                // 添加封面
                Elements pic_elements = element.getElementsByClass("result-game-item-pic-link-img");
                if(ObjectUtils.isNotEmpty(pic_elements)){
                    String picUrl = pic_elements.get(0).attr("src");
                    book.setCoverUri(picUrl);
                }

                // 添加书名
                Elements title_elements = element.getElementsByAttributeValue("cpos", "title");
                if(ObjectUtils.isNotEmpty(title_elements)){
                    String title = title_elements.get(0).attr("title");
                    String href = title_elements.get(0).attr("href");
                    book.setName(title);
                    book.setBookUrl(new AbstractMap.SimpleEntry<>(ENGINE_NAME , href));
                }

                // 添加描述
                Elements desc_elements = element.getElementsByClass("result-game-item-desc");
                if(ObjectUtils.isNotEmpty(desc_elements)){
                    String desc = desc_elements.get(0).text();
                    book.setDescription(desc);
                }

                // 添加作者
                Elements info_elements = element.getElementsByClass("result-game-item-info-tag");
                for(int i = 0 ; i < info_elements.size() ; i ++){
                    String info = info_elements.get(i).child(1).text().trim();
                    switch (i){
                        case 0:
                            // 作者
                            book.setAuthor(info);
                            break;
                        case 1:
                            // 类型
                            break;
                        case 2:
                            // 更新时间
                            book.setLatestTime(info);
                            break;
                        case 3:
                            // 最新章节
                            book.setLatestChapter(info);
                            break;
                    }
                }
                Log.i("engine" , book.toString());
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book initBookChapters(Book book) {
        return null;
    }

    @Override
    public Book.Chapter initChapter(Book.Chapter chapter) {
        return null;
    }
}
