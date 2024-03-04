package org.talang.wabackend.mapper;

import org.apache.ibatis.annotations.*;
import org.talang.wabackend.model.generator.Comment;
import org.talang.wabackend.model.generator.Posting;
import org.talang.wabackend.model.vo.posting.PostingVoFull;
import org.talang.wabackend.model.vo.posting.PostingVoLiteTemp;

import java.util.List;

@Mapper
public interface PostingMapper {
    @Insert("insert into sd_postings (user_id,title,create_time,update_time,sd_images)" +
            "values (#{userId},#{title},now(),now(),#{images})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createPosting(Posting posting);

    void deletePosting(List<Integer> posting);

    @Select("select id, user_id userId, title, favorite, comment_number commentNumber, sd_images images from sd_postings where user_id = #{userId}")
    List<PostingVoLiteTemp> getPostingByuser(Integer userId);

    @Select("select id, user_id userId, title, favorite, comment_id commentId, comment_number commentNumber, create_time createTime, update_time updateTime, sd_images images from sd_postings where id = #{id}")
    PostingVoFull getPostingById(Integer id);

    @Update("update sd_postings set comment_id=#{commentId} where id=#{postingId}")
    void updateCommentOfPosting(Integer postingId, Integer commentId);


    //评论
    @Insert("insert into sd_postings_comment (posting_id) values (#{postingId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createComment(Comment comment);
}
