package org.squirrelframework.foundation.fsm;

import org.squirrelframework.foundation.component.SquirrelComponent;
import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.component.SquirrelSingleton;

/**
 * Provide converter instance based on type converted object.
 * 
 * @author Henry.He
 *
 */
public interface ConverterProvider extends SquirrelComponent, SquirrelSingleton {

    /**
     * Singleton instance of ConverterProvider
     */
    public static ConverterProvider INSTANCE = SquirrelProvider.getInstance().newInstance(ConverterProvider.class);

    /**
     * Register a new converter on class type of converted object
     * @param clazz class type of converted object
     * @param converterClass class type of registered converter
     */
    void register(Class<?> clazz, Class<? extends org.squirrelframework.foundation.fsm.Converter<?>> converterClass);

    /**
     * Register a new converter on class type of converted object
     * @param clazz class type of converted object
     * @param converter registered converter
     */
    void register(Class<?> clazz, org.squirrelframework.foundation.fsm.Converter<?> converter);

    /**
     * Unregister converter which is registered to class type of converted object
     * @param clazz class type of converted object
     */
    void unregister(Class<?> clazz);

    /**
     * Clear registry
     */
    void clearRegistry();

    /**
     * Get converter which is registered to class type of converted object
     * @param clazz class type of converted object
     * @return registered converted
     */
    <T> org.squirrelframework.foundation.fsm.Converter<T> getConverter(Class<T> clazz);
}
