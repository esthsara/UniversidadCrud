package com.universidad.validation;

import java.time.LocalDateTime;

public class ApiError {
    private int status;
    private String mensaje;
    private Object detalles;
    private LocalDateTime timestamp;
    
    // Constructor
    public ApiError(int status, String mensaje, Object detalles, LocalDateTime timestamp) {
        this.status = status;
        this.mensaje = mensaje;
        this.detalles = detalles;
        this.timestamp = timestamp;
    }
    
    // Getters y setters
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public Object getDetalles() {
        return detalles;
    }
    
    public void setDetalles(Object detalles) {
        this.detalles = detalles;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}