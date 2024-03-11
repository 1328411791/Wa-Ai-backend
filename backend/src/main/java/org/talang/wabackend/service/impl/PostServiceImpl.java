package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.mapper.PostMapper;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.post.PostAddDto;
import org.talang.wabackend.model.dto.post.PostGetByUIDDto;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.post.PostFullVo;
import org.talang.wabackend.model.vo.post.PostLiteVo;
import org.talang.wabackend.service.PostService;

import java.util.*;

@Service
@Transactional
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Integer createPost(PostAddDto postAddDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postAddDto, post);
        post.setUserId(StpUtil.getLoginIdAsInt());
        postMapper.insert(post);
        postMapper.insertImage(postAddDto.getSdImages(), post.getId());
        return post.getId();
    }

    @Override
    public void deletePost(List<Integer> postingIdList) {
        postMapper.deleteBatchIds(postingIdList);
    }

    @Override
    public Map<String, Object> getPostLiteByCreate(PostGetByUIDDto postGetByUIDDto) {
        if (postGetByUIDDto.getUserId() == null) {
            postGetByUIDDto.setUserId(StpUtil.getLoginIdAsInt());
        }
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //uid
        lambdaQueryWrapper.eq(Post::getUserId, postGetByUIDDto.getUserId());
        return this.doList(lambdaQueryWrapper, postGetByUIDDto);
    }

    @Override
    public Map<String, Object> getPostLiteByFavours(PostGetByUIDDto postGetByUIDDto) {
        Integer UID = postGetByUIDDto.getUserId();
        if (UID == null) {
            postGetByUIDDto.setUserId(StpUtil.getLoginIdAsInt());
            UID = StpUtil.getLoginIdAsInt();
        }

        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查收藏表
        lambdaQueryWrapper.inSql(Post::getId, "select post_id from sd_post_favorite where is_delete!=1 and user_id = " + UID);

        return this.doList(lambdaQueryWrapper, postGetByUIDDto);
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


    private void wrapperDate(LambdaQueryWrapper<Post> lambdaQueryWrapper, PostGetByUIDDto postGetByUIDDto) {
        Date startTime = postGetByUIDDto.getStartTimestamp();
        Date endTime = postGetByUIDDto.getEndTimestamp();
        if (startTime != null && endTime != null) {
            lambdaQueryWrapper.ge(Post::getCreateTime, startTime)
                    .le(Post::getCreateTime, endTime);
        } else if (startTime != null) {
            lambdaQueryWrapper.ge(Post::getCreateTime, startTime);
        } else if (endTime != null) {
            lambdaQueryWrapper.le(Post::getCreateTime, endTime);
        }
    }

    private void wrapperTitle(LambdaQueryWrapper<Post> lambdaQueryWrapper, PostGetByUIDDto postGetByUIDDto) {
        String title = postGetByUIDDto.getSearchQuery();
        if (title != null && !title.isEmpty()) {
            lambdaQueryWrapper.like(Post::getTitle, title);
        }
    }

    private Page<Post> pageExecution(LambdaQueryWrapper<Post> lambdaQueryWrapper, PostGetByUIDDto postGetByUIDDto) {
        Integer page = postGetByUIDDto.getPage();
        Integer pageSize = postGetByUIDDto.getPageSize();
        Page<Post> postPage = new Page<>(page, pageSize);
        return postMapper.selectPage(postPage, lambdaQueryWrapper);
    }

    private List<PostLiteVo> postListHandle(List<Post> postList, PostGetByUIDDto postGetByUIDDto) {
        return postList.stream().map(post -> {
            PostLiteVo postLiteVo = BeanUtil.toBean(post, PostLiteVo.class);
            postLiteVo.setSdimageIdList(postMapper.getPostImage(post.getId()));
            User user = userMapper.selectById(postGetByUIDDto.getUserId());
            postLiteVo.setUserNickName(user.getNickName());
            return postLiteVo;
        }).toList();
    }

    private Map<String, Object> doList(LambdaQueryWrapper<Post> lambdaQueryWrapper, PostGetByUIDDto postGetByUIDDto) {

        //日期
        this.wrapperDate(lambdaQueryWrapper, postGetByUIDDto);
        //标题
        this.wrapperTitle(lambdaQueryWrapper, postGetByUIDDto);
        //分页
        List<Post> postList = this.pageExecution(lambdaQueryWrapper, postGetByUIDDto).getRecords();

        List<PostLiteVo> postLiteVos = this.postListHandle(postList, postGetByUIDDto);

        Map<String, Object> map = new HashMap<>();
        map.put("list", postLiteVos);
        map.put("total", postMapper.selectCount(lambdaQueryWrapper));

        return map;
    }
}