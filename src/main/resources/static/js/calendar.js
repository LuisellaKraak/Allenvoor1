$(document).ready(function() {
        hideAll();
        function ajaxPost() {



        				// Manier om een object te maken en door te geven
//        				var event = {
//         					name : $("#eventName").val(),
//         					title : $("#eventComment").val(),
//         					start : $("#eventStartDate").val()
//        				}

                        var token = document.getElementsByName("csrfToken").value;

        				// DO POST
        				$.ajax({
        				    url : "saveEventFromPost",
        					method : "POST",
        					cache : false,
                            async : true,
                            contentType : "application/json; charset=UTF-8",
        					data : JSON.stringify({
                                               "title" : "myname",
                                               "name" : "sup"
                                           }),
        					dataType : 'json',
        					success : function(result) {
        						if (result.status == "success") {
        							$("#postResultDiv").html(
        									"" + result.data.bookName
        											+ "Post Successfully! <br>"
        											+ "---> Congrats !!" + "</p>");
        						} else {
        							$("#postResultDiv").html("<strong>Error</strong>");
        						}
        						console.log(result);
        					},
        					error : function(e) {
        						alert("Error!")
        						console.log(event)
        						console.log("ERROR: ", e);
        					}
        				});

        			}


        <!-- this shows/hides the eventDone input field when the checkbox is toggled -->
        $("#eventDone").change(function () {
            if(document.getElementById("eventDone").checked == true) {
                    $("#datetimepickerDone").show()
            } else {
                    document.getElementById("eventDoneDate").removeAttribute("required");
                    $("#datetimepickerDone").hide()
            }
        });

        <!-- below makes sure that the unwanted fields in the modal are hidden and calls the selection upon change -->
        $("#selectie").change(function () {
            hideAll();
            activitySelection();
        });

        <!-- below cleans the modal upon closing -->
        $('#modal-form').on("hide.bs.modal", function() {
            $('#modal-form').trigger("reset");
            hideAll();
        });

        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timeZone: 'Europe/Amsterdam',
            timeFormat: 'H(:mm)',
            locale: 'nl',

            <!-- The buttons and title that are displayed at the top of the calendar -->
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,list'
            },

            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            <!-- This function is executed when an empty date/time is clicked -->
            select: function(start, end) {
                ajaxPost();
            },

            <!-- This function is executed when an already planned event is clicked -->
            eventClick: function(event, element) {
                <!-- pass eventId to a simple <a href> link: -->
                <!-- $('#deleteEvent').attr('href',"../event/delete/" + event.id); -->

                <!-- redefines the onclick action of the delete-event button, so that it will point the browser -->
                <!-- to the /event/delete/{eventId}/{activityId} mapping -->
                $('#delete-event').attr('onclick',"window.location='../event/delete/" + event.id + "/" + event.activity.id + "'");

                $("#eventId").val(event.id);
                $('#modal-form').attr('action',"../event/change/" + event.activity.id);
                $('#save-change-event').attr('action',"../event/change");
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#selectie').val(event.activity.category);
                $('.modal').find('#eventName').val(event.title);

                <!-- below shows the modal based on the event.activity.category -->
                fillingTheModal();
                showMedicationAmount(event, element);

                $('.modal').find('#eventStartDate').val(event.start.format('DD-MM-YYYY H:mm'));
                $('.modal').find('#eventEndDate').val(event.end.format('DD-MM-YYYY H:mm'));

                <!-- redefines the modal (popup) buttons with the appropriate button text -->
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";

                <!-- shows the delete button on the (still hidden) modal -->
                $("#delete-event").show();

                <!-- lastly, the modal (popup) is shown, which by now has been properly configured -->
                $('.modal').modal('show');
            },

            eventDragStop: function(info) {
            },

            <!-- This function is executed when an event was resized, not yet implemented -->
            eventResize: function(event, delta, revertFunc) {
                alert(event.title + " end is now " + event.end.format());

                revertFunc();
            },

            <!-- Remember last view on page reload. -->
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),

            editable: true,

            <!-- calendarData is a JSON string with all calendar events -->
            events: function(start, end, timezone, callback) {
                $.ajax({
                    type:'GET',
                    url: 'json/1/' + start + '/' + end + '/',
                    dataType: 'json',
                    error: function (xhr, type, exception) { alert("Error: " + exception); },
                    success: function (response) {
                        var events = [];
                        for (i in response) {
                            events.push({
                                id: response[i].id,
                                title: response[i].title,
                                start: response[i].start,
                                end: response[i].end,
                            });
                        }
                        callback(events);
                    }
                });
             },

            eventLimit: true // allow "more" link when too many events
        });

        <!-- These functions load the start, end & done date calendars (datetimepickers) in the modal (popup). -->
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

    });

<!-- below function shows the correct modal form based on the activity selection -->
    function activitySelection() {
        if ($("#selectie").val() === "Medisch") {
            $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        } else {
            $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
        }
        $("#datetimepickerDone").hide();
        $("#eventDoneDiv").css("display", "");
    }

<!-- below function hides all modal options -->
    function hideAll() {
        $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
    }

    function showMedicationAmount(event, element){
        if ($('.modal').find('#selectie').val() == "Medisch")
          $('.modal').find('#takenMedication').val(event.activity.takenmedication);
    }

<!-- below function fills the modal with event info if it exist -->
    function fillingTheModal() {
        if ($('.modal').find('#selectie').val() == "Medisch") {
            $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        } else {
            $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
        }
        $("#datetimepickerDone").hide();
        $("#eventDoneDiv").css("display", "");
    }

    function getCookie(c_name) {
        if(document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=");
            if(c_start != -1) {
                c_start = c_start + c_name.length + 1;
                c_end = document.cookie.indexOf(";", c_start);
                if(c_end == -1) c_end = document.cookie.length;
                return unescape(document.cookie.substring(c_start,c_end));
            }
        }
        return "";
    }

