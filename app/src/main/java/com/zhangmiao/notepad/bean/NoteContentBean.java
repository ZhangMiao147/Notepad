package com.zhangmiao.notepad.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 心情的content
 * Created by zhangmiao on 2018/8/12.
 */

public class NoteContentBean {

    /**
     * 文章
     */
    private String article;

    /**
     * 标题
     */
    private String title;

    private int font;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public int getFont() {
        return font;
    }

    @Override
    public String toString() {
        return "NoteContentBean{" +
                "article='" + article + '\'' +
                ", title='" + title + '\'' +
                ", font='" + font + '\'' +
                '}';
    }

    public NoteContentBean jsonToBean(String json) {
        NoteContentBean bean = new NoteContentBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            bean.setArticle(jsonObject.getString("article"));
            bean.setTitle(jsonObject.getString("title"));
            bean.setFont(jsonObject.getInt("font"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("article", article);
            jsonObject.put("title", title);
            jsonObject.put("font", font);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
