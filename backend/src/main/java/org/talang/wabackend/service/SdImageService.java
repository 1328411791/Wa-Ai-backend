package org.talang.wabackend.service;

import org.springframework.web.multipart.MultipartFile;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.SdImage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;

import java.util.Date;

/**
* @author lihan
* @description 针对表【sd_image】的数据库操作Service
* @createDate 2024-03-01 01:16:15
*/
public interface SdImageService extends IService<SdImage> {

    void saveSdImage(String imageId, Txt2ImgResult txt2ImgResult, Integer userId);

    SdImageVo getImageById(Integer userId,String id);

    Result getMyAllList(String startTimeStamp, String endTimeStamp,
                        boolean myGenerate, boolean myUpload,
                        Integer page, Integer pageSize);

    Result upLoadSdImageByUser(MultipartFile img);
}
