package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ModelLikesMapper;
import org.talang.wabackend.model.generator.ModelLikes;
import org.talang.wabackend.service.ModelLikesService;

/**
 * @author lihan
 * @description 针对表【sd_model_likes】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class ModelLikesServiceImpl extends ServiceImpl<ModelLikesMapper, ModelLikes>
        implements ModelLikesService {

    @Resource
    private ModelLikesMapper modelLikesMapper;

    @Override
    public Integer countByModelId(Integer id) {
        return modelLikesMapper.countByModelId(id);
    }
}




