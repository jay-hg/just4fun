爬虫学习  
先定一个小目标：爬取boss直聘上各个城市java岗位的需求量

1. 先从知乎获取html页面

2. 请求带参数

    至此，成功从boss直聘上获取数据，接下来考虑:  

3. 如何解析使用返回的数据？  
源数据html
```$xslt
<li class="\&quot;item\&quot;">\n <a href="\&quot;/job_detail/5966a65fb6b8f51f1Hd93ti0EFQ~.html\&quot;" ka="\&quot;job_list_0\&quot;" data-itemid="\&quot;0\&quot;" data-lid="\&quot;7f6svMvH8um.search.1\&quot;" data-index="\&quot;0\&quot;">\n <img src="\&quot;https://img.bosszhipin.com/beijin/mcs/chatphoto/20ca9f1be24a4cd2e6f1c6e9e47b9b0f-48bc5e623d2778881n1509i8DggQl9s~.jpeg?x-oss-process=image/resize,w_120,limit_0\&quot;/" />\n 
    <div class="\&quot;text\&quot;">
     \n 
     <div class="\&quot;title\&quot;">
      <h4>Java</h4>
      <span class="\&quot;salary\&quot;">11-22K</span>
     </div>\n 
     <div class="\&quot;name\&quot;">
      新致软件
     </div>\n 
     <div class="\&quot;msg\&quot;">
      <em>上海</em>
      <em>3-5年</em>
      <em>大专</em>
     </div>\n 
    </div>\n </a>\n </li>\n 
  
  ...
  
  <li class="\&quot;item\&quot;">\n <a href="\&quot;/job_detail/704c56e4d9e3e1081nV82tq-E1A~.html\&quot;" ka="\&quot;job_list_0\&quot;" data-itemid="\&quot;0\&quot;" data-lid="\&quot;7f6svMvH8um.search.15\&quot;" data-index="\&quot;14\&quot;">\n <img src="\&quot;https://img.bosszhipin.com/beijin/mcs/chatphoto/20180705/8f3d9504aeb0ba094ccfb059b6c31ccc0e9cd96778a5b4dc3010728100164f4a_s.jpg?x-oss-process=image/resize,w_120,limit_0\&quot;/" />\n 
    <div class="\&quot;text\&quot;">
     \n 
     <div class="\&quot;title\&quot;">
      <h4>Java</h4>
      <span class="\&quot;salary\&quot;">20-40K</span>
     </div>\n 
     <div class="\&quot;name\&quot;">
      上海三思
     </div>\n 
     <div class="\&quot;msg\&quot;">
      <em>上海</em>
      <em>5-10年</em>
      <em>本科</em>
     </div>\n 
    </div>\n </a>\n </li>
```
 从中提取出
 ```$xslt
详情链接	语言/职位类别	薪资	公司名称	工作地点	经验	学历
/job_detail/5966a65fb6b8f51f1Hd93ti0EFQ~.html	Java	11-22K	新致软件	上海	3-5年	大专
```

现在已经基本实现爬取boss直聘单个网址里边工作岗位的功能，接下来，
要实现哪个功能？  
plan A:数据落地  
plan B:改变分页条件，持续爬取更多数据  
plan C:提取通用代码，灵活支持其他网站其他内容的爬取

决定先实行plan B，然后是A，再解决新遇到的问题，其他问题都完善以后，
再提取通用代码，试着支持其他网站其他内容的爬虫。