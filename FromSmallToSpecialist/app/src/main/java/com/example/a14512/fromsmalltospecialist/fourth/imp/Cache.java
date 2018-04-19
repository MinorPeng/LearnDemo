package com.example.a14512.fromsmalltospecialist.fourth.imp;

/**
 * @author 14512 on 2018/4/10
 */

public interface Cache<K, V> {

    /**
     * 取出
     * @param key
     * @return
     */
    public V get(K key);

    /**
     * 写入
     * @param key
     * @param value
     */
    public void put(K key, V value);

    /**
     * 删除
     * @param key
     */
    public void remove(K key);
}
