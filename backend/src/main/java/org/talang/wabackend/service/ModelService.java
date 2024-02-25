package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.model.vo.model.HomePageModelVo;
import org.talang.wabackend.model.vo.model.SelectSdModelVo;

import java.util.List;

/**
 * @author lihan
 * @description 针对表【sd_model】的数据库操作Service
 * @createDate 2024-02-20 03:50:01
 */
public interface ModelService extends IService<Model> {

    List<HomePageModelVo> getByCategory(String category, Integer page, Integer pageSize);


    SelectSdModelVo selcetModelOrder(String searchQuery, Long startTimestamp, Long endTimestamp, Integer page, Integer pageSize);
}
