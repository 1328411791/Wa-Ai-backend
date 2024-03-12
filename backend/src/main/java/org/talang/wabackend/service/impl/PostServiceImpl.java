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
import org.talang.wabackend.common.ListResult;
import org.talang.wabackend.mapper.PostMapper;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.post.PostAddDto;
import org.talang.wabackend.model.dto.post.PostGetByMIDDto;
import org.talang.wabackend.model.dto.post.PostGetByUIDDto;
import org.talang.wabackend.model.generator.Post;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.post.PostFullVo;
import org.talang.wabackend.model.vo.post.PostLiteVo;
import org.talang.wabackend.service.PostService;

import java.util.Date;
import java.util.List;


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
        post.setNumLiked(0);
        post.setNumFavours(0);
        post.setNumComment(0);
        postMapper.insert(post);
        postMapper.insertImage(postAddDto.getSdImages(), post.getId());

        //是否关联模型
        Integer modelId = postAddDto.getInModel();
        if (modelId != null && modelId > 0) {
            postMapper.insertPostModel(modelId, post.getId());
        }
        return post.getId();
    }

    @Override
    public void deletePost(List<Integer> postingIdList) {
        postMapper.deleteBatchIds(postingIdList);
    }

    @Override
    public ListResult getPostLiteByCreate(PostGetByUIDDto postGetByUIDDto) {
        if (postGetByUIDDto.getUserId() == null) {
            postGetByUIDDto.setUserId(StpUtil.getLoginIdAsInt());
        }
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //uid
        lambdaQueryWrapper.eq(Post::getUserId, postGetByUIDDto.getUserId());
        //日期
        Date startTime = postGetByUIDDto.getStartTimestamp();
        Date endTime = postGetByUIDDto.getEndTimestamp();
        //标题
        String title = postGetByUIDDto.getSearchQuery();
        //分页大小
        Integer page = postGetByUIDDto.getPage();
        Integer pageSize = postGetByUIDDto.getPageSize();

        return this.doList(lambdaQueryWrapper, startTime, endTime, title, page, pageSize);
    }

    @Override
    public ListResult getPostLiteByFavours(PostGetByUIDDto postGetByUIDDto) {
        Integer UID = postGetByUIDDto.getUserId();
        if (UID == null) {
            postGetByUIDDto.setUserId(StpUtil.getLoginIdAsInt());
            UID = StpUtil.getLoginIdAsInt();
        }

        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查收藏表
        lambdaQueryWrapper.in(Post::getId, postMapper.getPostIdFromPostFavous(UID));
        //日期
        Date startTime = postGetByUIDDto.getStartTimestamp();
        Date endTime = postGetByUIDDto.getEndTimestamp();
        //标题
        String title = postGetByUIDDto.getSearchQuery();
        //分页大小
        Integer page = postGetByUIDDto.getPage();
        Integer pageSize = postGetByUIDDto.getPageSize();

        return this.doList(lambdaQueryWrapper, startTime, endTime, title, page, pageSize);
    }

    @Override
    public ListResult getPostLiteByModel(PostGetByMIDDto postGetByMIDDto) {
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer MID = postGetByMIDDto.getModelId();
        //在 PostModel 找帖子的id集合
        lambdaQueryWrapper.in(Post::getId, postMapper.getPostIdFromPostModel(MID));
        //日期
        Date startTime = postGetByMIDDto.getStartTimestamp();
        Date endTime = postGetByMIDDto.getEndTimestamp();
        //标题
        String title = postGetByMIDDto.getSearchQuery();
        //分页大小
        Integer page = postGetByMIDDto.getPage();
        Integer pageSize = postGetByMIDDto.getPageSize();

        return this.doList(lambdaQueryWrapper, startTime, endTime, title, page, pageSize);
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


    private void wrapperDate(LambdaQueryWrapper<Post> lambdaQueryWrapper, Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            lambdaQueryWrapper.ge(Post::getCreateTime, startTime)
                    .le(Post::getCreateTime, endTime);
        } else if (startTime != null) {
            lambdaQueryWrapper.ge(Post::getCreateTime, startTime);
        } else if (endTime != null) {
            lambdaQueryWrapper.le(Post::getCreateTime, endTime);
        }
    }

    private void wrapperTitle(LambdaQueryWrapper<Post> lambdaQueryWrapper, String title) {
        if (title != null && !title.isEmpty()) {
            lambdaQueryWrapper.like(Post::getTitle, title);
        }
    }

    private Page<Post> pageExecution(LambdaQueryWrapper<Post> lambdaQueryWrapper, Integer page, Integer pageSize) {
        Page<Post> postPage = new Page<>(page, pageSize);
        return postMapper.selectPage(postPage, lambdaQueryWrapper);
    }

    private List<PostLiteVo> postListHandle(List<Post> postList) {
        return postList.stream().map(post -> {
            PostLiteVo postLiteVo = BeanUtil.toBean(post, PostLiteVo.class);
            postLiteVo.setSdimageIdList(postMapper.getPostImage(post.getId()));
            User user = userMapper.selectById(postLiteVo.getUserId());
            postLiteVo.setUserNickName(user.getNickName());
            return postLiteVo;
        }).toList();
    }

    private ListResult doList(LambdaQueryWrapper<Post> lambdaQueryWrapper, Date startTime,
                              Date endTime, String title, Integer page, Integer pageSize) {

        //日期
        this.wrapperDate(lambdaQueryWrapper, startTime, endTime);
        //标题
        this.wrapperTitle(lambdaQueryWrapper, title);
        //执行分页查询
        List<Post> postList = this.pageExecution(lambdaQueryWrapper, page, pageSize).getRecords();
        //帖子作者的昵称，图片id的封装
        List<PostLiteVo> postLiteVos = this.postListHandle(postList);

        ListResult listResult = new ListResult();
        listResult.setList(postLiteVos);
        listResult.setSelectTotal(postMapper.selectCount(lambdaQueryWrapper));
        return listResult;
    }
}