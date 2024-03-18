package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.StaticImage;

/**
 * @author lihan
 * @description 针对表【sd_static_image(静态文件储存)】的数据库操作Service
 * @createDate 2024-02-22 19:00:22
 */
public interface StaticImageService extends IService<StaticImage> {

    String saveImage(String fileName, String readPath);

    String saveImage(String fileName, String readPath, Integer userId);

    String saveImage(String fileName, String readPath, String hash, Integer userId);

    String getStaticImagePathById(String imageId);

}
