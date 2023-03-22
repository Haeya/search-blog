# Blog Search API
(깃허브 : https://github.com/Haeya/search-blog)
(다운로드 : https://drive.google.com/file/d/1PBZhS2l8Ez3MyLN1w6IqfEr00VvzqHTJ/view?usp=share_link)
___
### Description
입력된 검색어로 카카오, 네이버 블로그 검색 Open API를 활용하여 '블로그 검색 서비스'를 제공합니다.

### Endpoint
`GET` /blog

### Request Parameters
| Name    | Type    | Required | Default    | Description                                   |
|---------|---------|----------|------------|-----------------------------------------------|
| `query` | string  | **Yes**  | N/A        | 검색어                                           |
| `sort`  | string  | No       | `accuracy` | 검색 결과 정렬 기준 (`accuracy` - 정확도순, `recency` - 최신순) |
| `page`  | integer | No       | `1`        | 페이지 번호                                        |
| `size`  | integer | No       | `10`       | 한 페이지에 보여질 검색 결과 수                            |

### Response
+ `data`
  + `body`
    +  `documents`: 검색 결과 리스트
  + `statusCodeValue`: API 결과 코드 Value
  + `statusCode`: API 결과 코드
+ `message`: API 호출 메시지

### Example Request
```kotlin
GET '/blog?query=test&sort=recency&page=1&size=10'
```

### Example Response
+ `kakao api`
<pre><code>HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
    "data": {
        "headers": {},
        "body": {
          "documents": [
              {
                  "blogname": "<b>test</b> blog",
                  "contents": "<b>test</b> data입니다...",
                  "datetime": "2023-03-22T01:01:01.000+09:00",
                  "thumbnail": "",
                  "title": "<b>test</b> data입니다.",
                  "url": "http://blog.naver.com/<b>test</b>/1"
              },
              ...,
              {
                  "blogname": "<b>test</b> blog",
                  "contents": "<b>test</b> data입니다...",
                  "datetime": "2023-03-22T01:01:01.000+09:00",
                  "thumbnail": "",
                  "title": "<b>test</b> data입니다.",
                  "url": "http://blog.naver.com/<b>test</b>/10"
              },
              ...
          ],
          "pagination": {
                "page": 1,
                "size": 10,
                "totalCount": 20,
                "totalPages": 2
          }
        },
        "statusCodeValue": 200,
        "statusCode": "OK"
    },
    "message": "success"
}</code></pre>
+ `naver api`: `kakao api`의 결과 코드가 200이 아닌 경우
<pre><code>HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
    "data": {
        "headers": {},
        "body": {
          "documents": [
              {
                  "title": "<b>test</b> data입니다.",
                  "link": "https://blog.naver.com/<b>test</b>/1",
                  "description": "<b>test</b> data입니다...",
                  "bloggername": "<b>test</b>blogger",
                  "bloggerlink": "blog.naver.com/<b>test</b>",
                  "postdate": "20230322"
              },
              ...,
              {
                  "title": "<b>test</b> data입니다.",
                  "link": "https://blog.naver.com/<b>test</b>/10",
                  "description": "<b>test</b> data입니다...",
                  "bloggername": "<b>test</b>blogger",
                  "bloggerlink": "blog.naver.com/<b>test</b>",
                  "postdate": "20230322"
              }
          ],
          "pagination": {
                "page": 1,
                "size": 10,
                "totalCount": 20,
                "totalPages": 2
          }
        },
        "statusCodeValue": 200,
        "statusCode": "OK"
    },
    "message": "success"
}</code></pre>
___
### Description
많이 검색된 순서대로 최대 10개의 검색어를 반환합니다.

### Endpoint
`GET` /keyword

### Request Parameters

| Name   | Type    | Required | Default | Description          |
|--------|---------|----------|---------|----------------------|
| `size` | integer | No       | `10`    | 키워드 목록에 포함될 최대 검색어 수 |

### Response
+ `data`
  + `keywords`: 인기 검색어
  + `count`: 검색어 별 검색된 횟수
+ `message`: API 호출 메시지


### Example Request
```kotlin
GET '/keyword?size=4'
```
### Example Response
<pre><code>HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "data": [
    {
      "keyword": "spring boot",
      "count": 30
    },
    {
      "keyword": "kotlin",
      "count": 25
    },
    {
      "keyword": "restful api",
      "count": 20
    },
    {
      "keyword": "java",
      "count": 10
    }
  ],
  "message": "success"
}</code></pre>
