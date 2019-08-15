package jgstestclasses;

import de.unifreiburg.cs.proglang.jgs.support.Constraints;
import de.unifreiburg.cs.proglang.jgs.support.Effects;
import de.unifreiburg.cs.proglang.jgs.support.IOUtils;

public class MethodCallAllConstants {

    @Constraints({"@0 <= @ret", "@1 <= @ret", "@ret <= ?"})
    @Effects({"LOW"})
    public static int add(int x, int y){

        int a = x + y;
        return a;
    }


    @Constraints("LOW <= @0")
    @Effects({"LOW"})
    public static void main(String[] args) {
        int q = 5;
        int b = 9;
        int s = sub(10, q, b);

        int x = 7;
        int z = 9;
        int r = add(x, z);

        IOUtils.printSecret(r);
        IOUtils.printSecret(s);
    }

    @Constraints({"@0 <= @ret", "@1 <= @ret", "@2 <= @ret", "@ret <= ?"})
    @Effects({"LOW"})
    public static int sub(int a, int b, int c){

        int d  = add(b, c);
        int r = d - a;

        return r;
    }

}