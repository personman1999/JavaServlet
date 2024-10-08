<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modern News Page</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
    <header>
        <div class="logo">
            <h1>News Today</h1>
        </div>
        <nav>
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">World</a></li>
                <li><a href="#">Technology</a></li>
                <li><a href="#">Sports</a></li>
                <li><a href="#">Entertainment</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <section class="news-grid">
        	<c:forEach items="${ListNews}" var="news" >
        	     <article class="news-item">
                <img src="${news.image_url }" alt="News Image 1">
                <div class="news-content">
                    <h2>${news.title }</h2>
                    <p>${news.content }</p>
                    <a href="#" class="read-more">Read more</a>
                </div>
            </article>
        	</c:forEach>
       
   
       
        </section>
    </main>

    <footer>
        <p>© 2024 News Today. All rights reserved.</p>
    </footer>


</body>
</html>