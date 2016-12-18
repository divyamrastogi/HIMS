package com.hospital.factory;

import com.hospital.dao.HospitalDAO;
import com.hospital.utils.Properties;

/**
 * Object Factory
 *
 * @author Shivam Khare
 */
public class ObjectFactory {

	private Properties properties;
	private HospitalDAO hospitalDAO;
	private JdbcConnectionPool jdbcConnectionFactory;

	public ObjectFactory() {

	}

	public void initialize() {
		try {
			this.properties = new Properties("hospital.properties");
			this.jdbcConnectionFactory = new JdbcConnectionPool(properties);
			this.hospitalDAO = new HospitalDAO(jdbcConnectionFactory);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}
	
	public HospitalDAO getHospitalDao() {
		return hospitalDAO;
	}

	public JdbcConnectionPool getJdbcConnectionFactory() {
		return jdbcConnectionFactory;
	}
}
