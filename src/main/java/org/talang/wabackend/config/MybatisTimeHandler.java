package org.talang.wabackend.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author li hanyu
 */
@Configuration
public class MybatisTimeHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充时间

        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        this.setFieldValByName("createTime", date, metaObject);
        this.setFieldValByName("updateTime", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        this.setFieldValByName("updateTime", date, metaObject);
    }
}
