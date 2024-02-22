package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.generator.SdTag;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_tag】的数据库操作Service
 * @createDate 2024-02-20 03:50:01
 */
public interface TagService extends IService<SdTag> {

    List<SdTag> getTagOrderByAsce(Integer page, Integer pageSize);
}
