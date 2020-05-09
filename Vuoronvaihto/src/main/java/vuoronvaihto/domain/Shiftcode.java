/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuoronvaihto.domain;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * This class represents the shiftcode and its properties.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shiftcode extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String code;

    private LocalTime startTime;
    private long durationInMinutes;

    /**
     * Konstruktori.
     * 
     * @param code            eg. "AA0600"
     * @param startTime       eg. "06:00"
     * @param durationInHours eg. 7.5
     */
    public Shiftcode(String code, String startTime, double durationInHours) {
        this.code = code;
        this.startTime = LocalTime.parse(startTime);
        this.durationInMinutes = (long) (durationInHours * 60);
    }

    public long getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getCode() {
        return code;
    }

    /**
     * Starting time of shift as LocalTime.
     * 
     * @return alkamisaika LocalTimena
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the calculated finish time of the shift. Used when examining the time
     * between end of previous shift and start of the next one.
     * 
     * @param date The date when the shift begins.
     * @return The time when the shift ends as LocalDateTime.
     */
    public LocalDateTime getFinishTime(LocalDate date) {
        LocalDateTime finishTime = LocalDateTime.of(date, startTime);
        return finishTime.plusMinutes(durationInMinutes);
    }

    @Override
    public String toString() {
        if (code.equals("Vapaa")) {
            return "Vapaa";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String s = code + " (" + startTime.format(formatter) + "-"
                + startTime.plusMinutes(durationInMinutes).format(formatter) + ")";
        return s;
    }

    /**
     * equals-method for the class.
     * 
     * @param v the shift to compare to.
     * @return true, if same.
     */
    public boolean equals(Shiftcode v) {
        return (this.code.equals(v.getCode()) && this.startTime == v.getStartTime()
                && (this.durationInMinutes == v.getDurationInMinutes()));
    }

}
