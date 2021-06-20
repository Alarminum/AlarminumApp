package com.alarminum.alarminumapp.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final int THREAD_COUNT = 2;
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    public static AppExecutors getInstance() { return InstanceHolder.instance; }

    private static final class InstanceHolder {
        private static AppExecutors instance = new AppExecutors(
                new DiskIOThreadExecutor(),
                new NetWorkIOThreadExecutor(THREAD_COUNT),
                new mainThreadExecutor()
        );
    }

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    private static class mainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    static class DiskIOThreadExecutor implements Executor {
        private final Executor myDiskIO;

        public DiskIOThreadExecutor() {
            myDiskIO = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(Runnable command) {
            myDiskIO.execute(command);
        }
    }

    static class NetWorkIOThreadExecutor implements Executor {
        private final Executor myNetworkIO;

        public NetWorkIOThreadExecutor(int count) {
            myNetworkIO = Executors.newFixedThreadPool(count);
        }

        @Override
        public void execute(Runnable command) {
            myNetworkIO.execute(command);
        }
    }

    public Executor getDiskIO() { return diskIO; }
    public Executor getNetworkIO() { return networkIO; }
    public Executor getMainThread() { return mainThread; }
}
