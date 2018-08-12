package com.zhangmiao.notepad.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 心情的content
 * Created by zhangmiao on 2018/8/12.
 */

public class MoodContentBean {

    public static final int WEATHER_FINE = 10001;
    public static final int WEATHER_RAIN = 10002;
    public static final int WEATHER_SHADE = 10003;
    public static final int WEATHER_SNOW = 10004;

    public static final int MOOD_HAPPY = 20001;
    public static final int MOOD_UNHAPPY = 20002;


    /**
     * 文章
     */
    private String article;

    /**
     * 是否加锁
     */
    private boolean islock;

    /**
     * 天气
     */
    private int weather;

    /**
     * 心情
     */
    private int mood;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "MoodContentBean{" +
                "article='" + article + '\'' +
                ", islock=" + islock +
                ", weather=" + weather +
                ", mood=" + mood +
                '}';
    }

    public MoodContentBean jsonToBean(String json) {
        MoodContentBean bean = new MoodContentBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            bean.setArticle(jsonObject.getString("article"));
            bean.setIslock(jsonObject.getBoolean("islock"));
            bean.setWeather(jsonObject.getInt("weather"));
            bean.setMood(jsonObject.getInt("mood"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("article", article);
            jsonObject.put("islock", islock);
            jsonObject.put("weather", weather);
            jsonObject.put("mood", mood);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
