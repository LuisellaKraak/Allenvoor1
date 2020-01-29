// These functions load the start, end & done date calendars (datetimepickers) in the modal (popup).
$('#datetimepickerStart').datetimepicker({
    format: 'DD-MM-YYYY HH:mm'
});
$('#datetimepickerEnd').datetimepicker({
    format: 'DD-MM-YYYY HH:mm'
});
$('#datetimepickerDone').datetimepicker({
    format: 'DD-MM-YYYY HH:mm'
});
$("#datetimepickerStart").on("change.datetimepicker", function (e) {
    $('#datetimepickerEnd').datetimepicker('minDate', e.date);
});
$("#datetimepickerEnd").on("change.datetimepicker", function (e) {
    $('#datetimepickerStart').datetimepicker('maxDate', e.date);
});
$('#datetimepickerDone').datetimepicker();

// This function hides all modal options
function hideAllModalInputFields() {
    $("#datetimepickerDone, #eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
}

//this shows the medication amount when an excisting event is chosen
function showMedicationAmount(event, element){
    if ($('.modal').find('#activityCategory').val() == "Medisch")
      getMedication()
      /*$('.modal').find('#takenMedication').val(event.activity.takenmedication);*/
}

// This function fills the modal with event info if it exist
function showModalInputFields() {
    if ($('.modal').find('#activityCategory').val() == "Medisch") {
        hideAllModalInputFields();
        $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv, #modal-footer").show();
    } else {
        hideAllModalInputFields();
        $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv, #modal-footer").show();
    }

    $("#eventDoneDiv").css("display", "");
}

function toggleModalNewOrExistingEvent(newOrExisting) {
    if (newOrExisting === "existing") {
        document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
        document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";
        $("#delete-event").show();
    } else {
        document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
        document.getElementById("save-change-event").innerHTML = "Maak afspraak";
        $("#delete-event").hide();
    }
}