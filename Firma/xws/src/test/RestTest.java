package test;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RestTest {

	@BeforeClass
	public static void clear() {
        // CLEAR THE SCREEN
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        // CLEAR THE SCREEN ESCAPE SEQUENCE. May now work on Windows
	}

    @Before
    public void setUp() {
    }

    @Test
    public void testHttpClientMethods() {
        HttpClient client = new HttpClient();
        HostConfiguration conf = client.getHostConfiguration();
        System.out.println(conf.getHost());
    };

}
