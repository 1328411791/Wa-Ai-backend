package org.talang.wabackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lihan
* @description 针对表【sd_image】的数据库操作Mapper
* @createDate 2024-02-20 03:50:01
* @Entity org.talang.wabackend.model.generator.Image
*/
@Mapper
public interface ImageMapper extends BaseMapper<Image> {

}




