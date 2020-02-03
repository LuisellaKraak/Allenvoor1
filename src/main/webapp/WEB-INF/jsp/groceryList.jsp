<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<html>
<head>
    <meta charset='utf-8' />
    <title>Boodschappenlijst</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.4.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel='stylesheet'>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/stylingGroceryList.css" rel="stylesheet" type="text/css"/>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link id="teamId" data-teamId="${team.teamId}"/>
    <link id="csrfToken" data-csrfToken="${_csrf.token}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<div id="GroceryList" class="header">
    <h2 style="margin:5px">Boodschappenlijst!</h2><br>
    <input type="text" id="newGroceryItem" placeholder="Nieuwe Boodschap...">
    <span onclick="newElement()" id="addButton" class="addBtn">Voeg toe!</span>
</div>


<ul id="allGroceries">
    <div id="allMedications">
    </div>
    <div id="groceryItem">
    </div>
</ul>

<div class="footer">
    <span onclick="removeAllCheckedFromList()" id="checkDBtn" class="checkDBtn">verwijder gekochte items</span>&nbsp;
    <span onclick="removeAllFromList()" id="removeBtn" class="removeBtn">verwijder alle items</span>
    <br>
</div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/groceryList.js"></script>


</body>
</html>
