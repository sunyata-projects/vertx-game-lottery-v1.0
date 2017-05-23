package org.sunyata.octopus;

/**
 * Created by leo on 17/4/18.
 */
public class MethodHandlerLocatorFactory {
    static Class<? extends MethodHandlerLocator> methodHandlerLocatorClass;
    public static <T extends MethodHandlerLocator> void setMethodHandlerLocator(Class<T> methodHandlerLocator) {
        methodHandlerLocatorClass = methodHandlerLocator;
    }

    public static MethodHandlerLocator getLocator() throws Exception {
        if (methodHandlerLocatorClass == null) {
            throw new Exception("必须设置methodHandlerLocator");
        } else {
            return methodHandlerLocatorClass.newInstance();
        }
    }
}
