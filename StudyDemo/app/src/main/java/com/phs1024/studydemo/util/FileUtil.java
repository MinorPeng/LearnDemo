package com.phs1024.studydemo.util;

import android.net.Uri;
import android.os.Environment;

import com.phs1024.studydemo.report.bean.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PHS1024
 * @date 2019/10/24 19:44:40
 */
public class FileUtil {

    private FileUtil() {

    }

    public static List<Music> getMusics() {
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        if (musicDir.exists()) {
            File[] musics = musicDir.listFiles();
            if (musics != null) {
                List<Music> musicList = new ArrayList<>();
                for (File file : musics) {
                    musicList.add(new Music(file.getName(), file.getPath()));
                }
                return musicList;
            }
        }
        return null;
    }

    public static Uri getVideo() {
        File videoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if (videoDir.exists()) {
            File[] moves = videoDir.listFiles();
            if (moves != null) {
                return Uri.fromFile(moves[0]);
            }
        }
        return null;
    }
}
