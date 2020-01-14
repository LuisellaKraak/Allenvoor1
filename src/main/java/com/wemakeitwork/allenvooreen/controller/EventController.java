package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class EventController {
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

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    @GetMapping("/event/delete/{eventId}/{activityId}")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public String deleteEvent(@PathVariable("eventId") final Integer eventId,
                              @PathVariable("activityId") final Integer activityId) {
        Team team = (Team) httpSession.getAttribute("team");

        eventRepository.deleteById(eventId);
        try {
            activityRepository.deleteById(activityId);
        } catch (EmptyResultDataAccessException ex) {
            return "redirect:/calendar/" + team.getTeamId();
        }

        // The responsestatus that is preceding this method is necessary to prevent a 500 error.
        return "redirect:/calendar/" + team.getTeamId();
    }

    @PostMapping("/event/new")
    protected String saveOrUpdateEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity")
            MedicationActivity medicationActivity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        }

        else {
            System.out.println(medicationActivity);
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);

            if (event.getActivity().getActivityCategory().equals("Medisch")){
                event.setActivity(medicationActivity);
            }

            event.getActivity().setActivityName(event.getEventName());
            if(event.getActivity() instanceof MedicationActivity){
                System.out.println("Take your chill pills");
                Optional<Medication> medication = medicationRepository.findById(medicationActivity.getMedication().getMedicationId());
                medication.ifPresent(value -> value.setTakenMedications(medicationActivity));
            }else {
                System.out.println("dit is een normale activiteit");
            }
            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @PostMapping("/event/change/{activityId}/{teamId}")
    protected String changeEvent(@ModelAttribute("event") Event event,
                                 @ModelAttribute("teamId") Integer teamId,
                                 @PathVariable("activityId") final Integer activityId, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            event.getActivity().setActivityName(event.getEventName());
            event.getActivity().setActivityId(activityId);

            //finding/setting the team corresponding with the event
            Team team = teamRepository.getOne(teamId);
            event.setTeam(team);

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }
}
