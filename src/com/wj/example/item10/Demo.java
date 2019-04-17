package com.wj.example.item10;

/*
    Obey the general contract when overriding equals
 */

import java.util.ArrayList;
import java.util.List;

class CaseInsensitiveString{
    private final String s;

    CaseInsensitiveString(String s){
        this.s = s;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(((CaseInsensitiveString)o).s);
        if (o instanceof String)    // Violates symmetric, don't do this
            return s.equalsIgnoreCase((String) o);
        return false;
    }
}


class Point1 {
    private int x;
    private int y;

    Point1(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Point1))
            return false;
        Point1 p = (Point1) o;
        return p.x == x && p.y == y;
    }
}

class Point2{

    private final int x;
    private final int y;

    Point2(int x, int y){
        this.x = x;
        this.y = y;
    }

    // Violates Liskov substitution principle!
    // Subclass will not replace this class when using equals method
    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != getClass())
            return false;
        Point2 p = (Point2) o;
        return p.x == x && p.y == y;
    }
}

class ColorPoint1 extends Point1 {

    private final int z;

    ColorPoint1(int x, int y, int z){
        super(x, y);
        this.z = z;
    }

    // Violates symmetry!
    @Override
    public boolean equals(Object o){
        if (!(o instanceof ColorPoint1))
            return false;
        return super.equals(o) && ((ColorPoint1) o).z == z;
    }
}

class ColorPoint2 extends Point1 {

    private final int z;

    ColorPoint2(int x, int y, int z){
        super(x, y);
        this.z = z;
    }

    // Violates transitivity!
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Point1))
            return false;

        if (!(o instanceof ColorPoint2))
            return o.equals(this);

        return super.equals(o) && ((ColorPoint2) o).z == z;
    }
}

class ColorPoint3 extends Point1 {

    private final int z;

    ColorPoint3(int x, int y, int z){
        super(x, y);
        this.z = z;
    }

    // Violates transitivity!
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Point1))
            return false;

        if (!(o instanceof ColorPoint3))
            return o.equals(this);  // cause StackOverflowError

        return super.equals(o) && ((ColorPoint3) o).z == z;
    }
}




public class Demo {

    public static void main(String[] args) {
        CaseInsensitiveString cs = new CaseInsensitiveString("123");
        String s = "123";
        System.out.println(cs.equals(s));
        System.out.println(s.equals(cs));   // no symmetic

        System.out.println("-------------------------------------");

        List<CaseInsensitiveString> l = new ArrayList<>();
        l.add(cs);
        System.out.println(l.contains(cs));
        System.out.println(l.contains(s));

        System.out.println("-------------------------------------");

        Point1 p = new Point1(1, 2);
        ColorPoint1 p11 = new ColorPoint1(1, 2, 3);
        System.out.println(p.equals(p11));
        System.out.println(p11.equals(p));

        System.out.println("-------------------------------------");
        ColorPoint2 p21 = new ColorPoint2(1, 2, 3);
        ColorPoint2 p22 = new ColorPoint2(1, 2 ,4);
        System.out.println(p.equals(p21));
        System.out.println(p21.equals(p));
        System.out.println(p.equals(p22));
        System.out.println(p21.equals(p22));

        System.out.println("-------------------------------------");
        ColorPoint3 p31 = new ColorPoint3(1, 2, 3);
        // System.out.println(p21.equals(p31));    // Throw StackOverflowError
    }

}
