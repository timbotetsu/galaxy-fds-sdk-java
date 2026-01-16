package com.xiaomi.infra.galaxy.fds.xml;

import com.xiaomi.infra.galaxy.fds.result.UploadPartResult;
import com.xiaomi.infra.galaxy.fds.result.UploadPartResultList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.UnmarshallerHandler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

/**
 * Request for Complete Multipart Upload request.
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "CompleteMultipartUpload")
public class CompleteMultipartUploadRequest {

  @XmlElement(name = "Part") private List<Part> partList = new ArrayList<Part>();

  public List<Part> getPartList() {
    return partList;
  }

  public void setPartList(List<Part> partList) {
    this.partList = partList;
  }

  public static CompleteMultipartUploadRequest from(String content) throws Exception {
    // Create the JAXBContext
    JAXBContext jc = JAXBContext.newInstance(CompleteMultipartUploadRequest.class);

    // Create the XMLFilter
    XMLFilter filter = new NamespaceFilter();

    // Set the parent XMLReader on the XMLFilter
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser sp = spf.newSAXParser();
    XMLReader xr = sp.getXMLReader();
    filter.setParent(xr);

    // Set UnmarshallerHandler as ContentHandler on XMLFilter
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    UnmarshallerHandler unmarshallerHandler = unmarshaller.getUnmarshallerHandler();
    filter.setContentHandler(unmarshallerHandler);

    // Parse the XML
    InputSource xml = new InputSource(new StringReader(content));
    filter.parse(xml);
    return (CompleteMultipartUploadRequest) unmarshallerHandler.getResult();
  }

  /**
   * JAXB entity for child element.
   */
  @XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "Part") public static class Part {

    @XmlElement(name = "PartNumber") private int partNumber;

    @XmlElement(name = "ETag") private String eTag;

    public int getPartNumber() {
      return partNumber;
    }

    public void setPartNumber(int partNumber) {
      this.partNumber = partNumber;
    }

    public String geteTag() {
      return eTag;
    }

    public void seteTag(String eTag) {
      this.eTag = eTag;
    }
  }

  public UploadPartResultList getUploadPartResult() {
    UploadPartResultList uploadPartResultList = new UploadPartResultList();
    for (Part part : partList) {
      UploadPartResult uploadPartResult = new UploadPartResult();
      uploadPartResult.setPartNumber(part.getPartNumber());
      uploadPartResult.setEtag(StringUtils.strip(part.geteTag(), "\""));
      uploadPartResultList.addUploadPartResult(uploadPartResult);
    }
    return uploadPartResultList;
  }

}
