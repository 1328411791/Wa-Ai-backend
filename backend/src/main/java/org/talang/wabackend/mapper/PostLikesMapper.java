package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.talang.wabackend.model.generator.PostLikes;

@Mapper
public interface PostLikesMapper extends BaseMapper<PostLikes> {
    @Select("select count(*) from sd_post_likes where post_id=#{postId} and is_delete!=1")
    Integer getTotalLikes(Integer postId);

    @Select("select count(*) from sd_post_likes where post_id=#{postId} and user_id=#{userId}")
    Integer ifLikes(Integer postId, Integer userId);

    @Update("update sd_post_likes set is_delete=0, update_time=now() where post_id=#{postId} and user_id=#{userId}")
    void recoverLikes(Integer postId, Integer userId);
}
