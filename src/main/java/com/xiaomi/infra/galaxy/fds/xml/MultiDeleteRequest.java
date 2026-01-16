package com.xiaomi.infra.galaxy.fds.xml;

import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.UnmarshallerHandler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Request for multi object delete request.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Delete")
public class MultiDeleteRequest {

  @XmlElement(name = "Quiet")
  private Boolean quiet = Boolean.FALSE;

  @XmlElement(name = "Object")
  private List<DeleteObject> objects = new ArrayList<DeleteObject>();

  public boolean isQuiet() {
    return quiet;
  }

  public static MultiDeleteRequest from(InputStream inputStream)
      throws Exception {
    // Create the JAXBContext
    JAXBContext jc = JAXBContext.newInstance(MultiDeleteRequest.class);

    // Create the XMLFilter
    XMLFilter filter = new NamespaceFilter();

    // Set the parent XMLReader on the XMLFilter
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser sp = spf.newSAXParser();
    XMLReader xr = sp.getXMLReader();
    filter.setParent(xr);

    // Set UnmarshallerHandler as ContentHandler on XMLFilter
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    UnmarshallerHandler unmarshallerHandler = unmarshaller
        .getUnmarshallerHandler();
    filter.setContentHandler(unmarshallerHandler);

    // Parse the XML
    InputSource xml = new InputSource(inputStream);
    filter.parse(xml);
    return  (MultiDeleteRequest) unmarshallerHandler.getResult();
  }

  public void setQuiet(boolean quiet) {
    this.quiet = quiet;
  }

  public List<DeleteObject> getObjects() {
    return objects;
  }

  public void setObjects(
      List<DeleteObject> objects) {
    this.objects = objects;
  }

  public String[] getDeleteObjectNameArray() {
    String[] objectNameArray = new String[objects.size()];
    int index = 0;
    for (DeleteObject object : objects) {
      objectNameArray[index++] = object.getKey();
    }
    return objectNameArray;
  }

  @Override
  public String toString() {
    return "MultiDeleteRequest{" + "quiet=" + quiet + ", objects=" + objects + '}';
  }

  /**
   * JAXB entity for child element.
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlRootElement(name = "Object",
      namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
  public static class DeleteObject {

    @XmlElement(name = "Key")
    private String key;

    @XmlElement(name = "VersionId")
    private String versionId;

    public DeleteObject() {
    }

    public DeleteObject(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getVersionId() {
      return versionId;
    }

    public void setVersionId(String versionId) {
      this.versionId = versionId;
    }

    @Override
    public String toString() {
      return "DeleteObject{" + "key='" + key + '\'' + ", versionId='" + versionId + '\'' + '}';
    }
  }
}
