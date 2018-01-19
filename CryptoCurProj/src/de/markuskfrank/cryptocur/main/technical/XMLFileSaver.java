package de.markuskfrank.cryptocur.main.technical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.User;

public class XMLFileSaver {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
	private static final String DIR = "./users/";

	public static void persistUserToXML(User user, String password) throws Exception {
		File dir = new File(DIR);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		JAXBContext contextObj = JAXBContext.newInstance(User.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshallerObj.marshal(user, new FileOutputStream(getFileString(user.getUsername(), true)));

		File inputFile = new File(getFileString(user.getUsername(), true));
		File outputFile = new File(getFileString(user.getUsername(), false));

		doCryptFile(Cipher.ENCRYPT_MODE, inputFile, outputFile, password);
		inputFile.delete();
	}

	public static User loadUserFromXML(String username, String password) throws Exception {
		File inputFile = new File(getFileString(username, false));
		File outputFile = new File(getFileString(username, true));

		doCryptFile(Cipher.DECRYPT_MODE, inputFile, outputFile, password);

		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		User user = (User) jaxbUnmarshaller.unmarshal(outputFile);

		outputFile.delete();
		return user;

	}

	private static String getFileString(String username, boolean tmp) {
		StringBuilder sb = new StringBuilder(DIR);
		sb.append(username);
		sb.append(".xml");
		if (tmp) {
			sb.append(".tmp");
		}
		return sb.toString();
	}

	public static boolean doesUserExist(String username) {
		File file = new File(getFileString(username, false));
		return file.exists();
	}

	private static void doCryptFile(int cipherMode, File inputFile, File outputFile, String password) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			Key secretKey = new SecretKeySpec(md.digest(password.getBytes()), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException ex) {
			throw new Exception("Error encrypting/decrypting file", ex);
		}
	}

	public static boolean detectLegacyProblem(String username) {
		File file = new File("./" + username + ".xml");
		return file.exists();
	}
	
	private static User loadOldUser(String username) throws JAXBException{

		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		User user = (User) jaxbUnmarshaller.unmarshal(new File("./" + username + ".xml"));

		return user;
	}

	public static User updateLegacyUser(String username, String password, Currencys currencys) throws Exception {
		User user = loadOldUser(username);
		user.setPassword(password);
		user.setBaseCurrency(currencys);
		persistUserToXML(user, password);
		deleteOldUserFile(username);
		
		return user;
	}

	private static void deleteOldUserFile(String username) {
		File file = new File("./" + username + ".xml");
		file.delete();
	}
}
