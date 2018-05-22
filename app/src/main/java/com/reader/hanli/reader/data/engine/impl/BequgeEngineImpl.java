package com.reader.hanli.reader.data.engine.impl;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanli on 2018/5/22.
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
                    book.setBookUrl(href);
                    book.setEngineName(getEngineName());
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
                Elements chapterElementlistAll = document.getElementsByClass("article_texttitleb");
                int chapterId = 0;
                List<Chapter> chapters = new ArrayList<>();
                for(Element chapterElementList : chapterElementlistAll.get(0).children()){
                    Elements elementChildrens = chapterElementList.children();
                    for(Element chapterElement : elementChildrens){
                        Chapter chapter = new Chapter();
                        Element childElement = chapterElement.child(0);
                        String chapterUrl = childElement.attr("href");
                        String chapterName = childElement.text();
                        chapter.setChapterUrl(getChapterUrl(book.getBookUrl() , chapterUrl));
                        chapter.setName(chapterName);
                        chapter.setEngineName(getEngineName());
                        chapter.setId(chapterId);
                        chapter.setBookId(book.getBookId());
                        chapterId ++;
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
                Element content_element = document.getElementById("book_text");
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
        return "http://zhannei.baidu.com/cse/search?s=17595686867322250167&q=";
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
