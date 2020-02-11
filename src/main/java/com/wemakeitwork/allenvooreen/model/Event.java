package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@JsonPropertyOrder(value = {"id", "title", "description", "start", "end", "donedate"}, alphabetic = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
})
@Table(name = "events")
public class Event {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer eventId;

    @JsonProperty("title")
    @NotBlank(message = "veld mag niet blank zijn")
    private String eventName;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("start")
    private java.util.Date eventStartDate;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("doneByMember")
    private Member doneByMember;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("end")
    private java.util.Date eventEndDate;

    @JsonProperty("comment")
    private String eventComment;

    @JsonProperty("interval")
    private String eventInterval;

    @JsonProperty("maxNumber")
    private Integer eventMaxNumber;

    @JsonProperty("donedate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.util.Date eventDoneDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "event")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<EventSubscribers> eventSubscriptions;

    public Event() {
    }
    public Event(Integer eventId, @NotBlank(message = "veld mag niet blank zijn") String eventName, Date eventStartDate,
                 Date eventEndDate, String eventComment, String eventInterval, Integer eventMaxNumber,
                 Date eventDoneDate, Activity activity,
                 Team team) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventComment = eventComment;
        this.eventInterval = eventInterval;
        this.eventMaxNumber = eventMaxNumber;
        this.eventDoneDate = eventDoneDate;
        this.activity = activity;
        this.team = team;
    }

    public Integer getEventMaxNumber() {
        return eventMaxNumber;
    }

    public void setEventMaxNumber(Integer eventMaxNumber) {
        this.eventMaxNumber = eventMaxNumber;
    }

    public String getEventInterval() {
        return eventInterval;
    }

    public void setEventInterval(String eventInterval) {
        this.eventInterval = eventInterval;
    }

    public Date getEventDoneDate() {
        return eventDoneDate;
    }

    public void setEventDoneDate(Date eventDoneDate) {
        this.eventDoneDate = eventDoneDate;
    }

    @JsonGetter
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @JsonGetter
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @JsonGetter
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @JsonGetter
    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @JsonGetter
    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    //joda-time
    /* @JsonGetter
    public DateTime getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(DateTime eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @JsonGetter
    public DateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(DateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    } */

    @JsonGetter
    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    public Team getTeam() {
        return team;
    }

    @Nullable
    public Member getDoneByMember() {
        return doneByMember;
    }

    public void setDoneByMember(@Nullable Member doneByMember) {
        this.doneByMember = doneByMember;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
