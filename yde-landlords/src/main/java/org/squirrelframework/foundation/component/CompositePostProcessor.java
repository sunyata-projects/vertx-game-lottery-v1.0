package org.squirrelframework.foundation.component;

/**
 * Composite post processor together and being processed orderly.
 * 
 * @author Henry.He
 *
 * @param <T> bean type
 */
public interface CompositePostProcessor<T> extends org.squirrelframework.foundation.component.SquirrelPostProcessor<T> {

    /**
     * Compose new processor to composite processor list. Not allowed to composite duplicate processor.
     * @param processor new processor
     */
    void compose(org.squirrelframework.foundation.component.SquirrelPostProcessor<? super T> processor);
    
    /**
     * Decompose old processor from composite processor list.
     * @param processor old processor
     */
    void decompose(org.squirrelframework.foundation.component.SquirrelPostProcessor<? super T> processor);
    
    /**
     * Clear all the processors in the composite processor list.
     */
    void clear();
}
