# amvc

已经可以用了～～～

目前只能返回json类型，视图部分还没有做。

## 开发记录
有兴趣的看一下吧
1.  [自己写一个mvc框架吧（一）](https://juejin.im/post/5c539c6de51d455223301f52)
2.  [自己写一个mvc框架吧（二）](https://juejin.im/post/5c539f036fb9a049fc03f45c)
3.  [自己写一个mvc框架吧（三）](https://juejin.im/post/5c54015651882562d17d7494)
4.  [自己写一个mvc框架吧（四）](https://juejin.im/post/5c61017951882562547b873a)
5.  [自己写一个mvc框架吧（五）](https://juejin.im/post/5c63b2cc51882528735f1e15)

## 怎么用呢？

1：在**web.xml**中添加：

```xml
<servlet>
    <servlet-name>mvc</servlet-name>
    <servlet-class>com.hebaibai.amvc.MvcServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>mvc</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

2：在 **resources** 中添加配置文件 **mvc.json** 。

```json
{
  "annotationSupport": true,//开启注解支持
  "annotationPackage": "com.hebaibai.demo.web"//要被扫描的包名
}
```

3：在 **class** 上添加注解 **@Request** 。

4：在 **方法** 上添加注解。

```java

import com.hebaibai.amvc.annotation.Request;

/**
 * @author hjx
 */
@Request("index")
public class IndexController {

    @Request(value = "/test/info")
    public String testGet() {
        return "success";
    }

}

```

5：启动项目，访问：http://127.0.0.1:8080/[项目名称]/index/test/info 就好了。

## 指定请求方式

```java
//get
@Request(value = "test", type = RequestType.GET)
public String testGet() {
    return "success";
}
//post
@Request(value = "test", type = RequestType.POST)
public String testPost() {
    return "success";
}
//put
@Request(value = "test", type = RequestType.PUT)
public String testPut() {
    return "success";
}
//delete
@Request(value = "test", type = RequestType.DELETE)
public String testDelete() {
    return "success";
}
```

## 接收参数

1：基本形式：

支持基本类型的数据自动转换。

参数名称为方法入参的名称。

```java
//get请求
//test?name=何白白&age=20
@Request(value = "test", type = RequestType.GET)
public String testGet(String name,int age) {
	return "success";
}
//post put get一样
```

2：路径传参

路径中使用占位符 **{}**，占位符中的名称要和参数名称对应。

```java
@Request(value = "user/{name}/{age}", type = RequestType.GET)
public String user(String name, int age) {
    return "success";
}
```



## 与Ioc框架集成

1：写一个类继承 MvcServlet.class

```java

import com.hebaibai.amvc.Application;
import com.hebaibai.amvc.MvcServlet;
import com.hebaibai.amvc.objectfactory.ObjectFactory;

public class MyMvc extends MvcServlet {

    /**
     * 执行其他内容
     * @param application
     */
    @Override
    protected void afterInitMvc(Application application) {
        application.setObjectFactory(new ObjectFactory() {
            @Override
            public Object getObject(Class objectClass) {
                //返回通过ioc框架实例化的对象
                object
                return object;
            }
        });
    }
}
```

修改 **web.xml**

```xml
<servlet>
    <servlet-name>mvc</servlet-name>
    <servlet-class>写你自己实现的，继承了MvcServletde的类名</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>mvc</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```





