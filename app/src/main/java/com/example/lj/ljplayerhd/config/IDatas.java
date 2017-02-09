package com.example.lj.ljplayerhd.config;

import java.io.File;

import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.utils.MobileMng;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

public interface IDatas {

    public final static String PACKAGE = "com.vstar3d.player4hd";

    public static class NewVersions {
        // public final static String UPDATE_SERVER =
        // "http://www.cnliti.com/api/rest.php?app=player&def=hd&mod=update";
        // public final static String UPDATE_PATH =
        // "http://www.3dv.cn/attachment/";
        public final static String UPDATE_SERVER = "http://update.3dv.cn/";
        public final static String UPDATE_VER = "3DVPlayerHD.js";
        public static String UPDATE_APKNAME = UPDATE_SERVER + "3DVPlayerHD.apk";
    }

    public final static String VERNAME = "verName";

    public final static String VERCODE = "verCode";

    public final static String APKNAME = "apkname";

    public final static String VERTEXT = "verText";
    public final static String VERTEXTEN = "verTextEn";

    public final class Messages {
        public static final int CONFIG_INIT = 0x0001;
        public static final int START_REGISTER = 0x0002;
        public static final int RETRUN_LOGIN = 0x0003;

        public static final int REQUEST_DOWNLOAD = 0x0002;
        public static final int REQUEST_PLAY = 0x0003;
    }

    public final class WebService {
        // 服务器维护信息， 如果不为空需要提示
        public static String MAINTAN_MESSAGE = "";
        // 视频路径
        public static String DUMMY_VIDEO_PATH = "DUMMY_VIDEO_PATH";
        public static String VIDEO_PATH;
        //public static String API_ROOT_ROOT_PATH = "http://www.cnliti.com/api/rest.php?app=player&def=hd&ver=";
        public static String API_ROOT_ROOT_PATH = "http://rest.3dv.cn/app=player&def=hd&ver=";
        public static String API_ROOT_ROOT_PATH2 = "http://rest.3dv.cn/app=player&def=hd&fork=";
        public static String API_ROOT_PATH = API_ROOT_ROOT_PATH + "1003&";
        public static String VIDEO_LIST_PATH;

        // 新版本1003请求地址
        public static String API_ROOT_PATH2 = API_ROOT_ROOT_PATH2 + "1003&";
        // vr激活请求
        public static String VR_CHECK = API_ROOT_PATH2 + "mod=vractive";

        // 图片根路径
        public static String THUMB_PATH;
        // 字幕路径
        public static String SUBTITLE_PATH;
        // 收藏 的 图片根路径
        public static String COLLECT;
        // 会员收藏的分页根路径
        public static String MEMBERCENTER_FAVOURITE_PATH;
        // 会员首页请求路径
        public static String MEMBERCENTER_PATH;
        // 会员头像的 根路径
        public static final String AVATAR = "http://bbs.cnliti.com/uc/avatar.php?";
        // 会员评论删除根路径
        public static String COMMENTDELETE_PATH;

        // 会员收藏的 全部数据路径
        public static String ALLFAV_PATH;

        // 收藏本片 根路径
        public static String ADDFAV_PATH;
        // 取消收藏 根路径
        public static String CANCLEFAV_PATH;
        // 注册的根路径
        public static String WEB_REGISTER;
        // 登陆的 根路径
        public static String WEB_LOGIN;
        // 分类
        public static String CATEGORY_PATH;
        /**
         * @modular 影片详情
         * @param mark
         * 类型
         * @param mid
         * 影片ID
         */

        //反馈
        public static String FEEDBACK_PATH;

        //详情
        public static String VIDEO_DETAIL_PATH;

        //推出登陆
        public static String LOGINOUT_PATH;

        /**
         * @modular 评论列表
         * @param mark
         * 类型
         * @param mid
         * 影片ID
         */
        public static String VIDEO_COMMENTLIST_PATH;

        public static String VIDEO_COMMENTRATING_PATH = API_ROOT_PATH
                + "&mod=mark";

        public static String VIDEO_COMMENTSUBMIT_PATH = API_ROOT_PATH
                + "mod=comment";

        /**
         * @modular 记录影片播放次数
         * @param mark
         * 类型
         * @param mid
         * 影片ID
         */
        public static String WEB_PLAY_TIMES;

        public static String SEARCH_VIDEO;

        public static String SEARCH_SUBTITLE;

        // 发送mac、机型、用户uid，更新一些全局常量
        // context主要用于获取一些android信息，如mac和包版本号
        public static void UpdateGlobalSettingsFromServer(Context context) {
            try {
                final String mac = MobileMng.getMac(context);
                boolean isZh = MobileMng.getLanguage();
                WebService.API_ROOT_PATH = WebService.API_ROOT_ROOT_PATH
                        + MobileMng.getVerCode(context)
                        + "&mac="
                        + Uri.encode(mac)
                        + "&machine="
                        + Uri.encode(android.os.Build.MODEL)
                        + "&lang="
                        + (isZh ? "zh" : "en") + "&";
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            Log.e("info", "API_ROOT_PATH:" + API_ROOT_PATH);
            VIDEO_LIST_PATH = API_ROOT_PATH + "mod=movielist";
            CATEGORY_PATH = API_ROOT_PATH + "mod=moviecategory";
            VIDEO_DETAIL_PATH = API_ROOT_PATH + "mod=info";
            VIDEO_COMMENTLIST_PATH = API_ROOT_PATH + "mod=commentlist";
            WEB_PLAY_TIMES = API_ROOT_PATH + "mod=click";
            SEARCH_VIDEO = API_ROOT_PATH + "mod=search";
            SEARCH_SUBTITLE = API_ROOT_PATH + "mod=subtitle&json=1";
            SUBTITLE_PATH = "http://www.cnliti.com/uploadfile/subtitle/";
            // 收藏 的 图片根路径
            COLLECT = "http://rest.3dv.cn/";
            // 会员收藏的分页根路径
            MEMBERCENTER_FAVOURITE_PATH = API_ROOT_PATH
                    + "&mod=membercenter&action=favorites";
            // 会员首页请求路径
            MEMBERCENTER_PATH = API_ROOT_PATH
                    + "&mod=membercenter&action=index";

            // 会员评论删除根路径
            COMMENTDELETE_PATH = API_ROOT_PATH
                    + "&mod=membercenter&action=commentdel";

            // 会员收藏的 全部数据路径
            ALLFAV_PATH = API_ROOT_PATH
                    + "mod=membercenter&action=allfavorites";

            // 收藏本片 根路径
            ADDFAV_PATH = API_ROOT_PATH + "mod=favorites&action=add";
            // 取消收藏 根路径
            CANCLEFAV_PATH = API_ROOT_PATH + "mod=favorites&action=del";
            // 注册的根路径
            WEB_REGISTER = API_ROOT_PATH + "&mod=member&action=register";
            // 登陆的 根路径
            WEB_LOGIN = API_ROOT_PATH + "&mod=member&action=login";
            // 分类
            CATEGORY_PATH = API_ROOT_PATH + "mod=moviecategory";
            LOGINOUT_PATH = API_ROOT_PATH + "mod=member&action=logout";
            /**
             * @modular 影片详情
             * @param mark
             *            类型
             * @param mid
             *            影片ID
             */
            FEEDBACK_PATH = API_ROOT_PATH + "mod=feedback";
        }
    }

    public final class SharedPreferences {
        public static final String FILE_NAME = "vstar3d";
        public static final String USERINFO = "userinfo";

        public static final String SID = "sid";

        public static final String USERNAME = "USERNAME";
        public static final String LASTTIME = "LASTTIME";
        /**
         * 激活状态
         */
        public static final String OPTION_VRKEY = "option_vrkey";

        /**
         * 字幕字号 存的是index
         */
        public static final String SUBTITLE_FONTSIZE_INDEX = "subtitle_fontSize_index";
        /**
         * 出屏效果 存的是index
         */
        public static final String SUBTITLE_ENTRYSCREEN_INDEX = "subtitle_entryScreen_index";
        /**
         * 字体样式 存的是名称
         */
        public static final String SUBTITLE_FONTSTYLE = "subtitle_fontStyle";
        /**
         * 字体延迟 存的是毫秒
         */
        public static final String SUBTITLE_DELAY = "subtitle_delay";

        /**
         * 设置提醒开关 -> 移动网络提醒
         */
        public static final String REMIND_MOBILENETWORK = "remind_mobileNetWork";
        /**
         * 设置提醒开关 -> 无线网络断开
         */
        public static final String REMIND_NETWORKDISCONNECT = "remind_netWork_disconnect";
        /**
         * 设置提醒开关 -> 字幕载入
         */
        public static final String REMIND_SUBTITLE = "remind_subtitle";
        /**
         * 设置提醒开关 -> 小技巧
         */
        public static final String REMIND_TIPS = "remind_tips";
        /*
         * 设置提醒开关 -> 软件升级
         */
        public static final String REMIND_SOFTUPGRADE = "remind_softWare_upgrade";
        /*
         * 设置提醒开关 -> 记录播放位置
         */
        public static final String OPTION_SAVEPOSITION = "option_savePosition";
        /*
         * 设置提醒开关 -> 直接从记录位置播放
         */
        public static final String OPTION_TIP_PLAYPOSITION = "option_tip_playPosition";
        /*
         * 设置提醒开关 -> 下载电影时自动下载字幕
         */
        public static final String OPTION_DOWNLOADSUBTITLE = "option_download_subtitle";
        /*
         * 设置提醒开关 -> 红蓝格式
         */
        public static final String OPTION_HONGLAN = "option_honglan";
        /*
         * 设置提醒开关 -> VR/TR模式开关
         */
        public static final String OPTION_VRTV = "option_vrtv";
        /*
         * 设置提醒开关 -> 2/3高度缩放
         */
        public static final String OPTION_ZOOM = "option_zoom";
        /*
         * 设置提醒开关 -> 翻转暂停
         */
        public static final String FUNCTION_FLIP = "function_flip";
        /**
         * 系统播放 存的是boolean
         */
        public static final String SETUP_SYSPLAYPRI = "setup_sysPlayPri";
        /*
         * 扫描路径
         */
        public static final String SCAN_DIR = "scan_dir";
        /*
         * 清晰度
         */
        public static final String DEFINITION_INDEX = "definition_index";

        /**
         * 亮度
         */
        public static final String BRIGHTNESS = "brightness";

        /*
         * 数据分类->中文系统
         */
        public static final String TYPE_ZH = "type_zh";
        /*
         * 数据分类->英文系统
         */
        public static final String TYPE_EN = "type_en";
        /*
         * 搜索返回引导界面->是否第一次点击
         */
        public static final String SEARCH_ISFIRST = "search_isfirst";
        public static final String PLAY_ISFIRST = "play_isfirst";
    }

    public final class DefaultValues {
        /**
         * 激活状态
         */
        public static final boolean OPTION_VRKEY = false;

        /**
         * 字幕设置 -> 字幕字号
         */
        public static final int SUBTITLE_FONTSIZE_INDEX = 3;
        /**
         * 字幕设置 -> 出屏效果
         */
        public static final int SUBTITLE_ENTRYSCREEN_INDEX = 4;
        /**
         * 字幕设置 -> 字幕延迟
         */
        public static final int SUBTITLE_DELAY = 0;
        /**
         * 数字字体名称，放在asset下
         */
        public static final String DIGITAL_FONT_PATH = "Digital-7Mono.TTF";
        /**
         * 系统字体目录
         */
        public static final String FONT_PATH = "system/fonts/";
        /**
         * 字体格式
         */
        public static final String FONT_END = ".ttf";
        /**
         * 设置提醒开关 -> 移动网络提醒
         */
        public static final boolean REMIND_MOBILENETWORK = true;
        /**
         * 设置提醒开关 -> 无线网络断开
         */
        public static final boolean REMIND_NETWORKDISCONNECT = true;
        /**
         * 设置提醒开关 -> 字幕载入
         */
        public static final boolean REMIND_SUBTITLE = true;
        /**
         * 设置提醒开关 -> 小技巧
         */
        public static final boolean REMIND_TIPS = true;
        /*
         * 设置提醒开关 -> 软件升级
         */
        public static final boolean REMIND_SOFTUPGRADE = true;
        /*
         * 设置提醒开关 -> 记录播放位置
         */
        public static final boolean OPTION_SAVEPOSITION = true;
        /*
         * 设置提醒开关 -> 直接从记录位置播放
         */
        public static final boolean OPTION_TIP_PLAYPOSITION = false;
        /*
         * 设置提醒开关 -> 下载电影时自动下载字幕
         */
        public static final boolean OPTION_DOWNLOADSUBTITLE = true;
        /*
         * 设置提醒开关 -> 红蓝播放
         */
        public static final boolean OPTION_HONGLAN = true;
        /*
         * 设置提醒开关 -> VR/TR模式开关
         */
        public static final boolean OPTION_VRTV = false;
        /*
         * 设置提醒开关 -> 2/3高度缩放
         */
        public static final boolean OPTION_ZOOM = false;
        /*
         * 设置提醒开关 -> 翻转暂停
         */
        public static final boolean FUNCTION_FLIP = false;
        /*
         * 搜索返回引导界面->是否第一次点击
         */
        public static final boolean SEARCH_ISFIRST = true;
        /*
         * 视频播放界面->是否第一次进入
         */
        public static final boolean PLAY_ISFIRST = true;
        /*
         * 下载路径
         */
        private static final String DOWNLOAD_DIR = "3DVPlayerHD" + File.separator
                + "video" + File.separator;

        public static String getDonwloadDirectory() {
            String path = Environment.getExternalStorageDirectory()
                    + File.separator + DefaultValues.DOWNLOAD_DIR;
            File f = new File(path);
            if (!f.exists())
                f.mkdirs();
            return path;
        }

        /*
         * 字幕目录
         */
        private static final String DIRECTORY_SRT = "3DVPlayerHD"
                + File.separator + "srt" + File.separator;

        public static String getSrtDirectory() {
            String path = Environment.getExternalStorageDirectory()
                    + File.separator + DefaultValues.DIRECTORY_SRT;
            File f = new File(path);
            if (!f.exists())
                f.mkdirs();
            return path;
        }

        public static final String COMMENT_CONTENT = "content";
        /*
         * 清晰度
         */
        public static final int DEFINITION_INDEX = 0;

        /**
         * 亮度
         */
        public static final float BRIGHTNESS = 0.8f;

        /*
         * 扫描路径
         */
        private static final String SCAN_DIR = "3DVPlayerHD" + File.separator
                + "video" + File.separator;

        public static String getScanDir() {
            String path = Environment.getExternalStorageDirectory()
                    + File.separator/* + IDatas.DefaultValues.SCAN_DIR */;
            File f = new File(path);
            if (!f.exists())
                f.mkdirs();
            return "(" + path + ")";

        }

        /**
         * 支持的文件类型，以便扫描
         */
        public static final String[] ENDS = {".3gp", ".mp4", ".avi", ".mpeg",
                ".mpg", ".3dv", ".rmvb", ".rm", ".wmv", ".mkv", ".ts", ".mov",
                ".flv"};
        public static final int HISTORY_COUNT = 15;

        /*
         * 数据分类->中文系统
         */
        public static final String TYPE_ZH = "[{\"mark\":\"video\",\"text\":\"电影\"},{\"mark\":\"trailers\",\"text\":\"片花\"},{\"mark\":\"short\",\"text\":\"短片\"},{\"mark\":\"demo\",\"text\":\"演示\"}]";
        /*
         * 数据分类->英文系统
         */
//		public static final String TYPE_EN = "[{\"mark\":\"video\",\"text\":\"Movie\"},{\"mark\":\"trailers\",\"text\":\"Trailers\"},{\"mark\":\"short\",\"text\":\"Short\"},{\"mark\":\"demo\",\"text\":\"Demo\"},{\"mark\":\"youtube\",\"text\":\"Youtube\"},{\"mark\":\"YNR\",\"text\":\"Network\"}]";
        public static final String TYPE_EN = "[{\"mark\":\"youtube\",\"text\":\"Youtube\"}]";
    }

    public final class JsonKey {
        public static final String ID = "id";
        public static final String COVER = "cover";
        public static final String NAME = "name";
        public static final String PICADDR = "picaddr";
        public static final String TIMEALL = "timeall";
        public static final String DETAILS = "details";
        public static final String SCORE = "score";
        public static final String ADDRESS = "address";
        public static final String CAPTION = "caption";
        public static final String CAPTIONEN = "captionen";
        public static final String LABEL = "label";
        public static final String ITEM = "item";
        public static final String TEXT = "text";
        public static final String INDEX = "index";
        public static final String AREA = "area";
        public static final String KIND = "kind";
        public static final String CATEGORY = "category";
        public static final String CLAZZ = "clazz";
        public static final String TIME = "time";
        public static final String DIRECTOR = "director";
        public static final String PLAYACTOR = "playactor";
        public static final String SYNOPSIS = "synopsis";
        public static final String SIZE = "size";
        public static final String LISTID = "listid";
        public static final String MODEL = "model";
        public static final String USERNAME = "username";
        public static final String UID = "uid";
        public static final String CONTENT = "content";
        public static final String CONTACT = "contact";

        public static final String STRENGHT = "extcredits1";
        public static final String MANA = "extcredits3";
        public static final String STEREO = "extcredits5";
        public static final String UPLOAD = "extcredits6";
        public static final String DOWNLOAD = "extcredits7";
        public static final String SHARE = "shareradio";
        public static final String COUNTCREDIT = "countcredit";
        public static final String CREDITSFORMULAEXP = "creditsformulaexp";
        public static final String CREDITLOG = "creditlog";
        public static final String OPERATION = "operation";
        public static final String DATELINE = "dateline";
        public static final String CREDIT = "credit";
        public static final String OPTYPE = "optype";
        public static final String CREDITS = "credits";
        public static final String FAVORITES = "favorites";
        public static final String MARK = "mark";
        public static final String MID = "mid";
        public static final String CID = "cid";
        public static final String FAVORITEMULTI = "favoritemulti";
        public static final String OPINFO = "opinfo";
        public static String COMMENT_CONTENT = "content";
        public static String SPLITPAGE = "splitpage";
        public static String PAGECONTENT = "pagecontent";
        public static String TITLE = "title";
        public static String MSCORE = "score";
        public static String MACHINE = "machine";
        public static String EFFECT = "effect";
        public static final String YEAR = "year";
        public static final String SNUM = "snum";
        public static final String TYPE = "type";
        public static final String TRACK = "track";
        public static final String TIMELEN = "timelen";
        public static final String SRT_ZH = "srt_zh";
        public static final String SRT_EN = "srt_en";
        public static final String ORDER_TIME = "time";
        public static final String ORDER_HITS = "hits";
        public static final String ORDER_RATING = "rating";
        public static final String ORDER_EFFECT = "effect";
        public static final String ORDER = "order";
        public static final String HIGH = "high";
        public static final String STANDARD = "standard";
        public static final String SMOOTH = "smooth";
        public static final String DEFINITION = "definition";
    }


}
