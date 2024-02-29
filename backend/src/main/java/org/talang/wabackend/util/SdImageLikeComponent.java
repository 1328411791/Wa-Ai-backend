package org.talang.wabackend.util;

import cn.hutool.core.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.model.generator.SdImage;
import org.talang.wabackend.service.ModelService;
import org.talang.wabackend.service.SdImageService;

@Component
public class SdImageLikeComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SdImageService sdImageService;

    public static final String SD_IMAGE_LIKE = "sdImage:like:";

    public boolean like(String sdmodelId, Integer userId) {
        String key = SD_IMAGE_LIKE + sdmodelId;
        SdImage model = sdImageService.getById(sdmodelId);
        if (model == null) {
            throw new RuntimeException("不存在Sd图片");
        }
        boolean flag = Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId)));
        if (BooleanUtil.isFalse(flag)) {
            boolean update = sdImageService.update().setSql("liked = liked + 1")
                    .eq("id", sdmodelId).update();
            if (update){
                stringRedisTemplate.opsForSet().add(key, String.valueOf(userId));
            }
            return true;
        }else {
            sdImageService.update().setSql("liked = liked - 1")
                    .eq("id", sdmodelId).update();
            stringRedisTemplate.opsForSet().remove(key, String.valueOf(userId));
            return false;
        }
    }

    public Long getLikeCount(String sdmodelId) {
        String key = SD_IMAGE_LIKE + sdmodelId;
        Long size = stringRedisTemplate.opsForSet().size(key);
        return size;
    }

    public boolean isLiked(String sdmodelId, Integer userId) {
        String key = SD_IMAGE_LIKE + sdmodelId;
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId)));
    }
}
