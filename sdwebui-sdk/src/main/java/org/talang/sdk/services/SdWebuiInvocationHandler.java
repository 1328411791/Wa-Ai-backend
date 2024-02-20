package org.talang.sdk.services;

import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.models.SdWebuiOptions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class SdWebuiInvocationHandler implements InvocationHandler {

  private final SdWebuiBeanContainer serviceContainer;

  public SdWebuiInvocationHandler(SdWebuiOptions options) {
    this.serviceContainer = SdWebuiBeanContainer.create(options);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Class<?> clazz = method.getDeclaringClass();
    Object service = serviceContainer.getBean(clazz);
    return method.invoke(service, args);
  }

}
