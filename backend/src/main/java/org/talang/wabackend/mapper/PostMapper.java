package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.talang.wabackend.model.generator.Post;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    void insertImage(List<String> sdimageList, Integer postId);

    @Select("select sdimage_id from sd_post_images where post_id = #{postId}")
    List<String> getPostImage(Integer postId);

    @Insert("insert into sd_post_model (model_id, post_id) values (#{modelId}, #{postId})")
    void insertPostModel(Integer modelId, Integer postId);

    @Select("select post_id from sd_post_favorite where is_delete!=1 and user_id = #{userId}")
    List<Integer> getPostIdFromPostFavous(Integer userId);

    @Select("select post_id from sd_post_model where model_id = #{MID}")
    List<Integer> getPostIdFromPostModel(Integer MID);
}
