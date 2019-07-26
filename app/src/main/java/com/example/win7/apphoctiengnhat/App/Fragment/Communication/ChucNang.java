package com.example.win7.apphoctiengnhat.App.Fragment.Communication;

/**
 * Created by WIN7 on 05-Jun-17.
 */

public class ChucNang {

    private String TenChucNang;
    private Integer AnhChucNang;
    private String TenTableSQLite;

    public ChucNang(String tenChucNang, Integer anhChucNang) {
        TenChucNang = tenChucNang;
        AnhChucNang = anhChucNang;
    }

    public ChucNang(String TenChucNang, Integer anhChucNang , String TenTableSQLite) {
        this.TenChucNang = TenChucNang;
        this.AnhChucNang = anhChucNang;
        this.TenTableSQLite = TenTableSQLite;
    }

    public String getTenChucNang() {
        return TenChucNang;
    }

    public Integer getAnhChucNang() {
        return AnhChucNang;
    }

    public String getTenTableSQLite() {
        return TenTableSQLite;
    }
}
