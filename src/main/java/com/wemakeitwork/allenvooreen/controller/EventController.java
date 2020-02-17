package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.*;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
public class EventController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    //TODO not sure if the mapping works correctly here
    protected void newEventWithMedicationActivity(Event event) {
        //tried out a stream to get the correct medication and set medication activity
        medicationRepository.findAll().stream()
                .filter(x -> {
                    assert ((MedicationActivity) event.getActivity()).getMedication() != null;
                    return x.getMedicationId() == ((MedicationActivity) event.getActivity()).getMedication().getMedicationId();
                })
                .forEach(x -> x.setTakenMedications((MedicationActivity) event.getActivity()));
        event.getTeam().getTeamId();
    }

    // Allow empty string in datefield (i.e. write them as NULL to database)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/event/new")
    protected String newEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity")
            MedicationActivity medicationActivity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam((Team) httpSession.getAttribute("team"));
            event.getActivity().setActivityName(event.getEventName());

            if (event.getActivity() instanceof MedicationActivity){
                if (medicationActivity.getMedication() == null) {
                    return "redirect:/calendar/" + team.getTeamId();
                }else {
                    event.setActivity(medicationActivity);
                    newEventWithMedicationActivity(event);
                }
            }

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @GetMapping("/event/{eventid}/subscriptionlist")
    protected String showSubscriptionListPage(@PathVariable("eventid") final Integer eventId, Model model) {
        model.addAttribute("eventId", eventId);
        return "subscriptionList";
    }

    @GetMapping("/event/{eventid}/getsubscriptionlist")
    public ResponseEntity<Object> fillSubscriptionList(@PathVariable("eventid") final Integer eventId){
        Set<EventSubscription> eventSubscriptionList = eventSubscriptionRepository.findByEventEventId(eventId);

        ServiceResponse<Set<EventSubscription>> response = new ServiceResponse<>("succes", eventSubscriptionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/event/subscribe")
    public ResponseEntity<Object> subscribeToEvent(@RequestBody String subscribeEvent) throws JsonProcessingException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventSubscription eventSubscription = mapper.readValue(subscribeEvent, EventSubscription.class);
        eventSubscription.setMember(member);

        eventSubscriptionRepository.save(eventSubscription);
        ServiceResponse<EventSubscription> response = new ServiceResponse<EventSubscription>("success", eventSubscription);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/event/unsubscribe")
    public ResponseEntity<Object> unsubscribeFromEvent(@RequestBody String unsubscribeEvent) throws JsonProcessingException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventSubscription eventUnsubscription = mapper.readValue(unsubscribeEvent, EventSubscription.class);
        eventSubscriptionRepository.delete(eventUnsubscription);
        return new ResponseEntity<Object>("success!", HttpStatus.OK);
    }
}
