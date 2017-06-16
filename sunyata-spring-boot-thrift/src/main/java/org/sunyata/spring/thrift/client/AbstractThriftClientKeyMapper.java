package org.sunyata.spring.thrift.client;

import org.sunyata.spring.thrift.client.pool.ThriftClientKey;
import java.util.Map;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2016-06-14
 */
public abstract class AbstractThriftClientKeyMapper {
    abstract public Map<String, ThriftClientKey> getMappings();
}
