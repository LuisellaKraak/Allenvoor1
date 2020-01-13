<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en" xmlns:c="" xmlns:form="http://www.w3.org/1999/xhtml" xmlns:fmt="">

<head>
    <meta charset="UTF-8">
    <title>Event</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
</head>
<body>
<h1>Maak nieuwe afspraak aan:</h1>
<div class="container-fluid">
    <form:form action="/event/new" modelAttribute="event" method="post">
    <div class="row">
        <div class="col-xs-1">
            <p class="font-weight-normal">Categorie: </p>
        </div>
        <div class="col-xs-2">
            <select id="activityCategory" name="activityCategory" required onChange="window.location.redirect(this.value)">
                <option disabled selected="selected">Selecteer categorie</option>
                <option value="Huishouden" href="/activity/new">Huishouden</option>
                <option value="Medisch" href="/medicationActivity/new">Medisch</option>
                <option value="Vrije tijd" href="/activity/new">Vrije tijd</option>
            </select>
        </div>
    </div>
    </form:form>
</div>
</body>
<script>

document.getElementById('activityCategory').onchange = function() {
    window.location.href = this.children[this.selectedIndex].getAttribute('href');
}
$(function() {
  $('#dateTimePickerStartDate').datetimepicker({
     <!-- month before day -->
     format: 'MM/DD/YYYY HH:mm',
   });
   $('#dateTimePickerEndDate').datetimepicker({
     format: 'MM/DD/YYYY HH:mm',
   });
});
</script>
</html>