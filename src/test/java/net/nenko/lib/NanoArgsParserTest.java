package net.nenko.lib;

import org.junit.Test;

import static org.junit.Assert.*;

public class NanoArgsParserTest {

    private static NanoArgsParser.Option optPassword = new NanoArgsParser.Option("p", null, "<password>", "user password");
    private static NanoArgsParser.Option optOutput = new NanoArgsParser.Option("o", "./output.data", "<output-file>", "file for output");
    private static NanoArgsParser.Option optVerbose = new NanoArgsParser.Option("v", NanoArgsParser.FLAG, null, "verbose logging");
    private static NanoArgsParser.Option optHelp = new NanoArgsParser.Option("h", NanoArgsParser.FLAG, null, "displays command line format and list of options");
    private static NanoArgsParser.Option[] options = new NanoArgsParser.Option[] { optPassword, optOutput, optVerbose, optHelp};

    private static String[] args = {"cmd", "-p", "abracadabra", "input-data.txt", "-v" };
    private static String expectedSynopsis = "\t-p <password> - user password (option has no default)\n"
            + "\t-o <output-file> - file for output (default is './output.data')\n"
            + "\t-v - verbose logging\n"
            + "\t-h - displays command line format and list of options\n";

    @Test
    public void parse() {
        NanoArgsParser parser = new NanoArgsParser(options);
        assertEquals(expectedSynopsis, parser.argsSynopsis());

        String[] argsPositional = parser.parse(args);
        assertEquals(2, argsPositional.length);
        assertEquals("cmd", argsPositional[0]);
        assertEquals("input-data.txt", argsPositional[1]);
        assertEquals("abracadabra", optPassword.value);
        assertEquals("./output.data", optOutput.value);
        assertTrue(optVerbose.isOn());
        assertFalse(optHelp.isOn());
    }

}