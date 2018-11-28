package com.easylinkin.listener;

import com.easylinkin.service.AsyncService;
import com.easylinkin.service.SnmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MyServletContextListener
 *
 */
/**
 * 通过  @WebListener 或者 使用代码注册  ServletListenerRegistrationBean
 * @author Administrator
 *
 */
@WebListener
public class SnmpListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(SnmpListener.class);


    @Autowired
    private AsyncService asyncService;

    @Autowired
    private SnmpService snmpService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("SnmpListener=============================="+" contextInitialized");

        logger.info("start submit");

        //启动一个异步任务
        asyncService.executeAsync();

        //启动另一个异步任务
        snmpService.executeAsync();

        logger.info("end submit");




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("SnmpListener----------------------------"+" contextDestroyed");

    }
}
