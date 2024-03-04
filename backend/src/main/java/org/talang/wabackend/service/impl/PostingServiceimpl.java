package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.talang.wabackend.mapper.PostingMapper;
import org.talang.wabackend.model.dto.posting.CreatePostingDto;
import org.talang.wabackend.model.generator.Comment;
import org.talang.wabackend.model.generator.Posting;
import org.talang.wabackend.model.vo.posting.PostingVoFull;
import org.talang.wabackend.model.vo.posting.PostingVoLite;
import org.talang.wabackend.model.vo.posting.PostingVoLiteTemp;
import org.talang.wabackend.service.PostingService;

import java.util.*;

@Service
public class PostingServiceimpl implements PostingService {
    @Autowired
    private PostingMapper postingMapper;

    @Override
    @Transactional
    public void createPosting(CreatePostingDto createPostingDto) {
        Posting posting = new Posting();
        BeanUtils.copyProperties(createPostingDto, posting);
        posting.setUserId(StpUtil.getLoginIdAsInt());
        posting.setImages(String.join(",", createPostingDto.getImagesArray()));

        postingMapper.createPosting(posting);
        Integer postingId = posting.getId();

        Comment comment = new Comment();
        comment.setPostingId(postingId);

        postingMapper.createComment(comment);
        postingMapper.updateCommentOfPosting(postingId, comment.getId());
    }

    @Override
    public void deletePosting(List<Integer> posting) {
        postingMapper.deletePosting(posting);
    }

    @Override
    public List<PostingVoLite> getPostingByuser(Integer userId) {
        List<PostingVoLiteTemp> postingVoLiteTempList = postingMapper.getPostingByuser(userId);
        List<PostingVoLite> list = new ArrayList<PostingVoLite>();
        for (PostingVoLiteTemp p : postingVoLiteTempList) {
            PostingVoLite postingVoLite = new PostingVoLite();
            BeanUtils.copyProperties(p, postingVoLite);
            postingVoLite.setImages(p.getImages().split(","));
            list.add(postingVoLite);
        }
        return list;
    }

    @Override
    public PostingVoFull getPostingById(Integer postingId) {
        return postingMapper.getPostingById(postingId);
    }
}
