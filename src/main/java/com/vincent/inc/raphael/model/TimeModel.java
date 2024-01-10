package com.vincent.inc.raphael.model;

import java.io.Serializable;
import java.time.ZoneId;

import com.vincent.inc.viesspringutils.util.DateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "time")
public class TimeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @Column
    private int year;
    
    @Column
    private int month;
    
    @Column
    private int day;
    
    @Column
    private int hour;
    
    @Column
    private int minute;
    
    @Column
    private int second;

    @Column
    @Builder.Default
    private ZoneId currentZoneId = DateTime.DEFAULT_ZONE_ID;

    @Column
    @Builder.Default
    private boolean bypassMax = false;

    public DateTime toDateTime() {
        return DateTime.builder()
                       .year(year)
                       .month(month)
                       .day(day)
                       .hour(hour)
                       .minute(minute)
                       .second(second)
                       .currentZoneId(currentZoneId)
                       .bypassMax(bypassMax)
                       .build();
    }

    public static TimeModel fromDateTime(DateTime dateTime) {
        return TimeModel.builder()
                        .year(dateTime.getYear())
                        .month(dateTime.getMonth())
                        .day(dateTime.getDay())
                        .hour(dateTime.getHour())
                        .minute(dateTime.getMinute())
                        .second(dateTime.getSecond())
                        .currentZoneId(dateTime.getCurrentZoneId())
                        .bypassMax(dateTime.isBypassMax())
                        .build();
    }
}
