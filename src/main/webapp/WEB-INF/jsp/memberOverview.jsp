<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <title>Overzicht gebruiker</title>
    </head>
    <body class="webpage">
            <div id="container">
                <p>
                    <form:form action="/logout" method="post">
                       <input type="submit" value="Logout" />
                   </form:form>
                </p>
                <h1>Overzicht gebruiker</h1>
                <form:form action="/member/current" modelAttribute="currentmember">
                <form:input path="memberId" type="hidden" />
                <table>
                      <tr>

                         <td><a href="/team/select/<c:out value="${members.memberId}" />"><c:out value="${members.membername}" /></a></td>
                           <td><a href="/member/delete">Verwijder je profiel</a></td>
                           <td><a href="/member/change">Verander je inlog naam</a></td>
                        </tr>
                </table>
               </form:form>
             </div>
        </body>
</html>