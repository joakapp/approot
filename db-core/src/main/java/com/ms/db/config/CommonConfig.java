package com.ms.db.config;

import com.robert.vesta.service.factory.IdServiceFactoryBean;
import com.robert.vesta.service.intf.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库应用常量配置库
 *
 * @Author lushaonan
 */
@Configuration
@Slf4j
public class CommonConfig {

    //设置生成方法
    @Value("${idGen.methId:0}")
    private long methodId;

    //设置主机名称
    @Value("${idGen.macId:0}")
    private long macId;

    /**
     * 设置ID生成器Bean
     * @return
     */
    @Bean
    public IdService getIdService(){
        IdServiceFactoryBean factoryBean = new IdServiceFactoryBean();
        factoryBean.setProviderType(IdServiceFactoryBean.Type.PROPERTY);
        factoryBean.setGenMethod(methodId);
        factoryBean.setMachineId(macId);
        IdService object = null;
        try {
            object = factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }
        return object;
    }

}
