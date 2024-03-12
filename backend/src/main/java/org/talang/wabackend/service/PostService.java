package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.model.dto.post.PostAddDto;
import org.talang.wabackend.model.dto.post.PostGetByMIDDto;
import org.talang.wabackend.model.dto.post.PostGetByUIDDto;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.vo.post.PostFullVo;

import java.util.List;

public interface PostService extends IService<Post> {
    Integer createPost(PostAddDto postAddDto);

    void deletePost(List<Integer> postingIdList);

    ListResult getPostLiteByCreate(PostGetByUIDDto postGetByUIDDto);

    ListResult getPostLiteByFavours(PostGetByUIDDto postGetByUIDDto);

    ListResult getPostLiteByModel(PostGetByMIDDto postGetByMIDDto);

    PostFullVo getPostFull(Integer postingId);

}
