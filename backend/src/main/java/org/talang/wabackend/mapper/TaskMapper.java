package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Task;

/**
 * @author lihan
 * @description 针对表【sd_task】的数据库操作Mapper
 * @createDate 2024-02-23 02:09:45
 * @Entity org.talang.wabackend.model.generator.Task
 */

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




