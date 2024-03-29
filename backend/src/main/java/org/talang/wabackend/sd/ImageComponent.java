package org.talang.wabackend.sd;

public interface ImageComponent {

    /*
     * 保存图片
     * @param options 保存图片参数
     * @return 保存图片地址
     */
    // 传入图片文件
    String saveImage(byte[] image, Integer userId);

    String saveImage(byte[] image, Integer userId, String fileName);

    byte[] getImageById(String id);

    boolean removeImage(String id);
}
