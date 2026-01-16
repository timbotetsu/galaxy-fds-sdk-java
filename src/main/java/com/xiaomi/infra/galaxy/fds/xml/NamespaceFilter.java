package com.xiaomi.infra.galaxy.fds.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class NamespaceFilter extends XMLFilterImpl {
  private static final String NAMESPACE = "http://s3.amazonaws.com/doc/2006-03-01/";

  @Override
  public void endElement(String uri, String localName, String qName)
      throws SAXException {
    super.endElement(NAMESPACE, localName, qName);
  }

  @Override
  public void startElement(String uri, String localName, String qName,
      Attributes atts) throws SAXException {
    super.startElement(NAMESPACE, localName, qName, atts);
  }
}
