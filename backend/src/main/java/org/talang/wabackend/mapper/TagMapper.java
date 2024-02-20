package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Tag;

/**
 * @author lihan
 * @description 针对表【sd_tag】的数据库操作Mapper
 * @createDate 2024-02-20 03:50:01
 * @Entity org.talang.wabackend.model.generator.Tag
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




