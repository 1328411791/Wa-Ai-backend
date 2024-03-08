package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.mapper.PostFavoriteMapper;
import org.talang.wabackend.mapper.PostMapper;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.generator.PostFavorite;
import org.talang.wabackend.service.PostFavoriteSevice;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Transactional
public class PostFavoriteSeviceImpl implements PostFavoriteSevice {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostFavoriteMapper postFavoriteMapper;

    @Override
    public HashMap<String, Integer> PostFavorite(Integer postId) {
        Integer userId = StpUtil.getLoginIdAsInt();

        LambdaQueryWrapper<PostFavorite> judgeWrapper = new LambdaQueryWrapper<>();
        judgeWrapper.eq(PostFavorite::getPostId, postId)
                .eq(PostFavorite::getUserId, userId);

        Integer ifFavorite = postFavoriteMapper.ifFavorite(postId, userId);
        Integer totalFavours = postFavoriteMapper.getTotalFavours(postId);
        PostFavorite postFavorite = postFavoriteMapper.selectOne(judgeWrapper);
        HashMap<String, Integer> map = new HashMap<>();

        if (postFavorite == null && ifFavorite != 0) {  //有，但已删除
            totalFavours = totalFavours + 1;

            postFavoriteMapper.recoverFavorite(postId, userId);

            updatePost(postId, totalFavours);

            map.put("isFavours", 1);

        } else if (postFavorite == null) {  //无
            totalFavours = totalFavours + 1;

            postFavorite = new PostFavorite();
            postFavorite.setPostId(postId);
            postFavorite.setUserId(userId);
            postFavoriteMapper.insert(postFavorite);

            updatePost(postId, totalFavours);

            map.put("isFavours", 1);


        } else { //有，且未删除
            totalFavours = totalFavours - 1;

            postFavoriteMapper.delete(judgeWrapper);

            updatePost(postId, totalFavours);

            map.put("isFavours", 0);

        }

        map.put("newNumFavours", totalFavours);
        return map;
    }


    void updatePost(Integer postId, Integer totalFavours) {
        LambdaUpdateWrapper<Post> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Post::getId, postId);
        updateWrapper.set(Post::getNumFavours, totalFavours);
        updateWrapper.set(Post::getUpdateTime, LocalDateTime.now());
        postMapper.update(null, updateWrapper);
    }
}
