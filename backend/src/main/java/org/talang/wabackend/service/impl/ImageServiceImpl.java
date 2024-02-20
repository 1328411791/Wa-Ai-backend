package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ImageMapper;
import org.talang.wabackend.model.generator.Image;
import org.talang.wabackend.service.ImageService;

/**
 * @author lihan
 * @description 针对表【sd_image】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
        implements ImageService {

}




