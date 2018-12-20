package com.reader.hanli.reader.data.engine.impl;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanli on 2018/5/22.
 * 网站：https://www.bequgezw.com/
 */
public class BequgeEngineImpl extends BaseEngineImpl {


    private static int RETRY_MAX_NUM = 3;

    private int mRetryNum;

    @Override
    public List<Book> searchBook(String search) {
        //获取整个页面文件
        List<Book> list = new ArrayList<>();
        search = EncodeUtils.urlDecode(search);
        try {
            Document document = getDocument(getSearchUrl() + search);
            Elements result_list = document.select("#main").select("div.novelslist2").select("ul").select("li");
            if(ObjectUtils.isEmpty(result_list)){
                return list;
            }
            for(Element element : result_list){
                Book book = new Book();
                // 添加封面
                // 无封面 暂不添加

                // 添加书名
                Elements title_elements = element.select("span.s2").select("a");
                if(ObjectUtils.isNotEmpty(title_elements)){
                    String title = title_elements.get(0).text();
                    String href = title_elements.get(0).attr("abs:href");
                    book.setName(title);
                    book.setBookUrl(href);
                    book.setEngineName(getEngineName());
                } else {
                    continue;
                }

                // 添加描述
                // 暂无描述

                // 添加作者
                Elements author_elements = element.select("span.s4");
                if(ObjectUtils.isNotEmpty(author_elements)){
                    book.setAuthor(author_elements.get(0).text());
                }

                // 更新时间
                Elements updatetime_elements = element.select("span.s6");
                if(ObjectUtils.isNotEmpty(updatetime_elements)){
                    book.setLatestTime(updatetime_elements.get(0).text());
                }


                // 最新章节
                Elements lastchapter_elements = element.select("span.s3").select("a");
                if(ObjectUtils.isNotEmpty(lastchapter_elements)){
                    book.setLatestChapter(lastchapter_elements.get(0).attr("title"));
                }

                list.add(book);
            }

            mRetryNum = 0;
        } catch (Exception e) {
            e.printStackTrace();
            if(mRetryNum <= RETRY_MAX_NUM){
                mRetryNum ++;
                LogUtils.i("重新尝试搜索");
                list = searchBook(search);
            }
        }
        return list;
    }

    @Override
    public Book initBookChapters(Book book) {
        if(ObjectUtils.isNotEmpty(book.getBookUrl())){
            try{
                Document document = getDocument(book.getBookUrl());
                Elements chapter_list = document.getElementById("list").child(0).children();
                int chapterId = 0;
                List<Chapter> chapters = new ArrayList<>();
                // 这个网站，章节列表中会有两个dt，第一个表示最新章节部分开始，第二个表示正文部分开始
                int dt_num = 0;
                for(Element element : chapter_list){
                    if(element.nodeName().equals("dt")){
                        dt_num ++;
                    }else if(dt_num >= 2){
                        // 正文部分开始
                        Chapter chapter = new Chapter();
                        Element chapter_element = element.child(0);
                        String chapterUrl = chapter_element.attr("href");
                        String chapterName = chapter_element.text();
                        chapter.setName(chapterName);
                        chapter.setEngineName(getEngineName());
                        chapter.setChapterUrl(getChapterUrl(book.getBookUrl() , chapterUrl));
                        chapter.setId(chapterId);
                        chapter.setBookId(book.getBookId());
                        chapterId ++ ;
                        chapters.add(chapter);
                    }
                }
                book.setChapters(chapters);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return book;
    }

    @Override
    public Chapter initChapter(Chapter chapter) {
        if(ObjectUtils.isNotEmpty(chapter)){
            try{
                Document document = getDocument(chapter.getChapterUrl());
                Element content_element = cleanContentElement(document.getElementById("content"));
                String content = getContentFromHtml(content_element.html());
                chapter.setContent(content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return chapter;
    }

    @Override
    public String getSearchUrl() {
        return "https://www.bequgezw.com/search.html?name=";
    }

    @Override
    public String getEngineName() {
        return "bequge";
    }

    @Override
    public String getEngineAlias() {
        return "笔趣阁2";
    }
}
