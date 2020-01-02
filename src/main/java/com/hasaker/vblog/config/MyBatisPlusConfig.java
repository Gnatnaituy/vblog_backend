package com.hasaker.vblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @package com.hasaker.vblog.config
 * @author 余天堂
 * @create 2020/1/2 13:48
 * @description MyBatisPlusConfig
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置分页
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);

        return paginationInterceptor;
    }

//    /**
//     * 配置自定义ID生成器
//     */
//    @Bean
//    public IdentifierGenerator identifierGenerator() {
//        return new SnowFlakeIdGenerator();
//    }
}
