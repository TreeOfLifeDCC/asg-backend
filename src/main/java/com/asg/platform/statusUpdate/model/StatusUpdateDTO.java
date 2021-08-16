package com.asg.platform.statusUpdate.model;

public class StatusUpdateDTO {

    String status;
    String gca_identifer;
    String organism;
    String weblink;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGca_identifer() {
        return gca_identifer;
    }

    public void setGca_identifer(String gca_identifer) {
        this.gca_identifer = gca_identifer;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }
}
