package com.example.lj.ljplayerhd.rxjava;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.MainActivity;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.google.gson.Gson;
import com.googlecode.gentyref.GenericTypeReflector;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.IParserRequest;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wh on 2016/11/24.
 */

public class RxNoHttp {

    /**
     * @param mActLifecycleProvider
     * @param url
     * @param subscribe
     * @param <T>
     */
    public static <T> void requestJavaBean(ActivityLifecycleProvider mActLifecycleProvider, String url, Action1<Response<T>> subscribe) {
        requestJavaBean(mActLifecycleProvider, url, RequestMethod.GET, subscribe);
    }

    /**
     * @param mFragLifecycleProvider
     * @param url
     * @param subscribe
     * @param <T>
     */
    public static <T> void requestJavaBean(FragmentLifecycleProvider mFragLifecycleProvider, String url, Action1<Response<T>> subscribe) {
        requestJavaBean(mFragLifecycleProvider, url, RequestMethod.GET, subscribe);
    }

    public static <T> void requestJavaBean(ActivityLifecycleProvider mActLifecycleProvider, String url, RequestMethod method, final Action1<Response<T>> subscribe) {
        try {
            Type responseType = GenericTypeReflector.getTypeParameter(subscribe.getClass(), Action1.class.getTypeParameters()[0]);
            Type tType = GenericTypeReflector.getTypeParameter(responseType, Response.class.getTypeParameters()[0]);
            JavaBeanRequest<T> request = new JavaBeanRequest<T>(url, (Class<T>) tType);
        } catch (Exception e) {
            if (e != null)
                showNetError(e);
        }
    }

    public static <T> void requestJavaBean(FragmentLifecycleProvider mFragLifecycleProvider, String url, RequestMethod method, Action1<Response<T>> subscribe) {
        try {
            Type responseType = GenericTypeReflector.getTypeParameter(subscribe.getClass(), Action1.class.getTypeParameters()[0]);
            Type tType = GenericTypeReflector.getTypeParameter(responseType, Response.class.getTypeParameters()[0]);
            JavaBeanRequest<T> request = new JavaBeanRequest<T>(url, (Class<T>) tType);
            requestImp(mFragLifecycleProvider, request, subscribe);
        } catch (Exception e) {
            if (e != null)
                showNetError(e);
        }
    }

    public static <T> void request(ActivityLifecycleProvider mActLifecycleProvider, Request<T> request, Action1<Response<T>> subscribe) {
        requestImp(mActLifecycleProvider, request, subscribe);
    }

    public static <T> void request(FragmentLifecycleProvider mFragLifecycleProvider, Request<T> request, Action1<Response<T>> subscribe) {
        requestImp(mFragLifecycleProvider, request, subscribe);
    }


    public static <T> void request(String url, Action1<Response<T>> subscribe) {
        Type responseType = GenericTypeReflector.getTypeParameter(subscribe.getClass(), Action1.class.getTypeParameters()[0]);
        Type tType = GenericTypeReflector.getTypeParameter(responseType, Response.class.getTypeParameters()[0]);
        JavaBeanRequest<T> request = new JavaBeanRequest<T>(url, (Class<T>) tType);
        requestImp(request, subscribe);
    }

    private static <T> void requestImp(ActivityLifecycleProvider mActLifecycleProvider, Request<T> request, final Action1<Response<T>> subscribe) {
        try {
            Observable.just(request)
                    .map(new Func1<Request<T>, Response<T>>() {
                        @Override
                        public Response<T> call(Request<T> request) {
                            return NoHttp.startRequestSync(request);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mActLifecycleProvider.<Response<T>>bindToLifecycle())
                    .subscribe(new Subscriber<Response<T>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            showNetError(e);
                        }

                        @Override
                        public void onNext(Response<T> response) {
                            subscribe.call(response);
                        }
                    });
        } catch (Exception e) {
            if (e != null)
                showNetError(e);
        }
    }

    private static <T> void requestImp(FragmentLifecycleProvider mFragLifecycleProvider, Request<T> request, final Action1<Response<T>> subscribe) {
        try {
            Observable.just(request)
                    .map(new Func1<Request<T>, Response<T>>() {
                        @Override
                        public Response<T> call(Request<T> request) {
                            return NoHttp.startRequestSync(request);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mFragLifecycleProvider.<Response<T>>bindToLifecycle())
                    .subscribe(new Subscriber<Response<T>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            showNetError(e);
                        }

                        @Override
                        public void onNext(Response<T> response) {
                            subscribe.call(response);
                        }
                    });
        } catch (Exception e) {
            if (e != null)
                showNetError(e);
        }
    }

    private static <T> void requestImp(IParserRequest<T> request, final Action1<Response<T>> subscribe) {
        try {
            Observable.just(request)
                    .map(new Func1<IParserRequest<T>, Response<T>>() {
                        @Override
                        public Response<T> call(IParserRequest<T> request) {
                            return NoHttp.startRequestSync(request);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Response<T>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            showNetError(e);
                        }

                        @Override
                        public void onNext(Response<T> response) {
                            subscribe.call(response);
                        }
                    });
        } catch (Exception e) {
            if (e != null)
                showNetError(e);
        }
    }


    private static void showNetError(Throwable e) {
        // 提示异常信息。
        if (e instanceof NetworkError) {// 网络不好
            ToastUtil.showShort(R.string.error_please_check_network);
        } else if (e instanceof TimeoutError) {// 请求超时
            ToastUtil.showShort(R.string.error_timeout);
        } else if (e instanceof UnKnownHostError) {// 找不到服务器
            ToastUtil.showShort(R.string.error_not_found_server);
        } else if (e instanceof URLError) {// URL是错的
            ToastUtil.showShort(R.string.error_url_error);
        } else if (e instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtil.showShort(R.string.error_not_found_cache);
        } else if (e instanceof ProtocolException) {
            ToastUtil.showShort(R.string.error_system_unsupport_method);
        } else if (e instanceof ParseError) {
            ToastUtil.showShort(R.string.error_parse_data_error);
        } else {
            ToastUtil.showShort(R.string.error_unknow);
        }
    }
}
