import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class ValuteElement {

    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    String id;
    @JacksonXmlProperty(localName = "NumCode")
    String numCode;
    @JacksonXmlProperty(localName = "CharCode")
    String charCode;
    @JacksonXmlProperty(localName = "Nominal")
    int nominal;
    @JacksonXmlProperty(localName = "Name")
    String name;
    @JacksonXmlProperty(localName = "Value")
    String value;

}
