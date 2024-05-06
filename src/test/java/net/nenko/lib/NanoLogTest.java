package net.nenko.lib;

import org.junit.Test;

import static org.junit.Assert.*;

public class NanoLogTest {

    private NanoLog log1 = new NanoLog(NanoLog.LogLevel.DEBUG);
    private NanoLog log2 = new NanoLog(NanoLog.LogLevel.DEBUG, NanoLog.LogStyle.SIMPLE, null);
    private NanoLog log3 = new NanoLog(NanoLog.LogLevel.DEBUG, NanoLog.LogStyle.COMPLEX, null);
    @Test
    public void test_HappyPath() {
        log1.info("log.info(string)");
        log1.info("log.info() with arguments {} {} {} {}", "arg1", 123, true, "arg4");
        assertTrue(true);
    }

    @Test
    public void test_SimpleStyle() {
        log2.info("log.info(string) with simple style");
        log2.info("log.info() with arguments {} {} {} {}", "arg1", 123, true, "simple-style-arg4");
        assertTrue(true);
    }

    @Test
    public void test_ComplexStyle() {
        log3.info("log.info(string) with complex style");
        log3.info("log.info() with arguments {} {} {} {}", "arg1", 123, true, "complex-style-arg4");
        assertTrue(true);
    }
}