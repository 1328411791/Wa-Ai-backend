package org.talang.wabackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ModelMapper;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.model.vo.model.HomePageModelVo;
import org.talang.wabackend.model.vo.model.SdModelVo;
import org.talang.wabackend.model.vo.model.SelectSdModelVo;
import org.talang.wabackend.service.*;
import org.talang.wabackend.util.SdModelLikeComponent;

import java.util.List;

import static org.talang.wabackend.util.SdModelLikeComponent.SD_MODEL_LIKE;

/**
 * @author lihan
 * @description 针对表【sd_model】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model>
        implements ModelService {

    @Resource
    private ImageFromSdmodelsService imageFromSdmodelsService;

    @Resource
    private ModelLikesService modelLikesService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @Override
    public List<HomePageModelVo> getByCategory(String category, Integer page, Integer pageSize) {
        Page<Model> modelPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Model> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Model::getType, category);

        List<Model> models = this.page(modelPage, queryWrapper).getRecords();

        List<HomePageModelVo> homePageModelVos = models.stream().map(model -> {
            HomePageModelVo modelVo = BeanUtil.toBean(model, HomePageModelVo.class);
            modelVo.setImagesUrl(imageFromSdmodelsService.selectImage(model.getId()));
            modelVo.setLikeCount(modelLikesService.countByModelId(model.getId()));
            return modelVo;
        }).toList();

        return homePageModelVos;
    }

    @Override
    public SelectSdModelVo selectModelOrder(Integer userId, String searchQuery, String type
            , Long startTimestamp, Long endTimestamp, Integer page, Integer pageSize) {
        Page<Model> modelPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Model> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Model::getType, type);

        // 时间戳搜索查询功能
        if (startTimestamp != 0 && endTimestamp != 0) {
            queryWrapper.between(Model::getCreateTime, startTimestamp, endTimestamp);
        } else if (startTimestamp != 0) {
            queryWrapper.ge(Model::getCreateTime, startTimestamp);
        } else if (endTimestamp != 0) {
            queryWrapper.le(Model::getCreateTime, endTimestamp);
        }

        queryWrapper.like(Model::getTitle, searchQuery);

        modelPage.setOrders(List.of(OrderItem.asc("create_time")));
        List<Model> models = this.page(modelPage, queryWrapper).getRecords();
        Long selectCount = this.count(queryWrapper);

        List<SdModelVo> sdModelVos = models.stream().map(model -> {
            SdModelVo sdModelVo = BeanUtil.toBean(model, SdModelVo.class);
            sdModelVo.setNickName(userService.getUserNickNameById(model.getAuthorId()));
            sdModelVo.setIsLiked(isMember(userId, model.getId()));
            return sdModelVo;
        }).toList();

        SelectSdModelVo selectSdModelVo = new SelectSdModelVo();
        selectSdModelVo.setModels(sdModelVos);
        selectSdModelVo.setSelectCount(selectCount);

        return selectSdModelVo;
    }

    private boolean isMember(Integer userId, Integer modelId) {
        String key = SD_MODEL_LIKE + modelId;
        return stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId));
    }

    @Override
    public SdModelVo getSdModelVo(Integer userId, Integer id) {
        Model model = this.getById(id);
        if (model == null) {
            throw new RuntimeException("不存在Sd模型");
        }
        SdModelVo sdModelVo = BeanUtil.toBean(model, SdModelVo.class);
        sdModelVo.setUserId(model.getAuthorId());
        sdModelVo.setNickName(userService.getUserNickNameById(model.getAuthorId()));
        sdModelVo.setIsLiked(isMember(userId, model.getId()));
        return sdModelVo;
    }

    @Override
    public Integer getModelIdByModelName(String modelName) {
        LambdaQueryWrapper<Model> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Model::getTitle, modelName);
        Model model = this.getOne(queryWrapper);
        return model.getId();
    }
}




