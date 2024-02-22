package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.StaticImageMapper;
import org.talang.wabackend.model.generator.StaticImage;
import org.talang.wabackend.service.StaticImageService;

/**
 * @author lihan
 * @description 针对表【sd_static_image(静态文件储存)】的数据库操作Service实现
 * @createDate 2024-02-22 19:00:22
 */
@Service
public class StaticImageServiceImpl extends ServiceImpl<StaticImageMapper, StaticImage>
        implements StaticImageService {

    @Override
    public void saveImage(String fileName, String readPath) {
        StaticImage staticImage = new StaticImage();
        staticImage.setImageName(fileName);
        staticImage.setFilePath(readPath);
        save(staticImage);
    }
}




