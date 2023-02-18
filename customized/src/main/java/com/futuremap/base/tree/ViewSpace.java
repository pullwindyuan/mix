package com.futuremap.base.tree;

/**
 * 家族人员
 */

public class ViewSpace {
    private int left = 0;//左
    private int top = 0;//上
    private int right = 0;//右
    private int bottom = 0;//下
    private int width = 0;//宽
    private int height = 0;//高
    /*防碰撞校准值，该值在绘制的时候需要加上父级成员的align再传入给下级绘制作为补偿校准。
    由于默认我们认为相互成员是紧邻防止，所以这个补偿都是正值补偿,用于加宽间距防止碰撞。
    由于补偿值是链式传递的，所以再传递的时候除了向下传递还要向后传递。
    所谓向下传递：就是绘制的时候需要通过取得parent的align和自己的alignl累加；
    所谓向后传递：就是要取得同代左边的成员的align和自己的align累加。
    */
    private int align;
    private int preGap;

    public int getPreGap() {
        return preGap;
    }

    public void setPreGap(int preGap) {
        this.preGap = preGap;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        right = this.left + this.getWidth() - 1;
        return right;
    }

    public int getRightDirect() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }
}
