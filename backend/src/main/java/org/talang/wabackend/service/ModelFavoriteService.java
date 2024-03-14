package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.model.dto.model.ModelFavoriteDto;
import org.talang.wabackend.model.generator.ModelFavorite;

/**
 * (ModelFavoriteVo)表服务接口
 *
 * @author ECAMT
 * @since 2024-03-13 15:38:43
 */
public interface ModelFavoriteService extends IService<ModelFavorite> {
    boolean addOrCancelFavoriteModel(Integer modelId);

    ListResult getMyFavoriteModel(ModelFavoriteDto modelFavoriteDto);

}

