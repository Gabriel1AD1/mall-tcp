
package com.labotec.pe.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.labotec.pe.app.constants.util.KEYS.KEY_ALARM;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Position {
    @JsonProperty("imei")
    private String imei;

    @JsonProperty("type")
    private String type;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("server_time")
    private Date serverTime = new Date();

    @JsonProperty("device_time")
    private Date deviceTime;

    @JsonProperty("fix_time")
    private Date fixTime;

    @JsonProperty("outdated")
    private boolean outdated;

    @JsonProperty("valid")
    private boolean valid;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("altitude")
    private double altitude; // value in meters

    @JsonProperty("speed")
    private double speed; // value in knots

    @JsonProperty("course")
    private double course;

    @JsonProperty("address")
    private String address;

    @JsonProperty("accuracy")
    private double accuracy;

    @JsonProperty("attributes")
    private Map<String, Object> attributes = new LinkedHashMap<>();

    @JsonProperty("geofence_ids")
    private List<Long> geofenceIds;



    public Position(String protocol) {
        this.protocol = protocol;
    }
    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            System.out.println("LAtitud enviada "  + latitude);

            throw new IllegalArgumentException("Latitude out of range");
        }
        this.latitude = latitude;

    }

    public void setTime(Date time) {
        setDeviceTime(time);
        setFixTime(time);
    }
    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            System.out.println(" LONGITUD ALTO "+ longitude);
            throw new IllegalArgumentException("Longitude out of range");
        }
        this.longitude = longitude;
    }
    public void setGeofenceIds(List<? extends Number> geofenceIds) {
        if (geofenceIds != null) {
            this.geofenceIds = geofenceIds.stream().map(Number::longValue).collect(Collectors.toList());
        } else {
            this.geofenceIds = null;
        }
    }

    public void addAlarm(String alarm) {
        if (alarm != null) {
            if (hasAttribute(KEY_ALARM)) {
                set(KEY_ALARM, getAttributes().get(KEY_ALARM) + "," + alarm);
            } else {
                set(KEY_ALARM, alarm);
            }
        }
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }


    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = Objects.requireNonNullElseGet(attributes, LinkedHashMap::new);
    }

    public void set(String key, Boolean value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Byte value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Short value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Integer value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Long value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Float value) {
        if (value != null) {
            attributes.put(key, value.doubleValue());
        }
    }

    public void set(String key, Double value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, String value) {
        if (value != null && !value.isEmpty()) {
            attributes.put(key, value);
        }
    }

    public void add(Map.Entry<String, Object> entry) {
        if (entry != null && entry.getValue() != null) {
            attributes.put(entry.getKey(), entry.getValue());
        }
    }

    public String getString(String key, String defaultValue) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            return value != null ? value.toString() : null;
        } else {
            return defaultValue;
        }
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public double getDouble(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.doubleValue();
            } else {
                return Double.parseDouble(value.toString());
            }
        } else {
            return 0.0;
        }
    }

    public boolean getBoolean(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Boolean booleanValue) {
                return booleanValue;
            } else {
                return Boolean.parseBoolean(value.toString());
            }
        } else {
            return false;
        }
    }

    public int getInteger(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.intValue();
            } else {
                return Integer.parseInt(value.toString());
            }
        } else {
            return 0;
        }
    }

    public long getLong(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.longValue();
            } else {
                return Long.parseLong(value.toString());
            }
        } else {
            return 0;
        }
    }

    public void set(String key, BigDecimal value) {
        if (value != null) {
            attributes.put(key, value.doubleValue());
        }
    }

    public void setIccid(String iccid) {
        attributes.put("iccid",iccid);
    }
}
