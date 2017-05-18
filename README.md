# My Home Page, a Blog System [![Build Status](https://travis-ci.org/lm2343635/Blog.svg?branch=master)](https://travis-ci.org/lm2343635/Blog)

This website is my homepage with a blog system. Blog articles can be added and modified by admin, while readers can read and rely.

- Platform: Web
- Programming Languages: Java, JavaScript
- Supported Languagues: English, Simplified Chinese and Japanese.
- Features
	- Add, modify and delete the category and blog.
	- Add pictures or attachment file in a blog.
	- Search a blog by keyword of title.
	- Review and comment.

Visit http://fczm.pw to view this blog system.

![Blog](https://raw.githubusercontent.com/lm2343635/Blog/master/screenshot/blog.png)

You can fork it, and modify these HTML document to create your own blog system:

- index.html(Index of this blog system)
- blogs.html(The blogs list)
- blog.html(The page of blog)

`blogs.html` and `blog.html` is dynamical pages, so you shuold be careful to placeholders such as `${key}$` or `#{key}`.

Visit `http://your_doamin/admin` to edit your blog, the admin's name and password can be modified in `WEB-INF/admin.json`.

```json
{
  "admins": [{
    "username":"admin",
    "password":"123"
  }]
}
```
	
You should create database before running this blog system, the character set should be utf-8. After that, just modify database configuration in `WEB-INF/config.properties`.
```aidl
db.url=jdbc:mysql://127.0.0.1:3306/blog?useUnicode=true&characterEncoding=utf-8
db.username=www
db.password=
```

