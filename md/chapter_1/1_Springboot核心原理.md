# Springboot 核心原理
## 核心运行原理
Springboot 通过 `@EnableAutoConfiguration` 注解开启自动配置，加载`spring.factories` 中注册的各种 `AutoConfiguration`类，当某个`@Conditionnal`指定的生效条件（`Starters` 提供的依赖、配置或Spring容器中是否存在某个Bean等）时，实例化改`AutoConfiguration`类中定义的Bean（组件等）,就可以完成依赖框架的自动配置

* **`@EnableAutoConfiguration`**：改注解由组合注解 `@SpringBootApplication` 引入，完成自动配置开启，扫描各个jar包下的`spring.factories`文件，并加载文件中注册的 `XxxAutoConfiguration`类等

* **`spring.factories`**：配置文件，位于jar包的`META-INF`目录下，按照指定格式注册列自动配置的`AutoConfiguration`类。`spring.facotries`也可以包含其他类型待注册的类。改配置文件不仅存在于Spring Boot 项目中，也可以存在于自定义的自动配置（或Starter）项目中。

* **`AutoConfiguration`类**：自动配置类，代表了Springboot中一类以 `XXXAutoConfiguration`命名的自动配置类。其中定义了三方组件集成Spring所需初始化的Bean和条件

* **`Staters`** 三方组件的依赖以及配置，Spring Boot已经预置的组件。Springboot默认的Starters项目往往只包含一个pom依赖的项目。如果自定义的 Starter,改项目需要包含 `spring.factories`文件、`XxxAutoConfiguration`类和其他配置类

## 源码解析 `@EnableAutoConfiguration` 

`@SpringBootApplication`

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    
    // 排除指定自动配置的类
    @AliasFor(annotation = EnableAutoConfiguration.class)
    Class<?>[] exclude() default {};
    
    // 排除指定自动配置类名
    @AliasFor(annotation = EnableAutoConfiguration.class)
    String[] excludeName() default {};
    
    // 指定扫描的包，激活注解组件的初始化
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    // 指定扫描的类，用于初始化
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};
    
    // 指定是否代理 @Bean 方法以强制执行 bean的生命周期
    @AliasFor(annotation = Configuration.class)
    boolean proxyBeanMethods() default true;
}

```
