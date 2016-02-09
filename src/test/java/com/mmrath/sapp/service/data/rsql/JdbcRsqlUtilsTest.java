package com.mmrath.sapp.service.data.rsql;

import org.junit.Test;

/**
 * Created by murali on 5/02/2016.
 */
public class JdbcRsqlUtilsTest {
    @Test
    public void testParseSimpleEqual() {
        String input = "property==Some";
        String output = JdbcRsqlUtils.parse(input).getSql();
        System.out.println(output);
    }
}