package org.talang.wabackend.model.generator;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (SdImageUserFavour)表实体类
 *
 * @author Polister
 * @since 2024-03-13 22:45:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sd_image_user_favour")
public class SdImageUserFavour  {
    @TableId
    private Integer id;

    private Integer userId;

    private String sdImageId;

}
