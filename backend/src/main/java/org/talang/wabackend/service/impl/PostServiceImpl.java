package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.mapper.PostMapper;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.post.PostDto;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.post.PostFullVo;
import org.talang.wabackend.model.vo.post.PostLiteVo;
import org.talang.wabackend.service.PostService;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Integer createPost(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setUserId(StpUtil.getLoginIdAsInt());
        postMapper.insert(post);
        postMapper.insertImage(postDto.getSdImages(), post.getId());
        return post.getId();
    }

    @Override
    public void deletePost(List<Integer> postingIdList) {
        postMapper.deleteBatchIds(postingIdList);
    }

    @Override
    public List<PostLiteVo> getPostLite(Integer userId) {
        List<PostLiteVo> list = postMapper.getPostLite(userId);
        for (PostLiteVo PL : list) {
            PL.setSdimageIdList(postMapper.getPostImage(PL.getId()));
            User user = userMapper.selectById(userId);
            PL.setUserNickName(user.getNickName());
        }
        return list;
    }

    @Override
    public PostFullVo getPostFull(Integer postingId) {
        Post post = postMapper.selectById(postingId);
        PostFullVo postFullVo = new PostFullVo();
        BeanUtils.copyProperties(post, postFullVo);
        postFullVo.setSdimageIdList(postMapper.getPostImage(postingId));
        User user = userMapper.selectById(post.getUserId());
        postFullVo.setUserNickName(user.getNickName());
        return postFullVo;
    }

}
