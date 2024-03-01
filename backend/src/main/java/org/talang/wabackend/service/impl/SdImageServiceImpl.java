package org.talang.wabackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.model.generator.SdImage;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.service.ModelService;
import org.talang.wabackend.service.SdImageService;
import org.talang.wabackend.mapper.SdImageMapper;
import org.springframework.stereotype.Service;
import org.talang.wabackend.service.StaticImageService;
import org.talang.wabackend.service.UserService;
import org.talang.wabackend.util.SdImageLikeComponent;

import static org.talang.wabackend.util.SdImageLikeComponent.SD_IMAGE_LIKE;

/**
* @author lihan
* @description 针对表【sd_image】的数据库操作Service实现
* @createDate 2024-03-01 01:16:15
*/
@Service
public class SdImageServiceImpl extends ServiceImpl<SdImageMapper, SdImage>
    implements SdImageService{

    @Autowired
    private StaticImageService staticImageService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveSdImage(String imageId, Txt2ImgResult txt2ImgResult, Integer userId) {
        SdImage sdImage = new SdImage();
        sdImage.setId(imageId);
        sdImage.setUserId(userId);
        sdImage.setParams(JSONUtil.toJsonStr(txt2ImgResult.getParameters()));
        String modelName = txt2ImgResult.getParameters()
                .getOverrideSettings().get("sd_model_checkpoint").toString();
        String vaeModelName = txt2ImgResult.getParameters()
                .getOverrideSettings().get("sd_vae").toString();
        sdImage.setStaticImageId(imageId);
        sdImage.setCheckpointModelId(modelService.getModelIdByModelName(modelName));
        sdImage.setVaeModelId(modelService.getModelIdByModelName(vaeModelName));

        save(sdImage);
    }

    @Override
    public SdImageVo getImageById(Integer userId,String id) {
        SdImage sdImage = getById(id);
        if (sdImage == null) {
            throw new RuntimeException("不存在Sd图像");
        }
        SdImageVo vo = BeanUtil.toBean(sdImage, SdImageVo.class);

        vo.setImageUrl(staticImageService.getSaticImagePathById(id));
        vo.setUser(userService.getUserVo(sdImage.getUserId()));
        String key = SD_IMAGE_LIKE + sdImage.getId();
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId));
        vo.setIsLiked(isMember);


        return vo;
    }
}



