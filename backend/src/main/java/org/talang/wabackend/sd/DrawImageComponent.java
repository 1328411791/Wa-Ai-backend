package org.talang.wabackend.sd;


public interface DrawImageComponent {

    /*
     * 画图
     * @param options 画图参数
     * @return 画图地址
     */
    void text2Image(String taskId, Integer user_Id, String options);


    void extraImage(String taskId, Integer user_Id, String extraImageOptions);
}
