# Jenkins构建脚本如何编写
- Git Parameter  
点击高级可以选择filter,排序方式，列表数量等等
- Choice Parameter  
提供列表供选择
- 源码管理  
选择Multiple SCMs支持从多个地址上拉取代码
- Pre Steps  
构建前操作，可以运行前端的构建项目，
`Trigger/call builds on other project`
前端构建完之后运行
powershell脚本拷贝文件（前后端不分离的情况），然后再构建
后端maven工程
- Build
Root POM : pom.xml  
Goals and options: clean package -DskipTests -P${dev_test等环境参数}
- 构建后操作  
传送文件到服务器  
Transfers:  
    source files: xx/xx/xx.zip
    ....