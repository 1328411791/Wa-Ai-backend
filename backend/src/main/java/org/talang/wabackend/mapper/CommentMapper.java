package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Comment;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author Polister
 * @since 2024-03-07 19:08:42
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
