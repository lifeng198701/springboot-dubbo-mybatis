package com.lifeng.sdm.serviceimpl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by lifeng on 2018/5/19.
 * 扫描dubbo属性文件,以及xml文件
 */
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource({ "classpath:dubbo/*.xml" })
public class DubboConfig {

}
