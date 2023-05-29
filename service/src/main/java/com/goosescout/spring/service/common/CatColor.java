package com.goosescout.spring.service.common;

import com.goosescout.spring.dao.common.Color;

public enum CatColor {
    BLACK, WHITE, BROWN, GREY, ORANGE, MULTICOLORED;

    public static CatColor fromColor(Color color) {
        return switch (color) {
            case BLACK -> CatColor.BLACK;
            case WHITE -> CatColor.WHITE;
            case BROWN -> CatColor.BROWN;
            case GREY -> CatColor.GREY;
            case ORANGE -> CatColor.ORANGE;
            case MULTICOLORED -> CatColor.MULTICOLORED;
        };
    }

    public static Color toColor(CatColor color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case BROWN -> Color.BROWN;
            case GREY -> Color.GREY;
            case ORANGE -> Color.ORANGE;
            case MULTICOLORED -> Color.MULTICOLORED;
        };
    }
}
