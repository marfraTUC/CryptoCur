package de.markuskfrank.cryptocur.main.technical;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.markuskfrank.cryptocur.main.model.User;

public class XMLFileSaver {

	public static void persistToXML(User user) throws JAXBException, FileNotFoundException {
		JAXBContext contextObj = JAXBContext.newInstance(User.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshallerObj.marshal(user, new FileOutputStream("./" + user.getUsername() + ".xml"));
	}

	public static User loadFromXML(String username) throws JAXBException {

		File file = new File("./" + username + ".xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		User user = (User) jaxbUnmarshaller.unmarshal(file);
		
		return user;

	}
}
