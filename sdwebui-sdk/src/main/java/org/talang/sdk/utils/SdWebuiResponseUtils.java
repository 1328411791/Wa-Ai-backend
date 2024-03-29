package org.talang.sdk.utils;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.talang.sdk.exceptions.SdBusinessException;
import org.talang.sdk.exceptions.SdWebuiBadRequestException;
import org.talang.sdk.exceptions.SdWebuiServerValidationException;

public class SdWebuiResponseUtils {

    public static void checkResponseStatus(ClassicHttpResponse response) {
        if (response.getCode() == HttpStatus.SC_OK) {
            return;
        }

        try {
            if (response.getCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                JsonUtils.fromJson(response.getEntity().getContent(), SdWebuiServerValidationException.class);
            }

            throw JsonUtils.fromJson(response.getEntity().getContent(), SdWebuiBadRequestException.class);
        } catch (Exception e) {
            throw new SdBusinessException(e);
        }

    }

    public static <T> T parseResponse(ClassicHttpResponse response, Class<T> clazz) {
        checkResponseStatus(response);
        try {
            return JsonUtils.fromJson(response.getEntity().getContent(), clazz);
        } catch (Exception e) {
            throw new SdBusinessException(e);
        }
    }

}
