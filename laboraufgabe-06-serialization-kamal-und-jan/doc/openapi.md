---
title: Book API v1.0
---

<!-- Generator: Widdershins v4.0.1 -->

<h1 id="book-api">Book API v1.0</h1>

Sample book API

Base URLs:

* <a href="http://localhost:8080">http://localhost:8080</a>

Email: <a href="mailto:boris.dudelsack@hs-flensburg.de">Boris Dudelsack</a> 

<h1 id="book-api-default">Default</h1>

## get-uuid

<a id="opIdget-uuid"></a>

`GET /{uuid}`

Get a book

<h3 id="get-uuid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|uuid|path|string|true|The UUID of the book|

<h3 id="get-uuid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|None|

<aside class="success">
This operation does not require authentication
</aside>

## post-uuid

<a id="opIdpost-uuid"></a>

`POST /{uuid}`

Update a book

> Body parameter

```json
{
  "author": "string",
  "title": "string"
}
```

<h3 id="post-uuid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[BookUpdateRequest](#schemabookupdaterequest)|false|none|
|uuid|path|string|true|The UUID of the book|

<h3 id="post-uuid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|None|

<aside class="success">
This operation does not require authentication
</aside>

## delete-uuid

<a id="opIddelete-uuid"></a>

`DELETE /{uuid}`

Delete a book

<h3 id="delete-uuid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|uuid|path|string|true|The UUID of the book|

<h3 id="delete-uuid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="success">
This operation does not require authentication
</aside>

## get

<a id="opIdget"></a>

`GET /`

Get all books

> Example responses

> 200 Response

```json
[
  {
    "uuid": "string",
    "author": "string",
    "title": "string"
  }
]
```

<h3 id="get-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="get-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Book](#schemabook)]|false|none|none|
|» Book|[Book](#schemabook)|false|none|none|
|»» uuid|string|true|none|none|
|»» author|string|true|none|none|
|»» title|string|true|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## post

<a id="opIdpost"></a>

`POST /`

Insert a new book

> Body parameter

```json
{
  "author": "string",
  "title": "string"
}
```

<h3 id="post-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[BookInsertRequest](#schemabookinsertrequest)|false|none|

> Example responses

> 200 Response

```json
{
  "uuid": "string",
  "author": "string",
  "title": "string"
}
```

<h3 id="post-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Book](#schemabook)|

<aside class="success">
This operation does not require authentication
</aside>

# Schemas

<h2 id="tocS_Book">Book</h2>
<!-- backwards compatibility -->
<a id="schemabook"></a>
<a id="schema_Book"></a>
<a id="tocSbook"></a>
<a id="tocsbook"></a>

```json
{
  "uuid": "string",
  "author": "string",
  "title": "string"
}

```

Book

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|uuid|string|true|none|none|
|author|string|true|none|none|
|title|string|true|none|none|

<h2 id="tocS_BookInsertRequest">BookInsertRequest</h2>
<!-- backwards compatibility -->
<a id="schemabookinsertrequest"></a>
<a id="schema_BookInsertRequest"></a>
<a id="tocSbookinsertrequest"></a>
<a id="tocsbookinsertrequest"></a>

```json
{
  "author": "string",
  "title": "string"
}

```

BookInsertRequest

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|author|string|true|none|none|
|title|string|true|none|none|

<h2 id="tocS_BookUpdateRequest">BookUpdateRequest</h2>
<!-- backwards compatibility -->
<a id="schemabookupdaterequest"></a>
<a id="schema_BookUpdateRequest"></a>
<a id="tocSbookupdaterequest"></a>
<a id="tocsbookupdaterequest"></a>

```json
{
  "author": "string",
  "title": "string"
}

```

BookUpdateRequest

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|author|string|false|none|none|
|title|string|false|none|none|

