package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.mapper.StaticImageMapper;
import org.talang.wabackend.model.generator.StaticImage;
import org.talang.wabackend.service.StaticImageService;

import java.util.UUID;

/**
 * @author lihan
 * @description 针对表【sd_static_image(静态文件储存)】的数据库操作Service实现
 * @createDate 2024-02-22 19:00:22
 */
@Service
public class StaticImageServiceImpl extends ServiceImpl<StaticImageMapper, StaticImage>
        implements StaticImageService {

    @Override
    public String saveImage(String fileName, String readPath) {
        return saveImage(fileName, readPath, null);
    }

    @Override
    public String saveImage(String fileName, String readPath, Integer userId) {
        StaticImage staticImage = new StaticImage();
        String uuid = UUID.randomUUID().toString();
        staticImage.setId(uuid);
        staticImage.setImageName(fileName);
        staticImage.setUserId(userId);
        staticImage.setFilePath(readPath);
        save(staticImage);
        return uuid;
    }

    @Override
    public String saveImage(String fileName, String readPath, String hash, Integer userId) {
        StaticImage staticImage = new StaticImage();
        String uuid = UUID.randomUUID().toString();
        staticImage.setId(uuid);
        staticImage.setImageName(fileName);
        staticImage.setUserId(userId);
        staticImage.setHash(hash);
        staticImage.setFilePath(readPath);
        save(staticImage);
        return uuid;
    }

    @Override
    public String getStaticImagePathById(String imageId) {
        StaticImage staticImage = getById(imageId);
        if (staticImage == null) {
            return null;
        }
        return staticImage.getFilePath();
    }

}




