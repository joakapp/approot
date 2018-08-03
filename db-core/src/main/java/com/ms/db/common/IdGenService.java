package com.ms.db.common;

import com.ms.db.config.SpringContextBeanUtil;
import com.robert.vesta.service.intf.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.StringContent;

@Component
public class IdGenService {

    /**
     * 在流水号生成的基础上新增前置号
     * @param prfix
     * @return
     */
    public String genServiceId(String prfix){
        IdService idService = SpringContextBeanUtil.getBean(IdService.class);
        return prfix+idService.genId();
    }

    /**
     * 不需要前置服务号
     * @return
     */
    public String genServieId(){
        return genServiceId("");
    }
}
