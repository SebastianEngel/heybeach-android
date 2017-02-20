![heybeach](_assets/heybeach_header.png)

<br>

# Welcome
This is the time to show us your coding abilities. We have gathered a series of pictures of beautiful beaches and your
task will be to present them in a sleek and functional way.
The time limit for this challenge, starting now, is _seven days_ **We know this is a demanding task,
so please complete as much as you can**. When you are done just share your git link.

<br>

# Your challenge
The coding challenge will consist on implementing a series of incremental tasks, that will test your skills on
networking, caching, UI, design skills and API implementation. We don't expect you to finish the whole tasks,
but we would love to see how far you can go. So please take the following features listed from highest priority to lowest priority

<br>

# Rules
- Please **do not use any 3rd party libraries** other than the iOS/Android SDK.
- You have complete freedom regarding the flow, transitions and interactions of the app.
- We expect you to follow technical best practices and design a clean, user-friendly app.
<br>

# Priority List
1. The user should see a list of images fetched from the HeyBeach API. (Initially just display the first page from the api)
2. A user should be able to register/login/logout inside the app.
3. Ensure the design interface is responsive and functional on mobile, desktop and/or tablets.
4. Include the image title with each image.
5. Implement some sort of infinite scrolling (Get all the images using the paginated api).
6. Implement some sort of image caching.
7. The image grid can be flexible, respecting the images aspect ratio (non-fixed size grid, check the wireframe, eg. Pinterest style)

<br>

![Wireframe](_assets/wireframe.png)

<br>

Thank you for your time and good luck!

<br>


# Api Explanation

### Hello

Test the connection with the server. Just to welcome you!!

```
curl -X GET -H "Content-Type: application/x-www-form-urlencoded" -H "Cache-Control: no-cache" "http://139.59.158.8:3000/hello"
```

### Register

Registers an user in Hey beach platform. Pay attention to the headers where you receive your x-auth token.

```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
  "email":"user@xxxx.com",
  "password":"pass"
}
' "http://139.59.158.8:3000/user/register"
```


### Login

Logs in an user in the Hey beach platform. Pay attention to the headers where you receive your x-auth token.

```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache"  -d '{
  "email":"user@xxxx.com",
  "password":"pass"
}
' "http://139.59.158.8:3000/user/login"
```

### User/me

Returns a users basic profile. Bear in mind that this requires a valid x-auth token.  

```
curl -X GET -H "x-auth: JWToken" -H "Cache-Control: no-cache" "http://139.59.158.8:3000/user/me"
```

### User/logout

Logs out a user from the Hey beach platform.

```
curl -X DELETE -H "x-auth: JWToken" -H "Cache-Control: no-cache" "http://139.59.158.8:3000/user/logout"
```

### Beaches

Gives you a list of the beaches. The end point is paginated. The size of the page is 5 elements.

```
curl -X GET -H "Cache-Control: no-cache" "http://139.59.158.8:3000/beaches?page=3"
```

### Get an Image

To access an image.

```
http://139.59.158.8:3000/images/ee98bcce-b321-4324-9527-373b8e9c2e55.png
```
