package org.talang.wabackend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

@Slf4j
@SpringBootTest
class WaBackendApplicationTests {

    @Autowired
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @Test
    public void MultiSdConnectTest() {
        log.info("sdwebui {}", multiSdWebUiConnect.getAvailableSdWebui().getSdModels());
    }

}
