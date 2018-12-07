package com.app.baselib.mask;
@Deprecated
class MaskInfo {
    int color;
    int width;
    int height;

    public MaskInfo(int color,int width,int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaskInfo)) {
            return false;
        }

        MaskInfo maskInfo = (MaskInfo)o;

        if (color != maskInfo.color) {
            return false;
        }
        if (width != maskInfo.width) {
            return false;
        }
        return height == maskInfo.height;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + color;
        return result;
    }
}
