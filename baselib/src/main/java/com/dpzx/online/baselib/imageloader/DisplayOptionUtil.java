//package com.dpzx.online.baselib.imageloader;
//
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//
//import com.dpzx.online.baselib.R;
//import com.dpzx.online.baselib.config.Global;
//import com.dpzx.online.baselib.utils.ScreenUtil;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
//
///**
// * Create by xuqunxing on  2019/3/14
// */
//public class DisplayOptionUtil {
//    public static final DisplayImageOptions DEFAULT_OPTIONS = (new Builder()).cacheInMemory(true).cacheOnDisk(true).build();
//    public static final DisplayImageOptions VIDEO_UNIT_ITEM_OPTIONS;
//    public static final DisplayImageOptions VIDEO_ROUNDED_OPTIONS;
//    public static final DisplayImageOptions VIDEO_ROUND_ICON_OPTIONS;
//
//    public DisplayOptionUtil() {
//    }
//
//    static {
//        VIDEO_UNIT_ITEM_OPTIONS = (new Builder()).bitmapConfig(Config.ARGB_8888).cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_loading_placehold_light).showImageForEmptyUri(R.drawable.ic_loading_placehold_light).showImageOnFail(R.drawable.ic_loading_placehold_light).considerExifParams(true).build();
//        VIDEO_ROUNDED_OPTIONS = (new Builder()).bitmapConfig(Config.ARGB_8888).cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_loading_placehold_light).showImageForEmptyUri(R.drawable.ic_loading_placehold_light).showImageOnFail(R.drawable.ic_loading_placehold_light).displayer(new RoundedBitmapDisplayer(ScreenUtil.dip2px(Global.getContext(), 3.0F), 0)).considerExifParams(true).build();
//        VIDEO_ROUND_ICON_OPTIONS = (new Builder()).bitmapConfig(Bitmap.Config.ARGB_8888).cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(R.drawable.ic_default_user_icon).showImageForEmptyUri(R.drawable.ic_default_user_icon).showImageOnFail(R.drawable.ic_default_user_icon).displayer(new RoundedBitmapDisplayer(ScreenUtil.dip2px(Global.getContext(), 1000.0F), 0)).considerExifParams(true).build();
//    }
//}
