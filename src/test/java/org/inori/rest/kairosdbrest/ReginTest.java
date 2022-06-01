package org.inori.rest.kairosdbrest;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 10:48 6月
 * @since 1.8
 */
public class ReginTest {

    @Test
    void testTegin() {
        Pattern compile = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})");
        Matcher matcher = compile.matcher("2021-03-12 00:00:00");
        boolean matches = matcher.matches();
        System.out.println("123 -> " + matches);
    }

    @Test
    void testTegin2() {
        Pattern compile = Pattern.compile("(\\d)* (\\w)*");
        boolean matches = compile.matcher("1 Hour").matches();
        boolean matches1 = compile.matcher("1111 Minute").matches();
        boolean matches2 = compile.matcher("123Second").matches();
        boolean matches3 = compile.matcher("abc 12312").matches();
        boolean abc12312 = compile.matcher("abc12312").matches();
        System.out.println("而技术");
    }

}
