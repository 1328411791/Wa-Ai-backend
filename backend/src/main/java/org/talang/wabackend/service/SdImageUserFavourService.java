package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.SdImageUserFavour;


/**
 * (SdImageUserFavour)表服务接口
 *
 * @author Polister
 * @since 2024-03-13 22:45:06
 */
public interface SdImageUserFavourService extends IService<SdImageUserFavour> {

    Result setFavour(String sdImageId);

    Result getFavour(String sdImageId);
}
