package com.example.win7.apphoctiengnhat.App.Fragment.Communication.CacChucNang.CacChucNangKhac;

/**
 * Created by ntkhai1996 on 19-Nov-17.
 */

public class ClassChucNangCustomAdapter {

    private Integer id;
    private String TiengNhat;
    private String Romanji;
    private String YNghia;
    private Integer YeuThich;

    public ClassChucNangCustomAdapter(Integer id, String tiengNhat, String romanji, String YNgia , Integer YeuThich) {
        this.id = id;
        TiengNhat = tiengNhat;
        Romanji = romanji;
        this.YNghia = YNgia;
        this.YeuThich = YeuThich;
    }

    public Integer getId() {
        return id;
    }

    public String getTiengNhat() {
        return TiengNhat;
    }

    public String getRomanji() {
        return Romanji;
    }

    public String getYNghia() {
        return YNghia;
    }

    public Integer getYeuThich() {
        return YeuThich;
    }

    public void setYeuThich(Integer yeuThich) {
        YeuThich = yeuThich;
    }
}
