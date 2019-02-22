package com.huojumu.model;

import java.util.List;

/**
 * 规格
 * */
public class Specification {

    private List<MatsBean> mats;
    private List<TastesBean> tastes;
    private List<MakesBean> makes;

    public List<MatsBean> getMats() {
        return mats;
    }

    public void setMats(List<MatsBean> mats) {
        this.mats = mats;
    }

    public List<TastesBean> getTastes() {
        return tastes;
    }

    public void setTastes(List<TastesBean> tastes) {
        this.tastes = tastes;
    }

    public List<MakesBean> getMakes() {
        return makes;
    }

    public void setMakes(List<MakesBean> makes) {
        this.makes = makes;
    }
}
