package com.alibaba.dubbo.spring.boot.condition;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.ProtocolConfig;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class DubboBeanDefinitionParser implements BeanDefinitionParser {
    private static final Logger logger = LoggerFactory.getLogger(DubboBeanDefinitionParser.class);

    private final Class<?> beanClass;

    private final boolean required;

    public DubboBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }

    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);


        //TODO 对ｉｄ的操作

        if(ProtocolConfig.class.equals(beanClass)){
            for (String name : parserContext.getRegistry().getBeanDefinitionNames()) {
                BeanDefinition definition=parserContext.getRegistry().getBeanDefinition(name);
                PropertyValue propertyValue=definition.getPropertyValues().getPropertyValue("protocol");
                if(propertyValue!=null){
                    Object value=propertyValue.getValue();
                    if (value instanceof ProtocolConfig ) {
                    }
                }
            }
        }




        return null;
    }


}
