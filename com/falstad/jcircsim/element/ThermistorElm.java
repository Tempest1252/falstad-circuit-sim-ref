package com.falstad.jcircsim.element;
// stub ThermistorElm based on SparkGapElm
// FIXME need to uncomment ThermistorElm line from CirSim.java
// FIXME need to add ThermistorElm.java to srclist

import com.falstad.jcircsim.view.edit.EditInfo;

import java.awt.*;
import java.util.StringTokenizer;

public class ThermistorElm extends CircuitElm
{
    public double minresistance, maxresistance;
    public double resistance;
    public Scrollbar slider;
    public Label label;

    public ThermistorElm(int xx, int yy)
    {
        super(xx, yy);
        maxresistance = 1e9;
        minresistance = 1e3;
        createSlider();
    }

    public ThermistorElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st)
    {
        super(xa, ya, xb, yb, f);
        minresistance = Double.parseDouble(st.nextToken());
        maxresistance = Double.parseDouble(st.nextToken());
        createSlider();
    }

    public boolean nonLinear()
    {
        return true;
    }

    public int getDumpType()
    {
        return 188;
    }

    public String dump()
    {
        return super.dump() + " " + minresistance + " " + maxresistance;
    }

    public Point ps3, ps4;

    public void createSlider()
    {
        sim.main.add(label = new Label("Temperature", Label.CENTER));
        int value = 50;
        sim.main.add(slider = new Scrollbar(Scrollbar.HORIZONTAL, value, 1, 0, 101));
        sim.main.validate();
    }

    public void setPoints()
    {
        super.setPoints();
        calcLeads(32);
        ps3 = new Point();
        ps4 = new Point();
    }

    public void delete()
    {
        sim.main.remove(label);
        sim.main.remove(slider);
    }

    public void draw(Graphics g)
    {
        int i;
        double v1 = volts[0];
        double v2 = volts[1];
        setBbox(point1, point2, 6);
        draw2Leads(g);
        // FIXME need to draw properly, see ResistorElm.java
        setPowerColor(g, true);
        doDots(g);
        drawPosts(g);
    }

    public void calculateCurrent()
    {
        double vd = volts[0] - volts[1];
        current = vd / resistance;
    }

    public void startIteration()
    {
        double vd = volts[0] - volts[1];
        // FIXME set resistance as appropriate, using slider.getValue()
        resistance = minresistance;
        //System.out.print(this + " res current set to " + current + "\n");
    }

    public void doStep()
    {
        sim.stampResistor(nodes[0], nodes[1], resistance);
    }

    public void stamp()
    {
        sim.stampNonLinear(nodes[0]);
        sim.stampNonLinear(nodes[1]);
    }

    public void getInfo(String arr[])
    {
        // FIXME
        arr[0] = "spark gap";
        getBasicInfo(arr);
        arr[3] = "R = " + getUnitText(resistance, sim.ohmString);
        arr[4] = "Ron = " + getUnitText(minresistance, sim.ohmString);
        arr[5] = "Roff = " + getUnitText(maxresistance, sim.ohmString);
    }

    public EditInfo getEditInfo(int n)
    {
        // ohmString doesn't work here on linux
        if (n == 0)
            return new EditInfo("Min resistance (ohms)", minresistance, 0, 0);
        if (n == 1)
            return new EditInfo("Max resistance (ohms)", maxresistance, 0, 0);
        return null;
    }

    public void setEditValue(int n, EditInfo ei)
    {
        if (ei.value > 0 && n == 0)
            minresistance = ei.value;
        if (ei.value > 0 && n == 1)
            maxresistance = ei.value;
    }
}

