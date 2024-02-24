package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.generator.ImageTag;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_image_tag】的数据库操作Service
 * @createDate 2024-02-22 22:31:26
 */
public interface ImageTagService extends IService<ImageTag> {

    List<ImageTag> getTagOrderBySearch(String search, Integer page, Integer pageSize);

    List<ImageTag> getTagOrderByAsce(Integer page, Integer pageSize);
}
