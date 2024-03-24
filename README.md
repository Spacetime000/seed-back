# 목차

---

1. 사용 기술
2. 주요 기능
3. API 명세
4. API 명세 자세히

# 사용 기술

---

- Java : 17
- Spring Boot
- Spring Security
- Spring Data Jpa
- Querydsl
- JWT
- MySQL
- Redis

# 주요 기능

---

## [회원]

- 사용자는 아이디, 비밀번호, 이메일, 닉네임을 이용해 회원가입할 수 있다.
- 회원가입시 사용한 아이디와 비밀번호를 이용해 로그인 할 수 있다.

## [공지]

- 생성, 수정, 삭제, 조회를 할 수 있다.
- 파일 또는 이미지를 첨부할 수 있다.
- 최근 작성된 글을 조회할 수 있다.

## [문제]

- 생성, 수정, 삭제, 조회를 할 수 있다.
- 카테고리 및 제목을 조건으로 조회할 수 있다.
- 자신이 작성한 글을 조회할 수 있다.
- 이미지를 첨부할 수 있다.
- 공개/비공개 상태를 변경할 수 있다.
- 카테고리를 생성, 삭제, 조회를 할 수 있다.

## [단어]

- 생성, 수정, 조회를 할 수 있다.
- 공개/비공개 상태를 변경할 수 있다.

## [복습]

- 생성, 수정, 삭제, 조회를 할 수 있다.
- 오늘 날자로 리스트 조회를 할 수 있다.
- 오늘 날자를 포함한 이전 날짜를 리스트 조회를 할 수 있다.

# API 명세

---

| Domain     | URL                                    | Http Method | 설명                  |    접근권한     |
|------------|----------------------------------------|:-----------:|---------------------|:-----------:|
| **Auth**   |                                        |             |                     |             |
|            | `/register`                            |   `POST`    | 회원가입                |             |
|            | `/login`                               |   `POST`    | 로그인(토큰 발급)          |             |
|            | `/refresh-token`                       |    `GET`    | 토큰 재발급              |             |
|            | `/token-valid`                         |    `GET`    | 토큰 검증               |             |
|            | `/member-login-list`                   |    `GET`    | 로그인 중인 회원 리스트 조회    |    ADMIN    |
| **Notice** |                                        |             |                     |             |
|            | `/notice`                              |    `GET`    | 공지 목록 조회            | USER, ADMIN |
|            | `/notice/{id}`                         |    `GET`    | 공지 조회               | USER, ADMIN |
|            | `/notice/recent`                       |    `GET`    | 최근 공지 조회            | USER, ADMIN |
|            | `/admin/notice/write`                  |   `POST`    | 공지 생성               |    ADMIN    |
|            | `/admin/notice/{id}/toggle-visibility` |   `PATCH`   | 공지 상태 변경            |    ADMIN    |
|            | `/notice/{*}`                          |    `PUT`    | 공지 수정               |    ADMIN    |
|            | `/notice/{id}`                         |  `DELETE`   | 공지 삭제               |    ADMIN    |
| **Quiz**   |                                        |             |                     |             |
|            | `/quiz`                                |    `GET`    | 문제 목록 조회            | USER, ADMIN |
|            | `/quiz`                                |   `POST`    | 문제 생성               | USER, ADMIN |
|            | `/quiz/{id}`                           |    `GET`    | 문제 조회               | USER, ADMIN |
|            | `/quiz/{id}`                           |  `DELETE`   | 문제 제거               | USER, ADMIN |
|            | `/quiz/my-page`                        |    `GET`    | 작성한 문제 전체 조회        | USER, ADMIN |
|            | `/quiz/{id}/toggle-visibility`         |   `POST`    | 문제 공개 여부 토글         | USER, ADMIN |
|            | `/quiz/category`                       |    `GET`    | 카테고리 목록 조회          | USER, ADMIN |
|            | `/quiz/category`                       |   `POST`    | 카테고리 목록 추가          |    ADMIN    |
|            | `/quiz/category/{name}`                |  `DELETE`   | 카테고리 목록 제거          |    ADMIN    |
| **Voca**   |                                        |             |                     |             |
|            | `/voca`                                |    `GET`    | 단어장 리스트 조회          | USER, ADMIN |
|            | `/voca`                                |   `POST`    | 단어장 생성              | USER, ADMIN |
|            | `/voca/{id}`                           |    `GET`    | 단어장 조회              | USER, ADMIN |
|            | `/voca`                                |    `PUT`    | 단어장 수정              | USER, ADMIN |
|            | `/voca/my-page`                        |    `GET`    | 작성한 단어장 전체 조회       | USER, ADMIN |
| **Review** |                                        |             |                     |             |
|            | `/review`                              |    `GET`    | 복습 리스트 조회           | USER, ADMIN |
|            | `/review/today`                        |    `GET`    | 오늘 날짜 리스트 조회        | USER, ADMIN |
|            | `/review`                              |   `POST`    | 복습 생성               | USER, ADMIN |
|            | `/review`                              |   `PATCH`   | 복습 업데이트             | USER, ADMIN |
|            | `/review/{id}`                         |    `GET`    | 복습 조회               | USER, ADMIN |
|            | `/review/{id}`                         |  `DELETE`   | 복습 제거               | USER, ADMIN |
|            | `/review/priorToToday`                 |    `GET`    | 오늘 날짜 포함한 이전 리스트 조회 | USER, ADMIN |


# API 명세 자세히

---

## 회원
### 회원가입

| 메서드    | URL         |
|--------|-------------|
| `POST` | `/register` |

**요청 헤더**

| 이름           | 설명               | 필수 |
|--------------|------------------|:--:|
| Content-Type | application/json | O  |

**요청 본문**

| 이름       | 타입     | 설명                              | 필수 |
|----------|--------|---------------------------------|:--:|
| username | String | 아이디(영소문자 및 숫자, 6~20 characters) | O  |
| password | String | 비밀번호(9~20 characters)           | O  |
| email    | String | 이메일                             | O  |
| nickname | String | 닉네임(2~10 characters)            | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/register" \
    -H "Content-Type: application/json" \
-d '{
     "username" : "test123123",
     "password" : "123456789",
     "email" : "test@test.com",
     "nickname" : "tester"
   }';
```

**응답 예제**

```shell
200 OK
```

### 로그인(토큰 발급)

| 메서드    | URL      |
|--------|----------|
| `POST` | `/login` |

**요청 헤더**

| 이름           | 설명               | 필수 |
|--------------|------------------|:--:|
| Content-Type | application/json | O  |

**요청 본문**

| 이름       | 타입     | 설명   | 필수 |
|----------|--------|------|:--:|
| username | String | 아이디  | O  |
| password | String | 비밀번호 | O  |

**응답 본문**

| 이름           | 타입     | 설명        | 필수 |
|--------------|--------|-----------|:--:|
| accessToken  | String | API 통신 토큰 | O  |
| refreshToken | String | 재발급 토큰    | O  |


**요청 예제**

```shell
curl -X POST "http://localhost/login" \
    -H "Content-Type: application/json" \
-d '{
      "username" : "test123123",
      "password" : "123456789"
   }'
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
{
    "accessToken" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.FgeeMoNMDYJRf6C42yWWyn3_SSoSsAc-gs2p6MGcfw8",
    "refreshToken" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.FgeeMoNMDYJRf6C42yWWyn3_SSoSsAc-gs2p6MGcfw8"
}
```

### 토큰 재발급

| 메서드   | URL              |
|-------|------------------|
| `GET` | `/refresh-token` |

**요청 헤더**

| 이름            | 설명                                                     | 필수 |
|---------------|--------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${REFRESH_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름           | 타입     | 설명        | 필수 |
|--------------|--------|-----------|:--:|
| accessToken  | String | API 통신 토큰 | O  |
| refreshToken | String | 재발급 토큰    | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/refresh-token" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
{
    "accessToken" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.FgeeMoNMDYJRf6C42yWWyn3_SSoSsAc-gs2p6MGcfw8",
    "refreshToken" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.FgeeMoNMDYJRf6C42yWWyn3_SSoSsAc-gs2p6MGcfw8"
}
```

### 토큰 검증

| 메서드   | URL            |
|-------|----------------|
| `GET` | `/token-valid` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/token-valid" \
-H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
```

### 로그인 중인 회원 리스트 조회

| 메서드   | URL                  |
|-------|----------------------|
| `GET` | `/member-login-list` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/member-login-list" \
-H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
["test456456","test123123"]
```

### 강제 로그아웃(화이트리스트 제거)

| 메서드    | URL              |
|--------|------------------|
| `POST` | `/forced-logout` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름 | 타입     | 설명  | 필수 |
|----|--------|-----|:--:|
| id | String | 아이디 | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/forced-logout" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -H "Content-Type: application/json" \
-d '{
      "id" : "test123123"
    }'
```

**응답 예제**

```shell
200 OK
```

## 공지

### 생성하기

| 메서드    | URL                   |
|--------|-----------------------|
| `POST` | `/admin/notice/write` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | multipart/form-data                                   | O  |

**요청 본문**

| 이름      | 타입            | 설명   | 필수 |
|---------|---------------|------|:--:|
| title   | String        | 제목   | O  |
| content | String        | 내용   | O  |
| files   | MultipartFile | 첨부파일 | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/admin/notice/write" \
    -H "Content-Type: multipart/form-data" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}" \
    -F 'title=post_title' \
    -F 'content=post_content' \
    -F 'file=file.txt'
```

**응답 예제**

```shell
200 OK
```

### 조회하기

| 메서드   | URL            |
|-------|----------------|
| `GET` | `/notice/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**응답 본문**

| 이름                   | 타입               | 설명       | 필수 |
|----------------------|------------------|----------|:--:|
| id                   | Long             | 고유값      | O  |
| title                | String           | 제목       | O  |
| content              | String           | 내용       | X  |
| createdDate          | LocalDateTime    | 생성 일자    | O  |
| noticeAttachmentList | NoticeAttachment | 첨부파일 리스트 | X  |

**NoticeAttachment**

| 이름           | 타입     | 설명     | 필수 |
|--------------|--------|--------|:--:|
| id           | Long   | 고유값    | O  |
| originalName | String | 본 이름   | O  |
| fileName     | String | UUID   | O  |
| filePath     | String | 파일 URL | O  |
| fileSize     | Long   | 파일 크기  | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/notice/{id}" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
{
    "id": 18,
    "title": "공지 제목입니다.",
    "content": "내용 첫 번째 줄 입니다\r\n내용 두 번째 줄 입니다\r\n\r\n내용 네 번째 줄 입니다.",
    "createdDate": "2024-02-04T03:52:49",
    "noticeAttachmentList": [
        {
            "id": 14,
            "originalName": "Redis-x64-3.0.504.msi",
            "fileName": "c5af9379-8f68-4854-b800-ae004b1c3fa5.msi",
            "filePath": "/files/notice/c5af9379-8f68-4854-b800-ae004b1c3fa5.msi",
            "fileSize": 6733824
        },
        {
            "id": 15,
            "originalName": "sh.php",
            "fileName": "38f67552-95a0-4293-b1de-23278187a1d2.php",
            "filePath": "/files/notice/38f67552-95a0-4293-b1de-23278187a1d2.php",
            "fileSize": 337
        }
    ]
}
```

### 최근 공지 조회하기

| 메서드   | URL              |
|-------|------------------|
| `GET` | `/notice/recent` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 쿼리 파라미터**

| 이름   |   타입    | 설명   | 필수 |
|------|:-------:|------|:--:|
| size | Integer | 문서 수 | X  |

**응답 본문**

| 이름                   | 타입               | 설명       | 필수 |
|----------------------|------------------|----------|:--:|
| id                   | Long             | 고유값      | O  |
| title                | String           | 제목       | O  |
| content              | String           | 내용       | X  |
| createdDate          | LocalDateTime    | 생성 일자    | O  |
| noticeAttachmentList | NoticeAttachment | 첨부파일 리스트 | X  |

**NoticeAttachment**

| 이름           | 타입     | 설명     | 필수 |
|--------------|--------|--------|:--:|
| id           | Long   | 고유값    | O  |
| originalName | String | 본 이름   | O  |
| fileName     | String | UUID   | O  |
| filePath     | String | 파일 URL | O  |
| fileSize     | Long   | 파일 크기  | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/notice/recent?size=2" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "id": 18,
        "title": "공지 제목입니다.",
        "content": "내용 첫 번째 줄 입니다\r\n내용 두 번째 줄 입니다\r\n\r\n내용 네 번째 줄 입니다.",
        "createdDate": "2024-02-04T03:52:49",
        "noticeAttachmentList": [
            {
                "id": 14,
                "originalName": "Redis-x64-3.0.504.msi",
                "fileName": "c5af9379-8f68-4854-b800-ae004b1c3fa5.msi",
                "filePath": "/files/notice/c5af9379-8f68-4854-b800-ae004b1c3fa5.msi",
                "fileSize": 6733824
            },
            {
                "id": 15,
                "originalName": "sh.php",
                "fileName": "38f67552-95a0-4293-b1de-23278187a1d2.php",
                "filePath": "/files/notice/38f67552-95a0-4293-b1de-23278187a1d2.php",
                "fileSize": 337
            }
        ]
    },
    {
        "id": 17,
        "title": "공지 제목입니다.",
        "content": "엔터1\r\n\r\n엔터2\r\n\r\n\r\n엔터3",
        "createdDate": "2024-02-03T10:32:46",
        "noticeAttachmentList": []
    }
]
```

### 목록 조회하기

| 메서드   | URL       |
|-------|-----------|
| `GET` | `/notice` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**응답 본문**

| 이름          | 타입            | 설명    | 필수 |
|-------------|---------------|-------|:--:|
| id          | Long          | 고유값   | O  |
| title       | String        | 제목    | O  |
| createdDate | LocalDateTime | 생성 날짜 | O  |
| status      | Boolean       | 공개 여부 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/notice/{id}" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}" \
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "id": 18,
        "title": "공지 제목입니다.",
        "createdDate": "2024-02-04T03:52:49",
        "status": true
    },
    {
        "id": 17,
        "title": "공지 제목입니다.",
        "createdDate": "2024-02-03T10:32:46",
        "status": false
    },
    {
        "id": 4,
        "title": "첨부파일이 없습니다.",
        "createdDate": "2024-01-27T03:06:18",
        "status": true
    }
]
```

### 수정하기

| 메서드   | URL           |
|-------|---------------|
| `PUT` | `/notice/{*}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | multipart/form-data                                   | O  |

**요청 본문**

| 이름                  | 타입            | 설명          | 필수 |
|---------------------|---------------|-------------|:--:|
| title               | String        | 제목          | O  |
| content             | String        | 내용          | O  |
| files               | MultipartFile | 첨부파일        | O  |
| removeAttachmentIds | Long          | 제거할 첨부파일 ID | X  |

**요청 예제**

```shell
curl -X PUT "http://localhost/notice/{*}" \
    -H "Content-Type: multipart/form-data" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}" \
    -F 'title=post_title' \
    -F 'content=post_content' \
    -F 'files[]=file.txt' \
    -F 'removeAttachmentIds[]=1' \
    -F 'removeAttachmentIds[]=2'
```

**응답 예제**

```shell
200 OK
```

### 삭제하기

| 메서드      | URL            |
|----------|----------------|
| `DELETE` | `/notice/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X DELETE "http://localhost/notice/{id}" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}" 
```

**응답 예제**

```shell
200 OK
```

### 상태 변경하기

| 메서드     | URL                                    |
|---------|----------------------------------------|
| `PATCH` | `/admin/notice/{id}/toggle-visibility` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

```shell
curl -X PATCH "http://localhost/admin/notice/{id}/toggle-visibility" \
    -H "Authorization: Bearer ${REFRESH_TOKEN}" 
```

**응답 예제**

```shell
200 OK
```

## Quiz

### 목록 조회하기

| 메서드    | URL     |
|--------|---------|
| `POST` | `/quiz` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 쿼리 파라미터**

| 이름       |   타입   | 설명      | 필수 |
|----------|:------:|---------|:--:|
| search   | String | 검색 질의어  | X  |
| category | String | 검색 카테고리 | X  |

**응답 본문**

| 이름          | 타입            | 설명    | 필수 |
|-------------|---------------|-------|:--:|
| id          | Long          | 고유값   | O  |
| title       | String        | 제목    | O  |
| category    | String        | 카테고리  | O  |
| createBy    | String        | 작성자   | O  |
| createdDate | LocalDateTime | 생성 날짜 | O  |
| visibility  | Boolean       | 공개 여부 | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/quiz?search=12&category=테스트" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "id": 13,
        "title": "12",
        "category": "테스트",
        "createdDate": "2024-02-29T02:13:52",
        "createBy": "test123123",
        "visibility": true
    }
]
```

### 작성한 목록 조회하기

| 메서드   | URL             |
|-------|-----------------|
| `GET` | `/quiz/my-page` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 쿼리 파라미터**

| 이름       |   타입   | 설명      | 필수 |
|----------|:------:|---------|:--:|
| search   | String | 검색 질의어  | X  |
| category | String | 검색 카테고리 | X  |

**응답 본문**

| 이름          | 타입            | 설명    | 필수 |
|-------------|---------------|-------|:--:|
| id          | Long          | 고유값   | O  |
| title       | String        | 제목    | O  |
| category    | String        | 카테고리  | O  |
| createBy    | String        | 작성자   | O  |
| createdDate | LocalDateTime | 생성 날짜 | O  |
| visibility  | Boolean       | 공개 여부 | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/quiz/my-page?search=12&category=테스트" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "id": 13,
        "title": "12",
        "category": "테스트",
        "createdDate": "2024-02-29T02:13:52",
        "createBy": "test123123",
        "visibility": true
    }
]
```

### 생성하기

| 메서드    | URL     |
|--------|---------|
| `POST` | `/quiz` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름       | 타입     | 설명                                        | 필수 |
|----------|--------|-------------------------------------------|:--:|
| title    | String | 제목                                        | O  |
| category | String | 카테고리 <br/> `/quiz/category` 에 나오는 목록에 한정. | O  |

**응답 본문**

| 이름 | 타입   | 설명        | 필수 |
|----|------|-----------|:--:|
| id | Long | 생성한 문제 ID | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/quiz" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
-d '{
      "title" : "제목",
      "category" : "기타",
    }'
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
{
    "id": 8
}
```

### 조회하기

| 메서드   | URL          |
|-------|--------------|
| `GET` | `/quiz/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**응답 본문**

| 이름        | 타입                | 설명   | 필수 |
|-----------|-------------------|------|:--:|
| id        | Long              | 고유값  | O  |
| title     | String            | 제목   | O  |
| category  | String            | 카테고리 | O  |
| questions | QuestionJsonDto[] | 내용   | X  |
| createBy  | String            | 작성자  | O  |

**QuestionJsonDto**

| 이름       | 타입       | 설명             | 필수 |
|----------|----------|----------------|:--:|
| question | String   | 질문             | O  |
| category | String   | 카테고리(주관식, 객관식) | O  |
| answer   | String   | 해답             | O  |
| options  | String[] | 객관식 옵션         | X  |
| img      | String   | 첨부 이미지         | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/quiz/1" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```


**응답 예제**

```shell
200 OK
Content-Type: application/json;
{
    "id": 7,
    "title": "제목입니다.",
    "category": "펀드투자상담사",
    "questions": [
        {
            "question": "1문제",
            "category": "주관식",
            "answer": "1문제답",
            "options": null,
            "img": null
        },
        {
            "question": "질문",
            "category": "객관식",
            "answer": "2",
            "options": [
                "2",
                "3",
                "4",
                "5",
                "6"
            ],
            "img": null
        },
        {
            "question": "질문입니다.",
            "category": "객관식",
            "answer": "정답입니다",
            "options": [
                "오답입니다",
                "정답입니다",
                "오답",
                "오답입",
                "오답입니"
            ],
            "img": "/files/quiz/0f5205b5-10cf-4ba1-8e9c-91c82e178ef9.png"
        },
        {
            "question": "질문입니다2",
            "category": "주관식",
            "answer": "정",
            "options": null,
            "img": "/files/quiz/10deff79-4a17-4329-b66e-6610ce3c6c31.png"
        }
    ],
    "createBy": "test123123"
}
```

### 내용 추가하기

| 메서드    | URL          |
|--------|--------------|
| `POST` | `/quiz/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | multipart/form-data                                   | O  |

**요청 본문**

| 이름       | 타입        | 설명     | 필수 |
|----------|-----------|--------|:--:|
| question | String    | 질문     | O  |
| category | String    | 카테고리   | O  |
| answer   | String    | 해답     | O  |
| options  | String[ ] | 객관식 옵션 | X  |
| img      | File      | 이미지    | X  |

**응답 본문**

| 이름       | 타입        | 설명     | 필수 |
|----------|-----------|--------|:--:|
| question | String    | 질문     | O  |
| category | String    | 카테고리   | O  |
| answer   | String    | 해답     | O  |
| options  | String[ ] | 객관식 옵션 | X  |
| img      | String    | 이미지    | X  |

**요청 예제**

```shell
curl -X POST "http://localhost/quiz/{id}" \
    -H "Content-Type: multipart/form-data" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -F 'question="질문"' \
    -F 'category="주관식"' \
    -F 'answer="정답"' \
    -F 'img="img.png"' 
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "question": "질문",
        "category": "주관식",
        "answer": "정답",
        "options": null,
        "img": "/files/quiz/c619466a-af88-40fd-8636-d6e9ac5ab50f.PNG"
    }
]
```

### 카테고리 목록 조회하기

| 메서드   | URL              |
|-------|------------------|
| `GET` | `/quiz/category` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**응답 본문**

| 이름   | 타입        | 설명      | 필수 |
|------|-----------|---------|:--:|
| name | String    | 카테고리 명칭 | O  |

**요청 예제**

```shell
curl -X GET "http://localhost/quiz/category" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
Content-Type: application/json;
[
    {
        "name": "정보처리기사"
    },
    {
        "name": "테스트"
    }
]
```

### 카테고리 목록 추가하기

| 메서드    | URL              |
|--------|------------------|
| `POST` | `/quiz/category` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름   | 타입        | 설명      | 필수 |
|------|-----------|---------|:--:|
| name | String    | 카테고리 명칭 | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/quiz/category" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -H 'Content-Type: application/json' 
```

**응답 예제**

```shell
200 OK
```

### 카테고리 목록 제거하기

| 메서드      | URL                     |
|----------|-------------------------|
| `DELETE` | `/quiz/category/{name}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X DELETE "http://localhost/quiz/category/test" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
```

**응답 예제**

```shell
200 OK
```

### 공개/비공개 변경하기

| 메서드    | URL                            |
|--------|--------------------------------|
| `POST` | `/quiz/{id}/toggle-visibility` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/quiz/{id}/toggle-visibility" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" 
```

**응답 예제**

```shell
200 OK
```

### 제거하기

| 메서드      | URL          |
|----------|--------------|
| `DELETE` | `/quiz/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X DELETE "http://localhost/quiz/{id}" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" 
```

**응답 예제**

```shell
200 OK
```

## 단어장

### 리스트 조회하기

| 메서드   | URL     |
|-------|---------|
| `GET` | `/voca` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름          | 타입                | 설명   | 필수 |
|-------------|-------------------|------|:--:|
| id          | Long              | 고유값  | O  |
| id          | title             | 제목   | O  |
| contents    | VocaContentDto[ ] | 내용   | X  |
| createBy    | String            | 작성자  | O  |
| createdDate | LocalDateTime     | 작성날짜 | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    |
|----------------|--------|-------|
| spelling       | String | 영단어   |
| meaning        | String | 뜻     |
| example        | String | 예시    |
| exampleMeaning | String | 예시 해석 |

**요청 예제**

```shell
curl -X GET "http://localhost/voca" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
[
    {
        "id": 4,
        "title": "아무것도",
        "contents": [
            {
                "spelling": "test",
                "meaning": "테스트",
                "example": "...",
                "exampleMeaning": "점점점..."
            },
            {
                "spelling": "lemon",
                "meaning": "레몬",
                "example": "lemon1",
                "exampleMeaning": "레몬1"
            },
            {
                "spelling": "a",
                "meaning": "에이",
                "example": null,
                "exampleMeaning": null
            }
        ],
        "createBy": "test123123",
        "createdDate": "2024-02-03T20:00:06"
    }
]
```

### 생성하기

| 메서드    | URL     |
|--------|---------|
| `POST` | `/voca` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름       | 타입                | 설명 | 필수 |
|----------|-------------------|----|:--:|
| title    | String            | 제목 | O  |
| contents | VocaContentDto[ ] | 내용 | X  |

**VocaContentDto**

| 이름             | 타입     | 설명    |
|----------------|--------|-------|
| spelling       | String | 영단어   |
| meaning        | String | 뜻     |
| example        | String | 예시    |
| exampleMeaning | String | 예시 해석 |

**응답 본문**

| 이름 | 타입   | 설명  | 필수 |
|----|------|-----|:--:|
| id | Long | 고유값 | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/voca" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
    -H 'Content-Type: application/json' \
    -d '{
      "title" : "test",
      "contents" : [
          {
              "spelling" : "lemon",
              "meaning" : "레몬"
          }
      ]
    }'
```

**응답 예제**

```shell
200 OK
{
    "id": "14"
}
```

### 단어장 조회하기

| 메서드   | URL          |
|-------|--------------|
| `GET` | `/voca/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름       | 타입                | 설명  | 필수 |
|----------|-------------------|-----|:--:|
| id       | Long              | 고유값 | O  |
| title    | String            | 제목  | O  |
| contents | VocaContentDto[ ] | 내용  | X  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/voca/4" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
{
    "id": 4,
    "title": "아무것도",
    "contents": [
        {
            "spelling": "test",
            "meaning": "테스트",
            "example": "...",
            "exampleMeaning": "점점점..."
        },
        {
            "spelling": "lemon",
            "meaning": "레몬",
            "example": "lemon1",
            "exampleMeaning": "레몬1"
        },
        {
            "spelling": "a",
            "meaning": "에이",
            "example": null,
            "exampleMeaning": null
        }
    ],
    "createBy": "test123123",
    "createdDate": "2024-02-03T20:00:06"
}
```

### 단어장 수정하기

| 메서드   | URL     |
|-------|---------|
| `PUT` | `/voca` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름       | 타입                | 설명  | 필수 |
|----------|-------------------|-----|:--:|
| id       | Long              | 고유값 | O  |
| title    | String            | 제목  | O  |
| contents | VocaContentDto[ ] | 내용  | X  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X PUT "http://localhost/voca" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -H 'Content-Type: application/json'
    -d '{
        "id" : 4,
        "title" : "test",
        "contents" : [
            {
                "spelling" : "lemon",
                "meaning" : "레몬"
            }
        ]
    }'
```


**응답 예제**

```shell
200 OK
```

### 작성한 단어장 리스트 조회하기

| 메서드   | URL             |
|-------|-----------------|
| `GET` | `/voca/my-page` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름          | 타입                | 설명       | 필수 |
|-------------|-------------------|----------|:--:|
| id          | Long              | 고유값      | O  |
| title       | String            | 제목       | O  |
| contents    | VocaContentDto[ ] | 내용       | X  |
| createdDate | LocalDateTime     | 생성 날짜    | O  |
| createBy    | String            | 생성자      | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/my-page" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
[
    {
        "id": 1,
        "title": "test",
        "contents": [
            {
                "spelling": "lemon",
                "meaning": "레몬",
                "example": null,
                "exampleMeaning": null
            }
        ],
        "createBy": "test123123",
        "createdDate": "2024-01-11T02:28:45",
        "visibility": false
    },
]
```


## 복습

### 리스트 조회하기

| 메서드   | URL       |
|-------|-----------|
| `GET` | `/review` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름          | 타입                | 설명       | 필수 |
|-------------|-------------------|----------|:--:|
| id          | Long              | 고유값      | O  |
| count       | Long              | 완료 횟수    | O  |
| contents    | VocaContentDto[ ] | 내용       | X  |
| reviewDate  | LocalDate         | 다음 복습 날짜 | O  |
| vocaTitle   | String            | 제목       | O  |
| createdDate | LocalDateTime     | 생성 날짜    | O  |
| createBy    | String            | 생성자      | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/review" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```


**응답 예제**

```shell
200 OK
[
    {
        "id": 2,
        "count": 5,
        "contents": [
            {
                "spelling": "lemon",
                "meaning": "레몬",
                "example": "lemon",
                "exampleMeaning": "레몬"
            },
            {
                "spelling": "lemon1",
                "meaning": "레몬1",
                "example": null,
                "exampleMeaning": null
            }
        ],
        "reviewDate": "2024-01-11",
        "vocaTitle": "아무것도",
        "createdDate": "2024-01-10T01:39:58",
        "createBy": "test123123"
    }
]
```

### 오늘 날짜 기준으로 리스트 조회하기

| 메서드   | URL             |
|-------|-----------------|
| `GET` | `/review/today` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름          | 타입                | 설명       | 필수 |
|-------------|-------------------|----------|:--:|
| id          | Long              | 고유값      | O  |
| count       | Long              | 완료 횟수    | O  |
| contents    | VocaContentDto[ ] | 내용       | X  |
| reviewDate  | LocalDate         | 다음 복습 날짜 | O  |
| vocaTitle   | String            | 제목       | O  |
| createdDate | LocalDateTime     | 생성 날짜    | O  |
| createBy    | String            | 생성자      | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/review/today" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
[
    {
        "id": 2,
        "count": 5,
        "contents": [
            {
                "spelling": "lemon",
                "meaning": "레몬",
                "example": "lemon",
                "exampleMeaning": "레몬"
            },
            {
                "spelling": "lemon1",
                "meaning": "레몬1",
                "example": null,
                "exampleMeaning": null
            }
        ],
        "reviewDate": "2024-01-11",
        "vocaTitle": "아무것도",
        "createdDate": "2024-01-10T01:39:58",
        "createBy": "test123123"
    }
]
```

### 생성하기

| 메서드    | URL       |
|--------|-----------|
| `POST` | `/review` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름    | 타입      | 설명         | 필수 |
|-------|---------|------------|:--:|
| id    | Long    | 문제 고유값     | O  |
| count | Integer | 예약 일자(day) | O  |

**요청 예제**

```shell
curl -X POST "http://localhost/review" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -H 'Content-Type: application/json'
```

**응답 예제**

```shell
200 OK
```

### 업데이트 하기

| 메서드     | URL       |
|---------|-----------|
| `PATCH` | `/review` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |
| Content-Type  | application/json                                      | O  |

**요청 본문**

| 이름    | 타입      | 설명         | 필수 |
|-------|---------|------------|:--:|
| id    | Long    | 고유값        | O  |
| count | Integer | 예약 일자(day) | O  |

**요청 예제**

```shell
curl -X PATCH "http://localhost/review" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" \
    -H 'Content-Type: application/json'
```

**응답 예제**

```shell
200 OK
```

### 삭제하기

| 메서드      | URL            |
|----------|----------------|
| `DELETE` | `/review/{id}` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**요청 예제**

```shell
curl -X DELETE "http://localhost/review" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" 
```

**응답 예제**

```shell
200 OK
```

### 조회하기

| 메서드   | URL            |
|-------|----------------|
| `GET` | `/review/{id}` |

**응답 본문**

| 이름          | 타입                | 설명       | 필수 |
|-------------|-------------------|----------|:--:|
| id          | Long              | 고유값      | O  |
| count       | Long              | 완료 횟수    | O  |
| contents    | VocaContentDto[ ] | 내용       | X  |
| reviewDate  | LocalDate         | 다음 복습 날짜 | O  |
| vocaTitle   | String            | 제목       | O  |
| createdDate | LocalDateTime     | 생성 날짜    | O  |
| createBy    | String            | 생성자      | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |

**요청 예제**

```shell
curl -X GET "http://localhost/review/3" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}" 
```


**응답 예제**

```shell
200 OK
{
    "id": 3,
    "count": 1,
    "contents": [
        {
            "spelling": "lemon",
            "meaning": "레몬",
            "example": "lemon",
            "exampleMeaning": "레몬"
        },
        {
            "spelling": "lemon1",
            "meaning": "레몬1",
            "example": null,
            "exampleMeaning": null
        }
    ],
    "reviewDate": "2024-01-13",
    "vocaTitle": "가",
    "createdDate": "2024-01-10T01:40:00",
    "createBy": "test123123"
}
```

### 오늘 날짜를 포함한 오늘 이전 리스트 조회하기

| 메서드   | URL                    |
|-------|------------------------|
| `GET` | `/review/priorToToday` |

**요청 헤더**

| 이름            | 설명                                                    | 필수 |
|---------------|-------------------------------------------------------|:--:|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} <br/> 인증 방식, 토큰 | O  |

**응답 본문**

| 이름          | 타입                | 설명       | 필수 |
|-------------|-------------------|----------|:--:|
| id          | Long              | 고유값      | O  |
| count       | Long              | 완료 횟수    | O  |
| contents    | VocaContentDto[ ] | 내용       | X  |
| reviewDate  | LocalDate         | 다음 복습 날짜 | O  |
| vocaTitle   | String            | 제목       | O  |
| createdDate | LocalDateTime     | 생성 날짜    | O  |
| createBy    | String            | 생성자      | O  |

**VocaContentDto**

| 이름             | 타입     | 설명    | 필수 |
|----------------|--------|-------|:--:|
| spelling       | String | 영단어   | O  |
| meaning        | String | 뜻     | O  |
| example        | String | 예시    | X  |
| exampleMeaning | String | 예시 해석 | X  |


**요청 예제**

```shell
curl -X GET "http://localhost/review/priorToToday" \
    -H "Authorization: Bearer ${ACCESS_TOKEN}"
```

**응답 예제**

```shell
200 OK
[
    {
        "id": 2,
        "count": 5,
        "contents": [
            {
                "spelling": "lemon",
                "meaning": "레몬",
                "example": "lemon",
                "exampleMeaning": "레몬"
            },
            {
                "spelling": "lemon1",
                "meaning": "레몬1",
                "example": null,
                "exampleMeaning": null
            }
        ],
        "reviewDate": "2024-01-11",
        "vocaTitle": "아무것도",
        "createdDate": "2024-01-10T01:39:58",
        "createBy": "test123123"
    }
]
```