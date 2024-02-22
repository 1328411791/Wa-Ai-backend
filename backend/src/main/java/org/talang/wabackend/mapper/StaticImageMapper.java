package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.StaticImage;

/**
 * @author lihan
 * @description 针对表【sd_static_image(静态文件储存)】的数据库操作Mapper
 * @createDate 2024-02-22 19:00:22
 * @Entity org.talang.wabackend.model.generator.StaticImage
 */
@Mapper
public interface StaticImageMapper extends BaseMapper<StaticImage> {

}




