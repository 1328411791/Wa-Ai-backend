package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.vo.post.PostLiteVo;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Select("select id, user_id userId, title, numFavours, numLiked, numComment from sd_post where user_id = #{userId} and is_delete!=1")
    List<PostLiteVo> getPostLite(Integer userId);

    void insertImage(List<String> sdimageList, Integer postId);

    @Select("select sdimage_id from sd_post_images where post_id = #{postId}")
    List<String> getPostImage(Integer postId);

}
