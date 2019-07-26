package com.example.win7.apphoctiengnhat.LessonDetails.Fragment.LuyenTap.model;

import java.io.Serializable;

/**
 * Created by ntkhai1996 on 10-Apr-18.
 */

public class Practice implements Serializable {

    private String hanashimashou;
    private String id;
    private String iimashou;

    public Practice() {
    }

    public Practice(String hanashimashou, String id, String iimashou) {
        this.hanashimashou = hanashimashou;
        this.id = id;
        this.iimashou = iimashou;
    }

    public String getHanashimashou() {
        return hanashimashou;
    }

    public void setHanashimashou(String hanashimashou) {
        this.hanashimashou = hanashimashou;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIimashou() {
        return iimashou;
    }

    public void setIimashou(String iimashou) {
        this.iimashou = iimashou;
    }
}
