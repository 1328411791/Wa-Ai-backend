package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.dto.post.PostAddDto;
import org.talang.wabackend.model.dto.post.PostGetByUIDDto;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.vo.post.PostFullVo;

import java.util.List;
import java.util.Map;

public interface PostService extends IService<Post> {
    Integer createPost(PostAddDto postAddDto);

    void deletePost(List<Integer> postingIdList);

    Map<String, Object> getPostLiteByCreate(PostGetByUIDDto postGetByUIDDto);

    Map<String, Object> getPostLiteByFavours(PostGetByUIDDto postGetByUIDDto);

    PostFullVo getPostFull(Integer postingId);

}
