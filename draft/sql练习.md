题目来源：知乎 https://zhuanlan.zhihu.com/p/32137597
1. 查询" 01 "课程比" 02 "课程成绩高的学生的信息及课程分数
```aidl
SELECT * FROM
(SELECT * FROM sc WHERE sc.CId = 01) as t1,(SELECT * FROM sc WHERE sc.CId = 02) as t2
WHERE t1.Sid = t2.Sid and t1.score > t2.score
```
或者
```aidl
SELECT * FROM sc sc1 INNER JOIN sc sc2 on sc1.CId = 01 and sc2.CId = 02 and sc1.SId = sc2.Sid and sc1.score > sc2.score
```
2. 查询同时存在" 01 "课程和" 02 "课程的情况
```aidl
SELECT * FROM sc sc1 INNER JOIN sc sc2 on sc1.CId = 01 and sc2.CId = 02 and sc1.SId = sc2.Sid
```
3. 查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
```aidl
SELECT student.SId,student.Sname,(sc.score) avg FROM student
INNER JOIN sc on student.SId = sc.SId
GROUP BY student.SId
HAVING avg > 60
```

4. 查询在 SC 表存在成绩的学生信息
```aidl
SELECT DISTINCT student.* FROM student,sc WHERE student.SId = sc.SId
```

5. 查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩(没成绩的显示为null)
```aidl
SELECT student.SId,student.Sname,COUNT(sc.SId) count,(sc.score) sum
FROM student LEFT JOIN sc 
on student.SId = sc.SId
GROUP BY student.SId
```

6. 查询学过「张三」老师授课的同学的信息
```aidl
SELECT student.* FROM student
INNER JOIN sc on student.SId = sc.SId
INNER JOIN course on sc.CId = course.CId
INNER JOIN teacher on course.TId = teacher.TId
WHERE teacher.Tname = '张三'
```

7. 查询没有学全所有课程的同学的信息
```aidl
SELECT count(sc.SId) cnt,sc.SId FROM sc GROUP BY sc.SId
HAVING cnt < (SELECT count(*) FROM course)
```

8. 查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
```mysql
SELECT DISTINCT student.* FROM student 
INNER JOIN sc sc1 on student.SId = sc1.SId
WHERE EXISTS (
	SELECT sc2.CId FROM sc sc2 WHERE sc2.SId = 01 and sc1.CId = sc2.CId and sc1.SId <> sc2.SId
)
```

9. 查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩
```mysql
SELECT student.SId,student.Sname,avg(sc.score) avg
FROM student INNER JOIN sc on student.SId = sc.SId
WHERE EXISTS(
	SELECT sc2.SId FROM sc sc2 WHERE sc2.score<60 GROUP BY sc2.SId
		HAVING count(1) >= 2 and student.SId = sc2.SId
)
GROUP BY sc.SId
```

10. 按照出生日期来算，当前月日 < 出生年月的月日则，年龄减一
```mysql
SELECT *,TIMESTAMPDIFF(YEAR,student.Sage,CURRENT_DATE()) as age FROM student
```

11. 查询本周过生日的学生
```mysql
SELECT * FROM student WHERE YEARWEEK(student.Sage) = YEARWEEK(CURRENT_DATE())
```



