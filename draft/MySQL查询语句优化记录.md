## 关于MySQL的索引
加索引时，索引类型和索引方法怎么确定？
### 索引类型
- FULLTEXT  
只有MyISAM引擎支持，用于长文本全文查找
- NORMAL  
普通索引，不知道用什么索引就用普通索引
- SPATIAL  
空间索引，innodb不支持
- UNIQUE  
比如id，能保证字段值不重复就可以使用
### 索引方法
btree和hash，顾名思义，这两种的实现方式分别是b+树和hash，innodb默认是btree
- BTREE  
缺点是相对于hash的，hash一般只需一次就能命中，而btree需要
在树的节点上访问多次
- HASH  
缺点是不支持范围查找，不支持多列索引的最左前缀原则特性，
不支持like
## 性能监控手段
- 执行计划  
explain select * ......
- 查看语句执行时间，配置等
```
show profiles 

show profile for query 16

show variables like 'slow_query%'
```
## 其他延伸
- inner join 和 left join 的区别？  
都是连接，a left join b的实现原理，是全表查a，
比如取出100条数据，再循环100次去连接b。
（假设后面没有where条件了）  
left join、right join都一样，先查哪个表的顺序已经
定死了，inner join不一样，mysql会灵活判断，哪个
表先查能用到索引，或者效率高，就用哪个表先查。
- 索引建立有哪些原则？
多列索引注意最左前缀；尽量扩展索引而不是新建；不同值较少
的列不适合建索引
