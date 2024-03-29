package org.talang.wabackend.sd;


import org.talang.sdk.SdWebui;
import org.talang.wabackend.exception.TaskFailException;

public interface DrawImageComponent {

    /*
     * 画图
     * @param options 画图参数
     * @return 画图地址
     */
    void text2Image(String taskId, Integer user_Id, String options, SdWebui sdWebui) throws TaskFailException;


    void extraImage(String taskId, Integer user_Id, String extraImageOptions, SdWebui sdWebui) throws TaskFailException;
}
