package com.lym.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.lym.interceptor.ShopLoginInterceptor;
import com.lym.interceptor.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 开启MVC,自动注入spring容器,WebMvcConfigurerAdapter:配置视图解析器
 * 当一个类实现了这个借口后(ApplicationContextAware)之后.这个类就可以方便获得ApplicationContext所有的bean
 *
 * @ClassName MvcConfiguration
 * @Author lyming
 * @Date 2019/4/9 23:52
 **/
@Configuration
//等价于<mvc:annotation-driven />
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    //spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置:对标<mvc:resources mapping="/resources/**" location="/resources/" />
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:I:\\upload\\");
        registry.addResourceHandler("/upload/images/**").addResourceLocations("file:/Users/lym/pictures/o2o/images/");
    }

    /**
     * 定义默认的请求处理器:对标<mvc:default-servlet-handler />
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建ViewResolver视图解析器
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //设置spring容器
        viewResolver.setApplicationContext(applicationContext);
        //取消缓存
        viewResolver.setCache(false);
        //设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        //设置视图解析的后缀
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    /**
     * 文件上传解析器
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);

        return multipartResolver;
    }

    //是否有边框
    @Value("${kaptcha.border}")
    private String border;
    //字体颜色
    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;
    //图片宽度
    @Value("${kaptcha.image.width}")
    private String width;
    //图片高度
    @Value("${kaptcha.image.height}")
    private String height;
    //使用哪些字符生成验证码
    @Value("${kaptcha.textproducer.char.string}")
    private String cString;
    //字体大小
    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;
    //干扰线的颜色
    @Value("${kaptcha.noise.color}")
    private String nColor;
    //字符个数
    @Value("${kaptcha.textproducer.char.length}")
    private String cLength;
    //字体:宋体
    @Value("${kaptcha.textproducer.font.names}")
    private String fNames;


    /**
     * 由于web.xml不生效了,需要在这里配置Kaptcha验证码Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
        servlet.addInitParameter("kaptcha.border",border);
        servlet.addInitParameter("kaptcha.textproducer.font.color",fcolor);
        servlet.addInitParameter("kaptcha.image.width",width);
        servlet.addInitParameter("kaptcha.image.height",height);
        servlet.addInitParameter("kaptcha.textproducer.char.string",cString);
        servlet.addInitParameter("kaptcha.textproducer.font.size",fsize);
        servlet.addInitParameter("kaptcha.noise.color",nColor);
        servlet.addInitParameter("kaptcha.textproducer.char.length",cLength);
        servlet.addInitParameter("kaptcha.textproducer.font.names",fNames);

        return servlet;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        //注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截器路径
        loginIR.addPathPatterns(interceptPath);
        //还可以注册其他的拦截器
        InterceptorRegistration permissonIR = registry.addInterceptor(new ShopPermissionInterceptor());
        permissonIR.addPathPatterns(interceptPath);
        /** 配置不拦截的路径 */
        //shoplist page
        permissonIR.excludePathPatterns("/shopadmin/shoplist");
        permissonIR.excludePathPatterns("/shopadmin/getshoplist");
        //shopregister page
        permissonIR.excludePathPatterns("/shopadmin/getshopinfo");
        permissonIR.excludePathPatterns("/shopadmin/registershop");
        permissonIR.excludePathPatterns("/shopadmin/shopoperation");
        //shopmanage page
        permissonIR.excludePathPatterns("/shopadmin/shopmanagement");
        permissonIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
    }
}
