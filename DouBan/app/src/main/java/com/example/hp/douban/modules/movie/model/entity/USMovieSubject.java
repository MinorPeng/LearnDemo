package com.example.hp.douban.modules.movie.model.entity;

import java.util.List;

/**
 * Created by HP on 2017/5/13.
 */

public class USMovieSubject {
    public String title;
    public String data;
    public List<UsBox> subjects;


    public static class UsBox {
            public int rank;
            public int box;
            public boolean IsNew;
            public Movie subject;

    }
}
