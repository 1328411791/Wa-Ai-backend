package org.talang.wabackend.model.vo.sdImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MySdImageVo {
      private String id;
      private Long userId;
      private String userNickName;
      private Integer status; // 服务器生成的状态，0为成功，1为正在生成，2为生成失败，用户上传忽略该字段
      private String type;
      private String staticImageId;
      private Integer liked;
      private Integer favours;
      private Boolean isLiked;
      private Boolean isFavours;
}
