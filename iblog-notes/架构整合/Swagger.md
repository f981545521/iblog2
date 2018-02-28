### 使用springfox整合SpringMVC和Swagger

#### [参考文档](http://blog.csdn.net/haoyifen/article/details/52703376)

### 构建步骤
1. pom.xml
```
        <!-- Swagger 构建 API -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.6.1</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.6.1</version>
        </dependency>
```
2. Swagger配置类
```
    @Configuration
    @EnableSwagger2
    public class SwaggerConfig {
        @Bean
        public Docket createRestApi() {
            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title("Swagger2 生成的APIs")
                    .description("客户端与服务端接口文档")
                    .termsOfServiceUrl("http://acyou.cn")
                    .contact(new Contact("youfang",null,null))
                    .version("1.0.0")
                    .build();
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("cn.acyou.iblog.controller"))
                    .paths(PathSelectors.any())
                    .build();
        }
    }
```
3. Spring-mvc支持
```
    <bean class="cn.acyou.iblog.swagger.SwaggerConfig" />
    <context:component-scan base-package="cn.acyou.iblog"/>
    <mvc:default-servlet-handler/>
```
### 注意
1. web.xml中使用Swagger时 ， url-pattern必须是：/
```
    <!-- Spring主配置 -->
    <servlet>
        <description></description>
        <display-name>DispatcherServlet</display-name>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <description></description>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/spring-*.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <!--使用Swagger时 ， url-pattern必须是：/ -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

2. 使用Swagger后 ， 测试用例中需要加上`@WebAppConfiguration`注解
报错信息：`Error creating bean with name 'documentationPluginsBootstrapper' ...`