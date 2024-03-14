package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.talang.wabackend.model.generator.ModelFavorite;

/**
 * (ModelFavoriteVo)表数据库访问层
 *
 * @author ECAMT
 * @since 2024-03-13 15:38:16
 */
@Mapper
public interface ModelFavoriteMapper extends BaseMapper<ModelFavorite> {

    @Select("select count(*) from sd_model_favorite where model_id=#{modelId} and user_id=#{userId}")
    Integer ifFavorite(Integer modelId, Integer userId);

    @Update("update sd_model_favorite set is_delete=0, update_time=now() where model_id=#{modelId} and user_id=#{userId}")
    void recoverFavorite(Integer modelId, Integer userId);
}

