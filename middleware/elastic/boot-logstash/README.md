# logstash 日志配置

## 文件日志格式 FILE_LOG_PATTERN

```text
%date{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%15.15thread] %-40.40logger{39} : %msg%n
```


## grok 配置

关键思路：定长匹配

```text
  grok {
    match => [ "message",
               "(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}) %{LOGLEVEL:level}\s+\[\s*(?<thread>.{15})\] (?<class>.{40}) : (?<logmessage>.*)",
               "message",
               "(?<timestamp>%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}) %{LOGLEVEL:level} .+? :\s+(?<logmessage>.*)"
             ]
  }
```