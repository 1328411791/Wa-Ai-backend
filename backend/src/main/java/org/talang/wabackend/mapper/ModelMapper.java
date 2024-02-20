package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Model;

/**
 * @author lihan
 * @description 针对表【sd_model】的数据库操作Mapper
 * @createDate 2024-02-20 03:50:01
 * @Entity org.talang.wabackend.model.generator.Model
 */
@Mapper
public interface ModelMapper extends BaseMapper<Model> {

}




