package org.talang.wabackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.SdImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lihan
* @description 针对表【sd_image】的数据库操作Mapper
* @createDate 2024-03-01 01:16:15
* @Entity org.talang.wabackend.model.generator.SdImage
*/
@Mapper
public interface SdImageMapper extends BaseMapper<SdImage> {

}




