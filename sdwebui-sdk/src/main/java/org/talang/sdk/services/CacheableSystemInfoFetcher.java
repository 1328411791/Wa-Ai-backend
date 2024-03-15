package org.talang.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.SdWebuiBeanFactory;
import org.talang.sdk.SystemInfoFetcher;
import org.talang.sdk.exceptions.SdBusinessException;
import org.talang.sdk.models.SystemInfo;

import java.io.IOException;

public class CacheableSystemInfoFetcher implements SystemInfoFetcher, SdWebuiBeanFactory<SystemInfo> {

    private static final String PATH = "/internal/sysinfo";
    private final String endpoint;
    private final SdWebuiBeanContainer serviceContainer;
    private volatile SystemInfo cachedSystemInfo;

    public CacheableSystemInfoFetcher(String endpoint, SdWebuiBeanContainer serviceContainer) {
        this.endpoint = endpoint;
        this.serviceContainer = serviceContainer;
    }

    @Override
    public SystemInfo systemInfo() {
        fetchSystemInfoIfAbsent();
        return cachedSystemInfo;
    }

    @Override
    public SystemInfo getBean(SdWebuiBeanContainer beanContainer) {
        return systemInfo();
    }

    void fetchSystemInfoIfAbsent() {
        if (cachedSystemInfo == null) {
            synchronized (this) {
                if (cachedSystemInfo == null) {
                    cachedSystemInfo = fetchSystemInfo();
                }
            }
        }
    }

    SystemInfo fetchSystemInfo() {
        try {
            return this.serviceContainer.getBean(HttpClient.class)
                    .execute(new HttpGet(endpoint + PATH), this::parseSystemInfo);
        } catch (IOException e) {
            throw new SdBusinessException(e.toString());
        }
    }

    SystemInfo parseSystemInfo(ClassicHttpResponse response) {
        try {
            return this.serviceContainer.getBean(ObjectMapper.class).readValue(response.getEntity().getContent(), SystemInfo.class);
        } catch (IOException e) {
            throw new SdBusinessException(e.toString());
        }
    }

}
