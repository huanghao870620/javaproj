package com.ld.model;

import java.math.BigDecimal;

public class Room {
    private BigDecimal id;

    private BigDecimal roomId;

    private String roomName;

    private BigDecimal type;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getRoomId() {
        return roomId;
    }

    public void setRoomId(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }
}