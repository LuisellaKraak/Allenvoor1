package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeleteActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/team/delete/{activityId}")
    public String deleteTeam(@PathVariable("activityId") final Integer activityId) {
        activityRepository.deleteById(activityId);
        return "redirect:/activity/all";
    }
}
