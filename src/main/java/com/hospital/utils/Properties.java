package com.hospital.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hospital property.
 *
 * @author Shivam Khare
 */
public class Properties {

	private static final String PAYMENT_LOG_DB_URL = "web.app.hospital.db.url";

	private static final String PAYMENT_LOG_DB_USERNAME = "web.app.hospital.db.username";

	private static final String PAYMENT_LOG_DB_PASSWORD = "web.app.hospital.db.password";

	private static final String PAYMENT_DB_DRIVER = "web.app.hospital.db.driver";

	private static final String PAYMENT_DB_CONNECTION_COUNT = "web.app.hospital.db.connection.count";

	private java.util.Properties properties;

	public Properties(String propertyLocation) throws IOException {
		properties = new java.util.Properties();
	/*	properties.put("web.app.hospital.db.url", "jdbc:mysql://localhost:3306/hospital");
		properties.put("web.app.hospital.db.username", "root");
		properties.put("web.app.hospital.db.password", "password");
		properties.put("web.app.hospital.db.driver", "com.mysql.jdbc.Driver");
		properties.put("web.app.hospital.db.connection.count", "5");
*/
		InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(propertyLocation);
		properties.load(iStream);
	}

	public int getPaymentDbConnectionCount() {
		return Integer.parseInt(properties.getProperty(PAYMENT_DB_CONNECTION_COUNT));
	}

	public String getPaymentDbUrl() {
		return properties.getProperty(PAYMENT_LOG_DB_URL);
	}

	public String getPaymentDbUsername() {
		return properties.getProperty(PAYMENT_LOG_DB_USERNAME);
	}

	public String getPaymentDbPassword() {
		return properties.getProperty(PAYMENT_LOG_DB_PASSWORD);
	}

	public String getPaymentDbDriver() {
		return properties.getProperty(PAYMENT_DB_DRIVER);
	}
}
