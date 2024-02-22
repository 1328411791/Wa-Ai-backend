package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.talang.wabackend.model.generator.ImageFromSdmodels;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_image_from_sdmodels】的数据库操作Mapper
 * @createDate 2024-02-20 03:50:01
 * @Entity org.talang.wabackend.model.generator.ImageFromSdmodels
 */
@Mapper
public interface ImageFromSdmodelsMapper extends BaseMapper<ImageFromSdmodels> {

    @Select("select sd_static_image.file_path from sd_image_from_sdmodels,sd_static_image where sd_image_from_sdmodels.id = #{id} " +
            "and sd_image_from_sdmodels.sdimage_id = sd_static_image.id")
    List<String> selectImageFromSdmodels(Integer id);

}




