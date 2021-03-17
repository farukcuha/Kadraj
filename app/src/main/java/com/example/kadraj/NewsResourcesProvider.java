package com.example.kadraj;

import com.example.kadraj.Models.NewsCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class NewsResourcesProvider {

    public List<NewsCategoryModel> getData(String newsName){
        List<NewsCategoryModel> list = new ArrayList<>();
        switch (newsName){
            case "Sözcü":
                list.add(new NewsCategoryModel(
                        "https://www.sozcu.com.tr",
                        R.drawable.sozcu,
                        "Sözcü"
                ));
                break;
            case "Sabah":
                list.add(new NewsCategoryModel(
                        "https://www.sabah.com.tr",
                        R.drawable.sabahlogo,
                        "Sabah"
                ));
                break;
            case "Habertürk":
                list.add(new NewsCategoryModel(
                        "https://www.haberturk.com",
                        R.drawable.haberturk,
                        "Habertürk"
                ));
                break;
            case "Hürriyet":
                list.add(new NewsCategoryModel(
                        "https://www.hurriyet.com.tr",
                        R.drawable.hurriyet,
                        "Hürriyet"
                ));
                break;
            case "Karar":
                list.add(new NewsCategoryModel(
                        "https://www.karar.com",
                        R.drawable.karar,
                        "Karar"
                ));
                break;
            case "Milliyet":
                list.add(new NewsCategoryModel(
                        "https://www.milliyet.com.tr",
                        R.drawable.milliyet,
                        "Milliyet"
                ));
                break;
            case "Yeni Şafak":
                list.add(new NewsCategoryModel(
                        "https://www.yenisafak.com",
                        R.drawable.yenisafak,
                        "Yeni Şafak"
                ));
                break;
            case "Türkiye":
                list.add(new NewsCategoryModel(
                        "https://www.turkiyegazetesi.com.tr",
                        R.drawable.turkiye,
                        "Türkiye"
                ));
                break;
            case "Takvim":
                list.add(new NewsCategoryModel(
                        "https://www.takvim.com.tr",
                        R.drawable.takvim,
                        "Takvim"
                ));
                break;
            case "Yeni Akit":
                list.add(new NewsCategoryModel(
                        "https://www.yeniakit.com.tr",
                        R.drawable.akit,
                        "Yeni Akit"
                ));
                break;
            case "Bein Sports":
                list.add(new NewsCategoryModel(
                        "https://tr.beinsports.com",
                        R.drawable.bein,
                        "Bein Sports"
                ));
                break;
            case "Fotomaç":
                list.add(new NewsCategoryModel(
                        "https://www.fotomac.com.tr",
                        R.drawable.fotomac,
                        "Fotomaç"
                ));
                break;
            case "Fanatik":
                list.add(new NewsCategoryModel(
                        "https://www.fanatik.com.tr",
                        R.drawable.fanatik,
                        "Fanatik"
                ));
                break;
            case "SporX":
                list.add(new NewsCategoryModel(
                        "https://www.sporx.com",
                        R.drawable.sporx,
                        "SporX"
                ));
                break;
            case "A Spor":
                list.add(new NewsCategoryModel(
                        "https://www.aspor.com.tr",
                        R.drawable.aspor,
                        "A Spor"
                ));
                break;
            case "Fotospor":
                list.add(new NewsCategoryModel(
                        "https://www.fotospor.com",
                        R.drawable.fotospor,
                        "Fotospor"
                ));
                break;
            case "NTV Spor":
                list.add(new NewsCategoryModel(
                        "https://www.ntvspor.net",
                        R.drawable.ntvspor,
                        "NTV Spor"
                ));
                break;
            case "TRT Spor":
                list.add(new NewsCategoryModel(
                        "https://www.trtspor.com.tr",
                        R.drawable.trtspor,
                        "TRT Spor"
                ));
                break;
            case "Ajans Spor":
                list.add(new NewsCategoryModel(
                        "https://ajansspor.com",
                        R.drawable.ajansspor,
                        "Ajans Spor"
                ));
                break;
            case "Webtekno":
                list.add(new NewsCategoryModel(
                        "https://www.webtekno.com",
                        R.drawable.webtekno,
                        "Webtekno"
                ));
                break;
            case "ShiftDelete":
                list.add(new NewsCategoryModel(
                        "https://shiftdelete.net",
                        R.drawable.shitdelete,
                        "ShiftDelete"
                ));
                break;
            case "Log":
                list.add(new NewsCategoryModel(
                        "https://www.log.com.tr",
                        R.drawable.log,
                        "Log"
                ));
                break;
            case "Chip":
                list.add(new NewsCategoryModel(
                        "https://www.chip.com.tr",
                        R.drawable.chip,
                        "Chip"
                ));
                break;
            case "Webrazzi":
                list.add(new NewsCategoryModel(
                        "https://webrazzi.com",
                        R.drawable.webrazzi,
                        "Webrazzi"
                ));
                break;
            case "TeknolojiOku":
                list.add(new NewsCategoryModel(
                        "https://www.teknolojioku.com",
                        R.drawable.teknolojioku,
                        "TeknolojiOku"
                ));
                break;
        }
        return list;
    }
}
