<%--
  Created by IntelliJ IDEA.
  User: beniamin.czaplicki
  Date: 2016-05-13
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.qbit.quiz.web.LinkEnum" %>
<c:url value="<%=LinkEnum.LoadQuizPage.getValue()%>" var="loadQuizPageURL"/>
<c:url value="<%=LinkEnum.MockQuizLoader.getValue()%>" var="mockQuizLoaderURL"/>
<c:url value="<%=LinkEnum.CleanDB.getValue()%>" var="cleanDBURL"/>
<html>
<head>
    <title>info</title>
</head>
<body>
<h1>Environment: </h1>
Server Version: <%= application.getServerInfo() %><br>
Servlet Version: <%= application.getMajorVersion()  %>.<%= application.getMinorVersion() %> <br>
JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %><br>
JSF Version: <%= Package.getPackage("com.sun.faces").getImplementationVersion() %><br>
<br>
link to test servlets:
<br/>

<a href="${loadQuizPageURL}">go to QuizPage  loader servlet</a><br/>
<a href="${mockQuizLoaderURL}">go to mock QuizLoader loader servlet</a><br/>
<a href="${cleanDBURL}">go to CleanDB loader servlet</a><br/>
</body>
</html>
