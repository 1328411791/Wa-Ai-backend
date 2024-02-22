package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.TagMapper;
import org.talang.wabackend.model.generator.SdTag;
import org.talang.wabackend.service.TagService;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_tag】的数据库操作Service实现
 * @createDate 2024-02-20 03:50:01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, SdTag>
        implements TagService {

    @Override
    public List<SdTag> getTagOrderByAsce(Integer page, Integer pageSize) {
        Page<SdTag> sdTagPage = new Page<>(page, pageSize);

        // 按照number_refe字段升序排列
        List<SdTag> numberRefe = this.page(sdTagPage).addOrder(
                OrderItem.asc("number_refe")).getRecords();

        return numberRefe;
    }
}




