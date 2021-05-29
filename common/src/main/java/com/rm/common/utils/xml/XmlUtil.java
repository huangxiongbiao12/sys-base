package com.rm.common.utils.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class XmlUtil {

	public static String toXml(Object object){
		XStream xStream = new XStream(new Dom4JDriver());
		xStream.autodetectAnnotations(true);
		return xStream.toXML(object);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xml, Class<T> clazz){
		XStream xStream = new XStream(new Dom4JDriver());
		xStream.autodetectAnnotations(true);
		xStream.processAnnotations(clazz);
		return (T) xStream.fromXML(xml);
	}
}
