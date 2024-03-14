package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.mapper.SdImageMapper;
import org.talang.wabackend.model.generator.SdImage;
import org.talang.wabackend.model.generator.SdImageUserFavour;
import org.talang.wabackend.mapper.SdImageUserFavourMapper;
import org.talang.wabackend.model.vo.sdImage.SdImageFavourVo;
import org.talang.wabackend.service.SdImageUserFavourService;

import java.util.Objects;

/**
 * (SdImageUserFavour)表服务实现类
 *
 * @author Polister
 * @since 2024-03-13 22:45:06
 */
@Service("sdImageUserFavourService")
public class SdImageUserFavourServiceImpl extends ServiceImpl<SdImageUserFavourMapper, SdImageUserFavour>
        implements SdImageUserFavourService {

    @Resource
    private SdImageMapper sdImageMapper;

    @Override
    public Result setFavour(String sdImageId) {

        int loginId = StpUtil.getLoginIdAsInt();

        LambdaQueryWrapper<SdImageUserFavour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SdImageUserFavour::getSdImageId, sdImageId)
                .eq(SdImageUserFavour::getUserId, loginId);
        SdImageUserFavour favour = this.getOne(wrapper);
        // 构造查询现在数据
        wrapper.clear();
        wrapper.eq(SdImageUserFavour::getSdImageId, sdImageId);
        int counts = (int) this.count(wrapper);

        if (Objects.isNull(favour)) {
            SdImageUserFavour sdImageUserFavour = new SdImageUserFavour();
            sdImageUserFavour.setUserId(loginId)
                    .setSdImageId(sdImageId);
            // 存到关联表
            this.save(sdImageUserFavour);
        } else {
            this.removeById(favour.getId());
        }

        // 更新数据
        counts = Objects.isNull(favour) ? counts + 1 : counts - 1;
        SdImage sdImage = new SdImage();
        sdImage.setId(sdImageId);
        sdImage.setFavours(counts);
        sdImageMapper.updateById(sdImage);

        return Result.success(new SdImageFavourVo(Objects.isNull(favour), counts));
    }

    @Override
    public Result getFavour(String sdImageId) {
        int loginId = StpUtil.getLoginIdAsInt();

        LambdaQueryWrapper<SdImageUserFavour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SdImageUserFavour::getSdImageId, sdImageId);

        return Result.success(new SdImageFavourVo(this.getIsFavour(sdImageId, loginId),
                                (int) this.count(wrapper)));
    }

    public boolean getIsFavour(String sdImageId, Integer userId) {
        LambdaQueryWrapper<SdImageUserFavour> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SdImageUserFavour::getSdImageId, sdImageId)
                .eq(SdImageUserFavour::getUserId, userId);

        return Objects.nonNull(this.getOne(wrapper));
    }
}


