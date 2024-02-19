package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.service.ModelService;
import org.talang.wabackend.mapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
* @author lihan
* @description 针对表【sd_model】的数据库操作Service实现
* @createDate 2024-02-20 03:50:01
*/
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model>
    implements ModelService{

}




