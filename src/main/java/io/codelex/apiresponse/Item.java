package io.codelex.apiresponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "pubDate")
    private String pubDate;

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}
