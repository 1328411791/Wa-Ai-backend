package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.generator.PostLikes;

import java.util.HashMap;

public interface PostLikesService extends IService<PostLikes> {
    HashMap<String, Integer> PostLikes(Integer postId);
}
