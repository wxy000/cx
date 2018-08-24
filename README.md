# cx 辰星商城
分布式，采用了soa面向服务的架构

## cx-parent（父）
## cx-common（工具）
## cx-manager（管理）
### cx-manager-pojo（逆向工程pojo）
### cx-manager-dao（逆向工程mapper）
### cx-manager-interface（商品管理接口）
### cx-manager-service（商品管理服务——接口实现）
#### tomcat端口为8080
#### dubbo服务端口20880
## cx-manager-web（后台管理）
#### 端口为8081
## cx-portal-web（商城入口——首页）
#### 端口8082
#### 采用了thymeleaf模板
## cx-content（内容管理）
### cx-content-interface（内容管理接口）
### cx-content-service（内容管理服务）
#### tomcat8083
#### dubbo20881