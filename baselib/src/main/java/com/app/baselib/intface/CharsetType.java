package com.app.baselib.intface;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({CharsetType.GBK, CharsetType.UTF_8, CharsetType.ASCII, CharsetType.CESU_8, CharsetType.UTF_16, CharsetType.UTF_16BE, CharsetType.UTF_16LE, CharsetType.UTF_16LE_BOM,
        CharsetType.UTF_32, CharsetType.UTF_32LE, CharsetType.UTF_32BE, CharsetType.UTF_32LE_BOM, CharsetType.UTF_32BE_BOM, CharsetType.ISO_8859_1,
        CharsetType.ISO_8859_2, CharsetType.ISO_8859_4, CharsetType.ISO_8859_5, CharsetType.ISO_8859_7, CharsetType.ISO_8859_9, CharsetType.ISO_8859_13,
        CharsetType.ISO_8859_15, CharsetType.KOI8_R, CharsetType.KOI8_U, CharsetType.MS1250, CharsetType.MS1251, CharsetType.MS1252, CharsetType.MS1253, CharsetType.MS1254, CharsetType.MS1257,
        CharsetType.IBM437, CharsetType.IBM737, CharsetType.IBM775, CharsetType.IBM850, CharsetType.IBM852, CharsetType.IBM855, CharsetType.IBM857, CharsetType.IBM858, CharsetType.IBM862,
        CharsetType.IBM866, CharsetType.IBM874})
@Retention(RetentionPolicy.SOURCE)
public @interface CharsetType {

    String GBK = "GBK";
    String UTF_8 = "UTF-8";
    String ASCII = "ASCII";
    String CESU_8 = "CESU-8";
    String UTF_16 = "UTF-16";
    String UTF_16BE = "UTF-16BE";
    String UTF_16LE = "UTF-16LE";
    String UTF_16LE_BOM = "UTF-16LE-BOM";
    String UTF_32 = "UTF-32";
    String UTF_32LE = "UTF-32LE";
    String UTF_32BE = "UTF-32BE";
    String UTF_32LE_BOM = "UTF-32LE-BOM";
    String UTF_32BE_BOM = "UTF-32BE-BOM";
    String ISO_8859_1 = "ISO-8859-1";
    String ISO_8859_2 = "ISO-8859-2";
    String ISO_8859_4 = "ISO-8859-4";
    String ISO_8859_5 = "ISO-8859-5";
    String ISO_8859_7 = "ISO-8859-7";
    String ISO_8859_9 = "ISO-8859-9";
    String ISO_8859_13 = "ISO-8859-13";
    String ISO_8859_15 = "ISO-8859-15";
    String KOI8_R = "KOI8-R";
    String KOI8_U = "KOI8_U";
    String MS1250 = "MS1250";
    String MS1251 = "MS1251";
    String MS1252 = "MS1252";
    String MS1253 = "MS1253";
    String MS1254 = "MS1254";
    String MS1257 = "MS1257";
    String IBM437 = "IBM437";
    String IBM737 = "IBM737";
    String IBM775 = "IBM775";
    String IBM850 = "IBM850";
    String IBM852 = "IBM852";
    String IBM855 = "IBM855";
    String IBM857 = "IBM857";
    String IBM858 = "IBM858";
    String IBM862 = "IBM862";
    String IBM866 = "IBM866";
    String IBM874 = "IBM874";
}
