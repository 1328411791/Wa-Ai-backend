package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.TagMapper;
import org.talang.wabackend.model.generator.Tag;
import org.talang.wabackend.service.TagService;

/**
 * @author lihan
 * @description 针对表【sd_tag】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

}




