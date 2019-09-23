idea:excel的一行数据导入生成一个dto后，根据dto类上的注解进行参数校验

step0:建一个spring boot项目，写一个文件上传接口 done

step1:引入poi组件，处理导入的excel文件，将excel内容转移到dto done

step2:为DTO加上注解，dto生成后根据注解校验数据合法性

step3:自定义一个注解，并在dto上使用

到这一步遇到两个问题：
1. cell为空跳过，但是dto上又不允许此字段为空
2. 以后每加一个注解，就要改一次controller上的校验代码，耦合度高

先略过问题1，看问题2，立马想到责任链模式，未完待续...