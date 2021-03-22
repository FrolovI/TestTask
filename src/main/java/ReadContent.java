import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;

public class ReadContent {
    private static final int CONNECTION_TIMEOUT = 5000;
    final URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
    final HttpURLConnection connect = (HttpURLConnection) url.openConnection();

    public ReadContent() throws IOException {
    }

    public String connectMethod() throws ProtocolException {
        connect.setRequestMethod("GET");
        connect.setRequestProperty("Content-type", "application/json");
        connect.setConnectTimeout(CONNECTION_TIMEOUT);
        connect.setReadTimeout(CONNECTION_TIMEOUT);
        //connect.setRequestProperty("Accept-Charset", "UTF-8");

        try (BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream(), "windows-1251"));) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void main(String... args) throws IOException {
        var cont = new ReadContent();
        String result = cont.connectMethod();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Double ienRub = null;
        var valList = xmlMapper.readValue(result, ValuteList.class);
        for (ValuteElement res : valList.valutes) {
            if (res.name.equals("Японских иен")) {
                ienRub = Double.parseDouble(res.value.replaceAll(",", ".")) / res.nominal;
            }
        }
        DecimalFormat dF = new DecimalFormat( "#.##" );
        System.out.println("Одна японская йена равна " + dF.format(ienRub) + " руб.");
    }
}
