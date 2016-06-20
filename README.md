# My Home Page, a Blog System
This website is my homepage with a blog system. Blog articles can be added and modified by admin, while readers can read and rely.
Visit http://www.fczm.pw to view this blog system.

You can fork it, and modify these HTML document to create your own blog system:

	index.html --Index of this blog system
	blogs.html --The blogs list.
	blog.html --The page of blog

`blogs.html` and `blog.html` is dynamical pages, so you shuold be careful to placeholders such as `${key}$` or `#{key}`.

Visit `http://your_doamin/admin` to edit your blog, the admin name and password can be modified in `WEB-INF/applicationContext.html`. You need not modify the files in folder `admin` because they are files of management system.

	<bean id="adminManager" class="org.fczm.blog.service.impl.AdminManagerImpl">
		<property name="username" value="[Your admin username]"></property>
		<property name="password" value="[Your admin password]"></property>
	</bean> 
	
You should create database before running this blog system, the character set should be utf-8. After that, just modify database configuration in `WEB-INF/applicationContext.html`.

	p:jdbcUrl="jdbc:mysql://127.0.0.1:3306/[Your database name]?characterEncoding=utf-8"
	p:user="[Your database username]" 
	p:password="[Your database password]"
