<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html xmlns:mytags="" xmlns:security="" xmlns:c="http://www.w3.org/1999/XSL/Transform">
<head>
    <title>Kalender</title>
    <meta charset='utf-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <!-- Add icon library -->
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <!-- Setup variables which can be read from external javascript file -->
    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <security:authorize access="isAuthenticated()">
        <c:set var="principalUsername">
            <security:authentication property="principal.username" />
        </c:set>
        <link id="principalUsername" data-principalUsername="${principalUsername}"/>
    </security:authorize>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/events.js"></script>

    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
</head>
<body class= "webpage">
<mytags:navbar/>
<div class= "masthead">
    <div class="w-25 p-3">

        <ul class="list-group" >
            <li class="list-group-item list-group-item-dark">Ingeschreven gebruikers:</li>
            <div id="subscriptionList">
                <!-- this list is filled by getEventSubscriptions() in the events.js -->
            </div>
        </ul>
    </div>
    <div class="w-25 p-3" id="subscribe">
        <!-- placeholder for subscribe-button; is filled by getEventSubscriptions() in the events.js -->
    </div>
</div>

<div class="modal fade" id="errorModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header" id="errorModalHeader">
                <!-- Error header message is filled by the error clause in getEventSubscriptions() in the events.js -->
            </div>
            <div class="modal-body" id="errorModalBody">
                <!-- Error body message is filled by the error clause in getEventSubscriptions() in the events.js -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Sluiten</button>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    $(document).ready(function(){
        getEventSubscriptions(${eventId});
    })
</script>
</html>