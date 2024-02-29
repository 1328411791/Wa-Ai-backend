package org.talang.wabackend.service;

import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.model.generator.SdImage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;

/**
* @author lihan
* @description 针对表【sd_image】的数据库操作Service
* @createDate 2024-03-01 01:16:15
*/
public interface SdImageService extends IService<SdImage> {

    void saveSdImage(String imageId, Txt2ImgResult txt2ImgResult, Integer userId);

    SdImageVo getImageById(String id);
}
