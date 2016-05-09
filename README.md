# My Home Page, a Blog System
This website is my homepage with a blog system. Blog articles can be added and modified by admin, while readers can read and rely.
Visit http://www.fczm.pw to view this blog system.

You can fork it, and modify these HTML document to create your own blog system:

	index.html --Index of this blog system
	work.html --It is loaded in index.html, shows the works of you
	blogs.html --The blogs list.
	blog.html --The page of blog

`blogs.html` and `blog.html` is dynamical pages, so you shuold be careful of placeholders such as `${key}$` or `#{key}`.

Visit `admin.html` to edit your blog, the admin name and password can be modified in `WEB-INF/applicationContext.html`

	<bean id="adminManager" class="org.fczm.blog.service.impl.AdminManagerImpl">
		<property name="username" value="admin"></property>
		<property name="password" value="123"></property>
	</bean> 