package com.huojumu.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator
 * <p>
 * <p>
 * Date: 2017/11/2
 * Class description:
 */
public class ThreadFactoryBuilder implements ThreadFactory {

    private String name;
    private int counter;

    public ThreadFactoryBuilder(String name) {
        this.name = name;
        counter = 1;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable, name);
        thread.setName("ThreadFactoryBuilder_" + name + "_" + counter);
        return thread;
    }
}
