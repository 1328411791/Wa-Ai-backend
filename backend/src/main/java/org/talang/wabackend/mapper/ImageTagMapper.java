package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.ImageTag;

/**
 * @author lihan
 * @description 针对表【sd_image_tag】的数据库操作Mapper
 * @createDate 2024-02-22 22:31:26
 * @Entity org.talang.wabackend.model.generator.ImageTag
 */
@Mapper
public interface ImageTagMapper extends BaseMapper<ImageTag> {

}




