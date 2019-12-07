package com.phs1024.studydemo.report.base;

import android.view.View;

/**
 * @author PHS1024
 * @date 2019/10/24 19:26:39
 */
public interface OnItemBaseClickListener {

    void onClick(int position, View view);

    void onLongClick(int position, View view);
}
