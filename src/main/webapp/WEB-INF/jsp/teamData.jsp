<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>

      <!-- Add icon library -->
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
     <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
     <link href="../css/style.css" rel="stylesheet" type="text/css"/>
     </head>

    <title>Wijzig de gegevens van een groep</title>
</head>
<body class="webpage">
<!-- Navigation -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow sticky-top">
              <div class="container">
                <a class="navbar-brand"</a>
                <img class="mb-4" src='${pageContext.request.contextPath}/images/LogoAllenVoorEen.png' alt="" width="230" height="178"> </a>
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

    <div class="container">
        <br>
        <h1>Wijzig gegevens groep</h1>
        <form:form action="${pageContext.request.contextPath}/team/change" modelAttribute="team">
        <form:input path="teamId" type="hidden" />
        <form:input path="allMembersInThisTeamSet" type="hidden" />
            <table>
                <tr>
                    <td>Groepsnaam:</td>
                    <td>
                        <form:input size="30" path="teamName" value="${team.teamName}" /></form>
                        <input class="btn btn-primary" type="submit" value="Wijzig" />
                    </td>
                </tr>
                <tr>
                     <td>Groepslid:</td>
                     <td>
                        <c:forEach items="${team.allMembersInThisTeamSet}" var="member">
                             <c:out value="${member.memberName}" /><br />
                         </c:forEach>
                     </td>
                 </tr>
             </table>
         </form:form>
         <form:form action="${pageContext.request.contextPath}/team/addMember" modelAttribute="teamMemberDTO">
             <form:input path="teamId" type="hidden" />
                <table>
                <tr>
                    <td>Nieuw groepslid:</td>
                    <td>
                       <form:input path="teamMemberName" /></form>
                    </td>
                </tr>
                <tr>
                    <td>
                         <input class="btn btn-primary" type="submit" value="Wijzig" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>