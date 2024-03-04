package org.talang.wabackend.service;

import org.talang.wabackend.model.dto.posting.CreatePostingDto;
import org.talang.wabackend.model.vo.posting.PostingVoFull;
import org.talang.wabackend.model.vo.posting.PostingVoLite;

import java.util.List;

/**
 *
 */

public interface PostingService {
    void createPosting(CreatePostingDto createPostingDto);

    void deletePosting(List<Integer> posting);

    List<PostingVoLite> getPostingByuser(Integer userId);

    PostingVoFull getPostingById(Integer postingId);

    void addFavoritePosting(Integer postingId);
}
