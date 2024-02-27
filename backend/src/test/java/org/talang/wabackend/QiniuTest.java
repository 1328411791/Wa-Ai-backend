package org.talang.wabackend;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.util.QiniuStorageUtiliy;

@Slf4j
@SpringBootTest
public class QiniuTest {

    private final String localFilePath = "D:\\微信图片_20220317213430.jpg";
    private final String key = "test.jpg";
    @Autowired
    private QiniuStorageUtiliy qiniuStorageUtiliy;

    @Test
    public void UploadTest() throws QiniuException {
        DefaultPutRet s = qiniuStorageUtiliy.uploadImage(localFilePath, key);
        log.info(s.toString());
    }
}
