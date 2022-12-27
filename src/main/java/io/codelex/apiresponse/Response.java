package io.codelex.apiresponse;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
    @XmlElement(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

}