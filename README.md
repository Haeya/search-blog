# Blog Search API
___
### Description
입력된 검색어로 카카오, 네이버 등 블로그 검색 Open API를 활용하여 '블로그 검색 서비스'를 제공합니다.

### Endpoint
`GET` /search/blog

### Request Parameters
| Name    | Type    | Required | Default    | Description                           |
|---------|---------|----------|------------|---------------------------------------|
| `query` | string  | **Yes**  | N/A        | 검색어                                   |
| `sort`  | string  | No       | `accuracy` | 검색 결과 정렬 기준 (`accuracy` or `recency`) |
| `page`  | integer | No       | `1`        | 페이지 번호                                |
| `size`  | integer | No       | `10`       | 한 페이지에 보여질 검색 결과 수                    |

### Response
+ `result`
  + `documents`: 검색 결과 리스트
  + `title`: 제목
  + `contents`: 내용
  + `datetime`: 작성일
+ `meta`
  + `total_count`: 총 검색 결과 수
  + `pageable_count`: 한 페이지에 표시된 검색 결과 수
  + `is_end`: 마지막 페이지 여부
+ `status`: API 호출 상태
+ `message`: API 호출 메시지

### Example Request
```kotlin
GET '/search/blog?query=코틀린&sort=recency&page=2&size=5'
```

### Example Response
<pre><code>HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
    "result": {
        "documents": [
            {
                "title": "코틀린 연산자 오버로딩",
                "contents": "코틀린 연산자 오버로딩이 가능합니다. 연산자 오버로딩은 operator 예약어로 정의합니다.",
                "url": "https://juneyr.dev/kotlin/operator-overloading/",
                "datetime": "2022-03-14T16:28:13.000+09:00"
            },
            {
                "title": "코틀린에서 자료형의 범위 지정하기 - sealed class",
                "contents": "코틀린에서 자료형의 범위를 지정하는 방법 중 하나가 sealed class입니다. sealed class는 상속이 가능한 enum class로 볼 수 있습니다.",
                "url": "https://velog.io/@ehdrhks0000/Kotlin-Sealed-Class-%EC%B2%98%EB%A6%AC",
                "datetime": "2022-03-13T13:22:54.000+09:00"
            },
            ...
        ],
        "meta": {
            "total_count": 100,
            "pageable_count": 5,
            "is_end": false
        }
    },
    "status": "OK",
    "message": null
}
</code></pre>
___
### Description
많이 검색된 순서대로 최대 10개의 검색어를 반환합니다.

### Endpoint
`GET` /search/keywords

### Request Parameters

| Name   | Type    | Required | Default | Description          |
|--------|---------|----------|---------|----------------------|
| `size` | integer | No       | `10`    | 키워드 목록에 포함될 최대 검색어 수 |

### Response
+ `result`
  + `keywords`: 검색어 리스트
+ `status`: API 호출 상태
+ `message`: API 호출 메시지


### Example Request
```kotlin
GET '/search/keywords'
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
}
</code></pre>