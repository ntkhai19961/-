package com.example.win7.apphoctiengnhat.App.Fragment.Communication.ChucNangKiemTra;

/**
 * Created by WIN7 on 20-Jun-17.
 */

public class ClassTracNghiem {
    public Integer idKiemTra;
    public String CauHoi;
    public String CauA;
    public String CauB;
    public String CauC;
    public String CauD;
    public String CauTraLoi;

    public ClassTracNghiem(Integer idKiemTra, String cauHoi, String cauA, String cauB, String cauC, String cauD, String cauTraLoi) {
        this.idKiemTra = idKiemTra;
        CauHoi = cauHoi;
        CauA = cauA;
        CauB = cauB;
        CauC = cauC;
        CauD = cauD;
        CauTraLoi = cauTraLoi;
    }

    public ClassTracNghiem(String cauHoi) {
        CauHoi = cauHoi;
    }
}
