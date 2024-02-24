package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.ImageTagMapper;
import org.talang.wabackend.model.generator.ImageTag;
import org.talang.wabackend.service.ImageTagService;


import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_image_tag】的数据库操作Service实现
 * @createDate 2024-02-22 22:31:26
 */
@Service
public class ImageTagServiceImpl extends ServiceImpl<ImageTagMapper, ImageTag>
        implements ImageTagService {

    @Override
    public List<ImageTag> getTagOrderBySearch(String search, Integer page, Integer pageSize) {
        Page<ImageTag> sdTagPage = new Page<>(page, pageSize);

        QueryWrapper<ImageTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name_cn", search)
                    .or()
                    .like("name_en", search);

        List<ImageTag> result = this.page(sdTagPage, queryWrapper).addOrder(
                OrderItem.asc("number_refe")).getRecords();

        return result;
    }

    @Override
    public List<ImageTag> getTagOrderByAsce(Integer page, Integer pageSize) {
        Page<ImageTag> sdTagPage = new Page<>(page, pageSize);

        // 按照number_refe字段升序排列
        List<ImageTag> numberRefe = this.page(sdTagPage).addOrder(
                OrderItem.asc("number_refe")).getRecords();

        return numberRefe;
    }

}




