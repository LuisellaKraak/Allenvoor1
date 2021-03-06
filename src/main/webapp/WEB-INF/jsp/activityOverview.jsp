<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht activiteiten</title>
    </head>
    <body class="webpage">
        <div id="container">
            <p>
                <input class="btn btn-primary" type="submit" value="Logout" onclick="window.location='${pageContext.request.contextPath}/logout';" />
            </p>
            <h1>Overzicht activiteiten</h1>
            <table>
                <c:forEach items="${allActivities}" var="activity">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/activity/select/<c:out value="${activity.activityId}" />"><c:out value="${activity.activityName}" /></a></td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder activity" onclick="window.location='${pageContext.request.contextPath}/activity/delete/${activity.activityId}';" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg activity toe" onclick="window.location='${pageContext.request.contextPath}/activity/new';" />
            </p>
        </div>
    <body>
</html>