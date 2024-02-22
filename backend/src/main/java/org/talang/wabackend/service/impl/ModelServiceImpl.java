package org.talang.wabackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ModelMapper;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.model.vo.model.HomePageModelVo;
import org.talang.wabackend.service.ImageFromSdmodelsService;
import org.talang.wabackend.service.ModelLikesService;
import org.talang.wabackend.service.ModelService;

import java.util.List;

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
}




