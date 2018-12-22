ALPACA BLOG
========
ALPACA BLOG 是单用户博客系统，分为前端和后端两部分，前端使用vue+quasar编写，后端以Spring框架为主。

- 前端地址： [alpaca_blog_vue_v2](https://github.com/yd1994/alpaca_blog_vue_v2)
- 后端地址： [alpaca_blog_v2](https://github.com/yd1994/alpaca_blog_v2)
- 线上地址： [YD的BLOG](https://www.yd1994.com)

线上版本未备案（务必加上https://），可多次尝试，可能无法访问。
查看后台可以使用默认用户名密码直接点击登录，但是该账户只有查看，没有修改权限（post、put、delete请求将返回403）。


特点
========
- 前后端分离
- 前台多平台兼容（可自适应pc、手机浏览器，可构建成ios、android、Electron应用）
- restful API
- Spring Cloud 部分功能加持（由于服务器内存有限，只使用Eureka，oauth、feign，未使用config，zuul等功能）


说明
=======
该项目为后端


技术详细
=======
- Spring Boot web
- Spring Boot Data Redis
- MyBatis Plus
- MySQL
- Spring Cloud Eureka
- Spring Cloud Oauth2
- Spring Cloud OpenFeign

后端主要功能
=======
后端分为3部分：
- [注册中心（registry）](https://github.com/yd1994/alpaca_blog_v2/tree/master/registry)
- [授权中心（auth-service）](https://github.com/yd1994/alpaca_blog_v2/tree/master/auth-service)
- [博客服务（blog-service）](https://github.com/yd1994/alpaca_blog_v2/tree/master/blog-service)
- [统计服务（statistics-service）（未上线）](https://github.com/yd1994/alpaca_blog_v2/tree/master/statistics-service)

注册中心
--------
Spring Cloud Eureka 注册注册中心

授权中心
--------
- 单点登录
- 用户权限控制
- 各个服务之间访问权限控制

博客服务
--------
- 博客
- 分类
- 标签

统计服务
--------
目前只实现获取文章词云数据


