package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "medicationAmount", "medicationComment", "team", "getTakenMedications", "hibernateLazyInitializer", "medicationId" })
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medicationId;

    @JsonProperty("medicationname")
    private String medicationName;

    private Integer medicationAmount;

    private String medicationComment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "medication")
    private List<MedicationActivity> takenMedications;

    public Integer getMedicationId() {
        return medicationId;
    }

    public List<MedicationActivity> getTakenMedications() {
        return takenMedications;
    }

    public void setTakenMedications(MedicationActivity medicationActivity) {
        this.takenMedications.add(medicationActivity);
        this.medicationAmount -= medicationActivity.getTakenMedication();
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Integer getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(Integer medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    public String getMedicationComment() {
        return medicationComment;
    }

    public void setMedicationComment(String medicationComment) {
        this.medicationComment = medicationComment;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void removalActivityAddedAmount(Integer integer){
        this.medicationAmount += integer;
    }
}
