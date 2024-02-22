package org.talang.wabackend.sd;

import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;


public interface DrawImageComponent {

    /*
     * 画图
     * @param options 画图参数
     * @return 画图地址
     */
    void text2Image(String taskId, Txt2ImageOptions options) throws InterruptedException;


    void extraImage(String taskId, Txt2ImageOptions txt2ImageOptions,
                    ExtraImageOptions extraImageOptions);

    String startTxt2ImageRequest(int userId, Txt2ImageOptions options);

    String startExtraImageRequest(int userId, Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions);
}
