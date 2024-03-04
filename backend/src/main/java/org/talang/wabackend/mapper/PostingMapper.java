package org.talang.wabackend.mapper;

import org.apache.ibatis.annotations.*;
import org.talang.wabackend.model.generator.Comment;
import org.talang.wabackend.model.generator.Posting;
import org.talang.wabackend.model.vo.posting.PostingVoFull;
import org.talang.wabackend.model.vo.posting.PostingVoLiteTemp;

import java.util.List;

@Mapper
public interface PostingMapper {
    @Insert("insert into sd_postings (user_id,title,create_time,update_time,sd_images,body)" +
            "values (#{userId},#{title},now(),now(),#{images},#{body})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createPosting(Posting posting);

    //删除帖子
    void deletePosting(List<Integer> posting);

    //查找要删除的帖子的id的对应评论站
    List<Integer> getDeletePostingCommentId(List<Integer> postingId);

    //同步删除评论站
    void deletePostingComment(List<Integer> commentId);

    //通过用户id查询
    @Select("select id, user_id userId, title, favorite, comment_number commentNumber, sd_images images from sd_postings where user_id = #{userId}")
    List<PostingVoLiteTemp> getPostingByuser(Integer userId);

    //通过帖子id查询
    @Select("select id, user_id userId, title, favorite, comment_id commentId, comment_number commentNumber, create_time createTime," +
            " update_time updateTime, sd_images images, body from sd_postings where id = #{id}")
    PostingVoFull getPostingById(Integer id);

    //创建帖子和评论时的数据同步
    @Update("update sd_postings set comment_id=#{commentId} where id=#{postingId}")
    void updateCommentOfPosting(Integer postingId, Integer commentId);


    //创建评论
    @Insert("insert into sd_postings_comment (posting_id) values (#{postingId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createComment(Comment comment);
}
