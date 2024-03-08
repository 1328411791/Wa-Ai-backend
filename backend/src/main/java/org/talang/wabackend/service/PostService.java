package org.talang.wabackend.service;

import org.talang.wabackend.model.dto.post.PostDto;
import org.talang.wabackend.model.vo.post.PostFullVo;
import org.talang.wabackend.model.vo.post.PostLiteVo;

import java.util.List;

public interface PostService {
    Integer createPost(PostDto postDto);

    void deletePost(List<Integer> postingIdList);

    List<PostLiteVo> getPostLite(Integer userId);

    PostFullVo getPostFull(Integer postingId);

}
