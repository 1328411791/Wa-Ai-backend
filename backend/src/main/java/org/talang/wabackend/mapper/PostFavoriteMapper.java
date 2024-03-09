package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.talang.wabackend.model.generator.PostFavorite;

@Mapper
public interface PostFavoriteMapper extends BaseMapper<PostFavorite> {

    @Select("select count(*) from sd_post_favorite where post_id=#{postId} and is_delete!=1")
    Integer getTotalFavours(Integer postId);

    @Select("select count(*) from sd_post_favorite where post_id=#{postId} and user_id=#{userId}")
    Integer ifFavorite(Integer postId, Integer userId);

    @Update("update sd_post_favorite set is_delete=0, update_time=now() where post_id=#{postId} and user_id=#{userId}")
    void recoverFavorite(Integer postId, Integer userId);
}
