# Schedule_Demo
这是一个基于Spring Boot和Quartz框架的定时任务Demo

## POST 新增定时任务
http://localhost:8080/quartz/add
```
{
    "quartzId": 1,
    "jobName": "ScheduleJob1",
    "jobGroup": "AppName1",
    "startTime": "2020-07-05 22:15:00",
    "cron": "* */30 * * * ?",
    "invokeParam": "",
    "creator": "Victor Lin",
    "createTime": "2020-07-05 22:15:00",
    "updateTime": "2020-07-05 22:15:00"
}
```

## GET 查看定时任务
http://localhost:8080/quartz?jobGroup=AppName1&jobName=ScheduleJob1

## PUT 修改定时任务
http://localhost:8080/quartz
```
{
    "jobName": "ScheduleJob1",
    "jobGroup": "AppName1",
    "startTime": "2020-07-05 22:15:00",
    "cron": "* */30 * * * ?",
    "invokeParam": "",
    "creator": "Victor Lin"
}
```

## DELETE 修改定时任务
http://localhost:8080/quartz
```
{
    "jobName": "ScheduleJob1",
    "jobGroup": "AppName1"
}
```