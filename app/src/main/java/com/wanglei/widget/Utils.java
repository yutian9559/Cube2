package com.wanglei.widget;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 
 * @author 王雷) 工具类，把一些平时编程时常用到的函数集中写在了这里
 */
public class Utils {
    private static volatile Handler mSubThreadHandler = null;

    /**
	 * 获取子线程Handler
	 * 
	 * @return 子线程Handler
	 */
	public static Handler getSubThreadHandler() {
		if (mSubThreadHandler == null) {
			synchronized (Utils.class) {
				if (mSubThreadHandler == null) {
                    HandlerThread mSubThread = new HandlerThread("SubThread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
					mSubThread.start();
					mSubThreadHandler = new Handler(mSubThread.getLooper());
				}
			}
		}
		return mSubThreadHandler;
	}

}