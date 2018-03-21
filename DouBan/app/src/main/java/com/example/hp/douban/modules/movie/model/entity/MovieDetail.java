package com.example.hp.douban.modules.movie.model.entity;

import java.util.List;

/**
 * Created by HP on 2017/5/13.
 */

public class MovieDetail {
    public String title;
    public Rate rating;
    public String year;
    public MovieImage images;
    public List<String> countries;
    public List<String> genres;
    public int collect_count;  //看过人数
    public String summary;  //简介
    public int ratings_count;  //评分人数


    public class MovieImage {
        public String small;
        public String large;
        public String medium;
    }
    public class Rate {
        public int max;
        public int average;
        public String stars;
        public int min;
    }

}
