package org.talang.wabackend.sd;

import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;


public interface DrawImageComponent {

    /*
     * 画图
     * @param options 画图参数
     * @return 画图地址
     */
    String text2Image(Txt2ImageOptions options);


    String extraImage(Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions);
}
