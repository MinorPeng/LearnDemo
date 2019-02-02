package com.example.hp.douban.modules.movie.model.entity;

import java.util.List;

/**
 * Created by HP on 2017/5/11.
 */

public class Movie {
    public Rate rating;
    public List<String> genres;
    public String title;
    public List<Casts> casts;
    public String collect_count;
    public String original_title;
    public String subtype;
    public List<Directors> directors;
    public String year;
    public MovieImage images;
    public String id;

    public static class Rate {
        public int max;
        public float average;
        public String stars;
        public int min;
    }

    public static class MovieImage {
        public String small;
        public String large;
        public String medium;
    }

    public static class Casts {
        public String alt;
        public Avatars avatars;
        public String name;
        public String id;

        public static class Avatars {
            public String small;
            public String large;
            public String medium;
        }
    }

    public static class Directors {
        public String alt;
        public Avatars avatars;
        public String name;
        public String id;

        public static class Avatars {
            public String small;
            public String large;
            public String medium;
        }
    }
}
