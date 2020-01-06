package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class CalendarController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/calendar")
    public String calendar(Model model) throws JsonProcessingException {
        List<Event> eventList = eventRepository.findAll();
        String calendarData = "";
        for (Event event : eventList) {
            calendarData += "" + new ObjectMapper().writeValueAsString(event) + ",";
        }
        Event event = new Event();
        event.setActivity(new Activity());
        model.addAttribute("event", new Event());
        model.addAttribute("calendarData", calendarData);
        return "calendar";
    }
}