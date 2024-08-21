# WebSocket 練習專案

## 配置

如果第一次啟動H2並且是檔案型資料庫，  
application.properties裡將這行註解打開才會創建table，  
創建以後就可以註解回去
```properties
#spring.sql.init.mode=always
```

要使用AI先在application.properties裡補上apikey
```properties
spring.ai.openai.api-key=
```
