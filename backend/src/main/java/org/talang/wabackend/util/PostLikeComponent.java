package org.talang.wabackend.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.service.PostService;

import java.util.HashMap;

@Component
@Transactional
public class PostLikeComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PostService postService;

    public static final String SD_POST_LIKE = "sdPost:like:";

    public HashMap<String, Integer> like(Integer postId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        String key = SD_POST_LIKE + postId;
        Post post = postService.getById(postId);

        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        boolean flag = Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, String.valueOf(userId)));

        HashMap<String, Integer> map = new HashMap<>();
        if (BooleanUtil.isFalse(flag)) {
            boolean update = postService.update().setSql("numLiked = numLiked + 1")
                    .eq("id", postId).update();
            if (update) {
                stringRedisTemplate.opsForSet().add(key, String.valueOf(userId));
            }
            map.put("isLiked", 1);
        } else {
            postService.update().setSql("numLiked = numLiked - 1")
                    .eq("id", postId).update();
            stringRedisTemplate.opsForSet().remove(key, String.valueOf(userId));
            map.put("isLiked", 0);
        }

        post = postService.getById(postId);
        map.put("newNumLiked", post.getNumLiked());
        return map;
    }


}
