package org.seckill.util.mainUtil;

/**
 * Created by Ken Pan on 2017/5/3.
 */
public class mainUtil {


    public static void main(String[] args) {
        String name = "[0055163]-[2017-05-03*05:31:09]-[2017-05-03*06:01:08].mkv";
        String[] names = name.split("\\[|\\]");
        for(
                int i = 0;
                i<names.length;i++)

        {
            System.out.println(names[i]+"------"+i);
        }
    }

}
