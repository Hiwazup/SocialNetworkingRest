# SocialNetworkingRest
Basic Social Networking Application Using Rest

**Posting**
----
  Adds a new message to a users posts

* **URL**

  /postMessage

* **Method:**
  
  `POST` 
  
*  **URL Params**

   None

* **Data Params**
  
    `username=[string]`
    `message=[string]`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

  * **Code:** 400 BADREQUEST <br />
    **Content:** `{
    "timestamp": "2018-12-16T18:03:05.591+0000",
    "message": "Maximum character length is 140 characters",
    "details": "uri=/postMessage"
}`

**Wall**
----
  Returns json data containing users messages

* **URL**

  /messages/{username}

* **Method:**
  
  `GET` 
  
*  **URL Params**

   **Required:**
 
   `username=[string]`

* **Data Params**
  
  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[
    {
        "username": "Alice",
        "message": "This my first message"
    }
]`
 
* **Error Response:**

  * **Code:** 400 BADREQUEST <br />
    **Content:** `{
    "timestamp": "2018-12-16T17:56:05.339+0000",
    "message": "User Eve does not exist",
    "details": "uri=/messages/Eve"
}`

**Following**
----
  Follows a new user
  
* **URL**

  /user/{username}/follow

* **Method:**
  
  `PUT` 
  
*  **URL Params**

   `username=[string]`

* **Data Params**
  
    `username=[string]`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

  * **Code:** 400 BADREQUEST <br />
    **Content:** `{
    "timestamp": "2018-12-16T18:03:05.591+0000",
    "message": "User Bob does not exist",
    "details": "uri=/user/Alice/follow"
}`

**Timeline**
----
  Returns json data containing the messages of a users followers

* **URL**

  /timeline/{username}

* **Method:**
  
  `GET` 
  
*  **URL Params**

   **Required:**
 
   `username=[string]`

* **Data Params**
  
   None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[
    {
        "username": "Charlie",
        "message": "Hello World"
    },
    {
        "username": "Bob",
        "message": "This is my first post"
    },
    {
        "username": "Charlie",
        "message": "Where am I?"
    }
]`
 
* **Error Response:**

  * **Code:** 400 BADREQUEST <br />
    **Content:** `{
    "timestamp": "2018-12-16T17:56:05.339+0000",
    "message": "User Alice does not exist",
    "details": "uri= /timeline/Alice"
}`
