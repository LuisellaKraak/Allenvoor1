package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class CalendarController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @PostMapping("/calendar/new/event")
    public ResponseEntity<Object> newEvent(@RequestBody String newEventJson) throws JsonProcessingException {
        Event event = mapper.readValue(newEventJson, Event.class);
        return new ResponseEntity<Object>(new ServiceResponse<Event>("success",
                eventRepository.save(event)), HttpStatus.OK);
    }

    @PostMapping("/calendar/change/eventdate")
    public ResponseEntity<Object> changeEventDate(@RequestBody String newDatesJson) throws JsonProcessingException {
        Event newDates = mapper.readValue(newDatesJson, Event.class);

        Event event = eventRepository.getOne(newDates.getEventId());
        event.setEventStartDate(newDates.getEventStartDate());
        event.setEventEndDate(newDates.getEventEndDate());
        eventRepository.save(event);

        ServiceResponse<Event> response = new ServiceResponse<Event>("success", newDates);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/calendar/change/event/{eventId}")
    protected ResponseEntity<Object> changeEvent(@RequestBody String changeEventJson,
                                                 @PathVariable("eventId") final Integer eventId) throws JsonProcessingException {
        System.out.println("json activityid: " + changeEventJson);
        Event event = mapper.readValue(changeEventJson, Event.class);
        System.out.println(eventId);
        System.out.println(activityRepository.getOne(eventRepository.getOne(eventId).getActivity().getActivityId()).getActivityId());
        event.setEventId(eventId);
        event.getActivity().setActivityId(activityRepository.getOne(eventRepository.getOne(eventId).getActivity().getActivityId()).getActivityId());

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(mapper.writeValueAsString(event));
        eventRepository.save(event);
        /*if (oldActivity instanceof MedicationActivity) {
            ((MedicationActivity) oldActivity).getMedication().removalActivityAddedAmount(((MedicationActivity) oldActivity).getTakenMedication());
        }*/
        return new ResponseEntity<Object>(new ServiceResponse<Event>("success", event), HttpStatus.OK);
    }

    @PostMapping("/event/delete")
    public ResponseEntity<Object> deleteEvent(@RequestBody String deleteEventJson) throws JsonProcessingException {
        Team team = (Team) httpSession.getAttribute("team");

        Event eventToDelete = mapper.readValue(deleteEventJson, Event.class);

        // Stream to remove the taken amount from the medication if the event is deleted
        activityRepository.findAll().stream()
                .filter(x -> x.getActivityId() == eventRepository.getOne(eventToDelete.getEventId()).getActivity().getActivityId())
                .filter(x -> x instanceof MedicationActivity)
                .forEach(x -> {
                    assert ((MedicationActivity) x).getMedication() != null;
                    ((MedicationActivity) x).getMedication().removalActivityAddedAmount(((MedicationActivity) x).getTakenMedication());
                });

        eventRepository.deleteById(eventToDelete.getEventId());
        return new ResponseEntity<Object>(eventToDelete, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamId}")
    public String showCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal)
            throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);
        Set<Team> teamList = memberRepository.findByMemberName(principal.getName()).get().getAllTeamsOfMemberSet();
        model.addAttribute("teamList", teamList);
        for (Medication medication : team.getMedicationList()) {
            System.out.println("MEDICATIE: " + medication.getMedicationName());
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        model.addAttribute("calendarData", mapper.writeValueAsString(team.getEventList()));
        return "calendar";
    }
}
