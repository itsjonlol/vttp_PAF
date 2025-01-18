package vttp_day24ws.vttp_day24ws.service;

import org.springframework.stereotype.Service;

@Service
public class AppNameService {
    private String appName;
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getAppName() {
        return appName;
    }
}
