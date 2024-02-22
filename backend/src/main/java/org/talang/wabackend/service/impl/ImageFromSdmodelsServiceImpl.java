package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ImageFromSdmodelsMapper;
import org.talang.wabackend.model.generator.ImageFromSdmodels;
import org.talang.wabackend.service.ImageFromSdmodelsService;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_image_from_sdmodels】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class ImageFromSdmodelsServiceImpl extends ServiceImpl<ImageFromSdmodelsMapper, ImageFromSdmodels>
        implements ImageFromSdmodelsService {

    @Resource
    private ImageFromSdmodelsMapper imageFromSdmodelsMapper;

    @Override
    public List<String> selectImage(Integer id) {
        return imageFromSdmodelsMapper.selectImageFromSdmodels(id);
    }
}




