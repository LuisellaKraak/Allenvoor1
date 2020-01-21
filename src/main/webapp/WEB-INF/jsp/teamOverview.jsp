<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:c="">
    <head>
        <meta charset="utf-8">
        <title>Overzicht groepen</title>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>

          <!-- Add icon library -->
        <link href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>


        <!-- Navigation -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
                <a class="navbar-brand"</a><br><br><br>
                <a href="${pageContext.request.contextPath}/home">
                <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="83" height="64"> </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                      <span class="navbar-toggler-icon"></span>
                    </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                  <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                       <a class="nav-link" href='${pageContext.request.contextPath}/home'><i class="fa fa-home"></i> Home</a>
                            <span class="sr-only">(current)</span>
                          </a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href='${pageContext.request.contextPath}/logout'><i class="fa fa-sign-out"></i> Logout</a>
                    </li>
                  </ul>
                </div>
              </div>
            </nav>
             <div id="container">
        <title>Overzicht groepen</title>
      <header class= "masthead">
   <br>
    <div class="mt-3 col-12">
            <h1 class="font-weight-light">Overzicht groepen</h1>

            <table>
                <tr><th><h5 class="font-weight-light">Groep</h5></th><th><h5 class="font-weight-light">Deelnemer</h5></th><th></th></tr>
                <c:forEach items="${teamList}" var="team">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/team/select/<c:out value="${team.teamId}" />"><c:out value="${team.teamName}" /></a></td>
                        <td>
                        <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
                            <a href="${pageContext.request.contextPath}/team/select/<c:out value="${member.memberName}" />"><c:out value="${member.memberName}" /></a><br />
                        </c:forEach>
                        </td>
                        <td><input class="btn btn-primary" type="submit" value="Verwijder groep" onclick="window.location='${pageContext.request.contextPath}/team/delete/${team.teamId}'" /></td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="btn btn-primary" type="submit" value="Voeg groep toe" onclick="window.location='${pageContext.request.contextPath}/team/new';" />
            </p>
        </div>
    <body>
</html>