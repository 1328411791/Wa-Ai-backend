package org.talang.wabackend.util;

import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QiniuStorageUtiliy {

    @Value("${sdwebui.image.qiniu.access-key}")
    private String accessKey;

    @Value("${sdwebui.image.qiniu.secret-key}")
    private String secretKey;

    @Value("${sdwebui.image.qiniu.bucket}")
    private String bucket;

    @Value("${sdwebui.image.qiniu.domain}")
    private String domain;

    public DefaultPutRet uploadImage(String localFilePath, String key) {
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
            // 访问路径
            log.info("{}/{}", domain, putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                log.error("", ex2);
            }
            throw new RuntimeException(ex);
        }

        return putRet;
    }

    public DefaultPutRet uploadImage(byte[] data, String key) {
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(data, key, upToken);
            //解析上传成功的结果
            putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
            // 访问路径
            log.info("{}/{}", domain, putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                log.error("", ex2);
            }
            throw new RuntimeException(ex);
        }

        return putRet;
    }
}
