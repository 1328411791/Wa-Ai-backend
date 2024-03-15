package org.talang.wabackend.util;

import cn.hutool.core.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.talang.wabackend.exception.BusinessException;
import org.talang.wabackend.exception.ErrorCode;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.service.ModelService;

@Component
public class SdModelLikeComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ModelService modelService;

    public static final String SD_MODEL_LIKE = "sdModel:like:";

    public boolean like(Integer sdmodelId, Integer userId) {
        String key = SD_MODEL_LIKE + sdmodelId;
        Model model = modelService.getById(sdmodelId);
        if (model == null) {
            throw new BusinessException(ErrorCode.MODEL_NOT_FOUND);
        }
        boolean flag = Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId)));
        if (BooleanUtil.isFalse(flag)) {
            boolean update = modelService.update().setSql("liked = liked + 1")
                    .eq("id", sdmodelId).update();
            if (update) {
                stringRedisTemplate.opsForSet().add(key, String.valueOf(userId));
            }
            return true;
        } else {
            modelService.update().setSql("liked = liked - 1")
                    .eq("id", sdmodelId).update();
            stringRedisTemplate.opsForSet().remove(key, String.valueOf(userId));
            return false;
        }
    }

    public Long getLikeCount(String sdmodelId) {
        String key = SD_MODEL_LIKE + sdmodelId;
        Long size = stringRedisTemplate.opsForSet().size(key);
        return size;
    }

    public boolean isLiked(Integer sdmodelId, Integer userId) {
        String key = SD_MODEL_LIKE + sdmodelId;
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId)));
    }
}
