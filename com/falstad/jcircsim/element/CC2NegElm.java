package com.falstad.jcircsim.element;

public class CC2NegElm extends CC2Elm
{
    public static final String NAME = "CCII-";

    public CC2NegElm(int xx, int yy)
    {
        super(xx, yy, -1);
    }

    public Class getDumpClass()
    {
        return CC2Elm.class;
    }
}
