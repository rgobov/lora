package com.example.security.spring_security.model;

import java.util.List;

public class ServiceCategory {
    private String name;
    private List<Service> services;

    public ServiceCategory(String name, List<Service> services) {
        this.name = name;
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
