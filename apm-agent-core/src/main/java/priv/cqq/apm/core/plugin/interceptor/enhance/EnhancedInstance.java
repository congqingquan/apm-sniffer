package priv.cqq.apm.core.plugin.interceptor.enhance;

import priv.cqq.apm.core.APMConstants;

public interface EnhancedInstance {

    String CONTEXT_ATTR_NAME = APMConstants.DEFAULT_CONTEXT_ATTR_NAME;

    Object getContextAttr();

    void setContextAttr(Object value);
}