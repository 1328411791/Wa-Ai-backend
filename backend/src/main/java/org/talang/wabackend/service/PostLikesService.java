package org.talang.wabackend.service;

import java.util.HashMap;

public interface PostLikesService {
    HashMap<String, Integer> PostLikes(Integer postId);
}
