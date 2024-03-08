package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.mapper.PostLikesMapper;
import org.talang.wabackend.mapper.PostMapper;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.generator.PostFavorite;
import org.talang.wabackend.model.generator.PostLikes;
import org.talang.wabackend.service.PostLikesService;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Transactional
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes> implements PostLikesService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostLikesMapper postLikesMapper;

    @Override
    public HashMap<String, Integer> PostLikes(Integer postId) {
        Integer userId = StpUtil.getLoginIdAsInt();

        LambdaQueryWrapper<PostLikes> judgeWrapper = new LambdaQueryWrapper<>();
        judgeWrapper.eq(PostLikes::getPostId, postId)
                .eq(PostLikes::getUserId, userId);

        Integer ifLikes = postLikesMapper.ifLikes(postId, userId);
        Integer totalLikes = postLikesMapper.getTotalLikes(postId);
        PostLikes postLikes = postLikesMapper.selectOne(judgeWrapper);
        HashMap<String, Integer> map = new HashMap<>();

        if (postLikes == null && ifLikes != 0) {  //有，但已删除
            totalLikes = totalLikes + 1;

            postLikesMapper.recoverLikes(postId, userId);

            LambdaUpdateWrapper<Post> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Post::getId, postId);
            updateWrapper.set(Post::getNumLiked, totalLikes);
            updateWrapper.set(Post::getUpdateTime, LocalDateTime.now());
            postMapper.update(null, updateWrapper);

            map.put("isLiked", 1);

        } else if (postLikes == null) {  //无
            totalLikes = totalLikes + 1;

            postLikes = new PostLikes();
            postLikes.setPostId(postId);
            postLikes.setUserId(userId);
            postLikesMapper.insert(postLikes);

            LambdaUpdateWrapper<Post> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Post::getId, postId);
            updateWrapper.set(Post::getNumLiked, totalLikes);
            updateWrapper.set(Post::getUpdateTime, LocalDateTime.now());
            postMapper.update(null, updateWrapper);

            map.put("isLiked", 1);


        } else { //有，且未删除
            totalLikes = totalLikes - 1;

            postLikesMapper.delete(judgeWrapper);

            LambdaUpdateWrapper<Post> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Post::getId, postId);
            updateWrapper.set(Post::getNumLiked, totalLikes);
            updateWrapper.set(Post::getUpdateTime, LocalDateTime.now());
            postMapper.update(null, updateWrapper);

            map.put("isLiked", 0);

        }

        map.put("newNumLiked", totalLikes);
        return map;
    }

}
