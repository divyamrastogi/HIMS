package com.hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.hospital.dto.Authorisation;
import com.hospital.dto.Bank;
import com.hospital.dto.City;
import com.hospital.dto.Department;
import com.hospital.dto.Doctor;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.RegisterResponse;
import com.hospital.factory.JdbcConnectionPool;
import com.hospital.dto.Source;

/**
 * Database Layer - HospitalDAO Class has all database query operations.
 *
 * @author Shivam Khare
 */
public class HospitalDAO {

	private static final String ONE = "1";
	private static final String TWO = "2";
	private static final String THREE = "3";
	private static final String ZERO = "0";

	private JdbcConnectionPool jdbcConnectionPool;

	public HospitalDAO(JdbcConnectionPool jdbcConnectionPool) {
		this.jdbcConnectionPool = jdbcConnectionPool;
	}

	public String getEncryptedPassword(String username) {
		String stmt = "select PWORD from USERS where USERNM=?";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		String password = null;
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);
			int index = 1;
			prepstmt.setString(index++, username);

			ResultSet result = prepstmt.executeQuery();
			if (result.next()) {
				password = result.getString(1);
			}

			if (password == null) {
				System.out.println("HospitalDAO.getEncryptedPassword() - password is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getEncryptedPassword() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}

		return password;
	}

	public String getUserId(String username) {
		String stmt = "select USERID from USERS where USERNM=?";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		String userId = null;
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);
			int index = 1;
			prepstmt.setString(index++, username);

			ResultSet result = prepstmt.executeQuery();
			if (result.next()) {
				userId = result.getString(1);
			}

			if (userId == null) {
				System.out.println("HospitalDAO.getEncryptedPassword() - userId is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getEncryptedPassword() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}

		return userId;
	}

	public List<Department> getDepartments() {

		String stmt = "select SUBCOMPANYID, SUBCOMPANYNM from SUBCOMPANIES";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<Department> departments = new ArrayList<Department>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				Department d = new Department();
				d.setId(result.getString("SUBCOMPANYID"));
				d.setName(result.getString("SUBCOMPANYNM"));
				departments.add(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getDepartments() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return departments;
	}

	public void populatedb() throws Exception {
		String data = "DOC00001,G.L.Annapurna M.B.B.S;DGO;D.N.B;PDHSC(U.S),SCOM0002,,,,,,,0,150,150,,,12/14/2013 00:00:00,personal,08/18/2012 00:00:00,U0000000,IU000015,1,1,15,IP1,0,1,150|DOC00002,\"P.Vijaya Kumar M.B.B.S;MD(G.M.),P.D.C.C.(CARDIO)\",SCOM0007,,,,,,,1,150,150,,,09/20/2012 00:00:00,Frontoffice-2,08/19/2012 00:00:00,U0000000,U0000000,1,1,15,SERVER,0,,150|DOC00003,G.UMA MAHESWARA RAO DNB MD(NEURO),SCOM0016,guntur,,,,,,1,300,300,,,10/30/2013 00:00:00,Frontoffice-2,08/27/2012 00:00:00,IU000001,IU000015,1,1,15,IP1,0,,300|DOC00004,DR. P.L. RAO  M.S. F.P.S.M,SCOM0030,NELLORE,NELLORE,,AP,,,1,150,150,,,11/15/2012 00:00:00,Frontoffice2,11/15/2012 00:00:00,IU000004,IU000004,1,1,15,Frontoffice2,0,,0|DOC00005,\"Dr.C BHASKAR M.B.B.S., D.T.C.D\",SCOM0031,,,,,,,1,300,300,,,11/27/2013 00:00:00,personal,11/19/2012 00:00:00,IU000004,U0000000,1,1,15,IP1,0,,0|DOC00006,\"Dr.S.GURU PRASAD MD.DM.,\",SCOM0043,,,,,,,1,300,300,,,09/01/2013 00:00:00,personal,12/08/2012 00:00:00,IU000004,IU000010,1,0,15,xyz-8a346907d9b,0,,300|DOC00007,Dr. PCS  REDDY,SCOM0015,,,,,,,1,150,150,,,12/12/2012 00:00:00,personal,12/12/2012 00:00:00,IU000004,IU000004,1,1,7,personal,0,,0|DOC00008,\"DR.GOWRI NADH REDDY M.B.B.S., MD.,CHEST\",SCOM0036,,,,,,,1,300,300,,,03/16/2013 00:00:00,personal,01/10/2013 00:00:00,IU000004,IU000004,1,1,7,personal,0,,300|DOC00009,\"DR.LAKSHMI RAMESH MS.,M.C.H\",SCOM0001,,,,,,,1,350,350,,,02/11/2014 00:00:00,Frontoffice2,02/07/2013 00:00:00,IU000010,U0000000,1,1,15,joshi-PC,0,,350|DOC00010,\"Dr.SK.MEERAVALI M.B.B.S.,M.S.(ORTHO)\",SCOM0038,,,,,,,1,150,150,,,03/16/2013 00:00:00,Frontoffice2,02/16/2013 00:00:00,IU000010,IU000004,1,1,15,personal,0,,0|DOC00011,DR.LOKESH DM U.S.A(TRAIND),SCOM0016,,,,,,,1,300,300,,,03/16/2013 00:00:00,personal,03/16/2013 00:00:00,IU000004,IU000010,1,1,7,Frontoffice2,0,,300|DOC00012,\"M.LAXMI RAMESH M.S.,MCh\",SCOM0001,,,,,,,1,350,350,,,01/17/2014 00:00:00,Frontoffice2,03/16/2013 00:00:00,IU000010,IU000015,1,1,15,frontoffice1,0,,350|DOC00013,\"Dr.P.V.PRADEEP M.S.,DNB.,MRSCED.,M.Ch.\",SCOM0041,,,,,,,1,300,300,,,06/24/2013 00:00:00,Frontoffice2,05/03/2013 00:00:00,IU000010,IU000010,1,1,1,FRONTOFFICE-1,0,,300|DOC00014,Dr.I.L.N REDDY MD,SCOM0029,,,,,,,1,0,0,,,,FRONTOFFICE-1,07/15/2013 00:00:00,IU000012,,1,0,7,,0,,|DOC00015,\"I.L.N.REDDY, M.D.,\",SCOM0042,,,,,,,1,150,150,,,10/24/2013 00:00:00,xyz-8a346907d9b,07/15/2013 00:00:00,IU000010,U0000000,1,1,7,IP1,0,,150|DOC00016,\"K.SUPRAJA MBBS.,DGO,D.DIAB\",SCOM0002,,,,,,,1,0,0,,,11/30/2013 00:00:00,IP1,11/30/2013 00:00:00,U0000000,IU000015,1,1,7,IP1,0,,0|DOC00017,\"C.KODHANDAPANI RAMAANUJADAASUDU M.S.,M.Ch.,\",SCOM0014,,,,,,,1,250,250,,9246291403,12/15/2013 00:00:00,IP1,12/15/2013 00:00:00,U0000000,U0000000,1,1,7,IP1,0,,250|DOC00018,\"Guru Prasad M.D.,D.M.\",SCOM0014,,,,,,,1,300,300,,,02/15/2014 00:00:00,joshi-PC,02/15/2014 00:00:00,U0000000,U0000000,1,1,7,joshi-PC,0,,300";
		String[] rows = data.split("\\|");

		System.out.println(rows.toString());
		Connection connection = jdbcConnectionPool.getConnectionFromPool();
		;
		connection.setAutoCommit(true);
		for (int i = 0; i < rows.length; i++) {
			String[] column = rows[i].split("\\,");
			String sql = "INSERT INTO SUBCOMPANIES (DOCID,DOCTORNM,SPEID,ADDRESS,AREA,CITY,STATE,PINCODE,PHNO,SEX,FEES,VISIT,PHONENO2,MOBILENO,EDITDATE,TERMINALID,CREATEDATE,CREATEUSERID,EDITUSERID,EMPLOYEE,ACTIVE,FREEDAYS,EDITTERMINALID,SPECFLG,ROOM,EMERGANCYFEE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			int index = 1;
			String str = "";
			for (String s : column) {
				// str = str + "'"+s + "', ";
				if (index == 15 || index == 17) {
					prepstmt.setTimestamp(index++, new Timestamp(new Date(32564).getTime()));
					continue;
				} else if (index == 11 || index == 12 || index == 26) {
					prepstmt.setFloat(index++, Float.parseFloat(s.trim()));
					continue;
				} else if (index == 20 || index == 21 || index == 22 || index == 24 || index == 25) {
					prepstmt.setInt(index++, Integer.parseInt(s.trim()));
					continue;
				}
				prepstmt.setString(index++, s);
			}
			// System.out.println(str);
			try {
				prepstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("Failed to insert !!");
			}
		}

	}

	public List<Doctor> getDoctors(String departmentId) {
		String stmt = "select DOCID, DOCTORNM, FEES from DOCTORINFO where SPEID=?";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<Doctor> doctors = new ArrayList<Doctor>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			prepstmt.setString(1, departmentId);
			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				Doctor d = new Doctor();
				d.setId(result.getString("DOCID"));
				d.setName(result.getString("DOCTORNM"));
				d.setFee(result.getDouble("FEES"));
				doctors.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getDoctors() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return doctors;
	}

	public List<Bank> getBanks() {
		String stmt = "select BANKID, BANKNM, ACTIVE from BANKMAST";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<Bank> banks = new ArrayList<Bank>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				Bank bank = new Bank();
				bank.setId(result.getString("BANKID"));
				bank.setName(result.getString("BANKNM"));
				bank.setActive(result.getInt("ACTIVE"));
				banks.add(bank);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getBanks() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return banks;
	}

	public List<Source> getSources(String src) {
		String stmt = "select SOURCEID, NAME, SPECIALITY, TYPE from REFERRALS where type='" + src + "'";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<Source> sources = new ArrayList<Source>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			// prepstmt.setString(1, src);
			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				Source source = new Source();
				source.setId(result.getString("SOURCEID"));
				source.setName(result.getString("NAME"));
				source.setDepartment(result.getString("SPECIALITY"));
				source.setType(result.getString("TYPE"));
				sources.add(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getBanks() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return sources;
	}

	public List<City> getCities(String prefix) {

		String stmt = "select AREAID, AREANM, DISTRICTNAME, STATENAME, c.DISTRICTID, c.STATEID from CITYS c JOIN DISTRICTS d ON (c.DISTRICTID = d.DISTRICTID) JOIN STATES s ON (c.STATEID = s.STATEID) where AREANM LIKE '"
				+ prefix + "%'";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<City> cities = new ArrayList<City>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				City city = new City();
				city.setId(result.getString("AREAID"));
				city.setName(result.getString("AREANM"));
				city.setDistrict(result.getString("DISTRICTNAME"));
				city.setState(result.getString("STATENAME"));
				city.setDistrictId(result.getString("DISTRICTID"));
				city.setStateId(result.getString("STATEID"));
				cities.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getBanks() - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return cities;
	}

	public List<Authorisation> getAuthorisations() {
		String stmt = "select AUTHID, AUTHNAME, USERID from AUTHORISATIONS";

		Connection con = jdbcConnectionPool.getConnectionFromPool();
		List<Authorisation> auths = new ArrayList<Authorisation>();
		try {
			PreparedStatement prepstmt = con.prepareStatement(stmt);

			ResultSet result = prepstmt.executeQuery();
			while (result.next()) {
				Authorisation auth = new Authorisation();
				auth.setId(result.getString("AUTHID"));
				auth.setName(result.getString("AUTHNAME"));
				auth.setUserId(result.getString("USERID"));
				auths.add(auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getAuthorisations - db operation failed." + e.getMessage());
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}
		return auths;
	}

	public RegisterResponse register(RegisterRequest request) {
		String maxId = "select max(REGID) REGID, max(REGNO) REGNO from PATIENTSREGISTRAION";
		Connection con = jdbcConnectionPool.getConnectionFromPool();
		int regId = 0, regNo = 0;
		String sql = "INSERT INTO PATIENTSREGISTRAION (REGID, REGTM, REGDT, REGNO, FNAME, ADDR1, PINCODE, PHONE1, SUBCOMPANYID, CREGNO, AGE, SEX, DEPTID, DOCID, CONSULTFEES, FEESCOLLECTED, REGFEES, "
				+ "BILLNO, REFERTYPE, REFERID, PAID, DUE, DOB, DISCOUNT, NETAMT, DISTRICTID, STATEID, STATENEW, CITYNEW, CITYID, AUTHID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		RegisterResponse response = new RegisterResponse();
		try {
			PreparedStatement prepstmt = con.prepareStatement(maxId);

			ResultSet result = prepstmt.executeQuery();
			if (result.next()) {
				regId = result.getInt("REGID");
				regNo = result.getInt("REGNO");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dob = df.parse(request.getBasicInfo().getDob());
			int index = 1;
			java.util.Date today = new java.util.Date();
			prepstmt = con.prepareStatement(sql);
			prepstmt.setInt(index++, regId+1);
			prepstmt.setDate(index++, new java.sql.Date(today.getTime()));

			today.setHours(0);

			prepstmt.setDate(index++, new Date (today.getTime()));
			prepstmt.setInt(index++, regNo+1);

			prepstmt.setString(index++, request.getBasicInfo().getName());
			prepstmt.setString(index++, request.getAddress().getAddress());
			prepstmt.setString(index++, request.getAddress().getPinCode());
			prepstmt.setString(index++, request.getAddress().getPhone());
			prepstmt.setString(index++, request.getConsultantDepartment().getDepartment());
			prepstmt.setString(index++, "0");
			prepstmt.setInt(index++, getAge(request.getBasicInfo().getDob()));
			prepstmt.setString(index++, request.getBasicInfo().getSex());
			prepstmt.setString(index++, request.getConsultantDepartment().getDepartment());
			prepstmt.setString(index++, request.getConsultantDepartment().getDoctorId());
			prepstmt.setDouble(index++, request.getPaymentDetails().getTotal());
			prepstmt.setDouble(index++, request.getPaymentDetails().getPaidAmount());
			prepstmt.setDouble(index++, request.getPaymentDetails().getNetAmount());
			prepstmt.setString(index++, null);
			prepstmt.setString(index++, getReferType(request.getHospitalInfo().getSourceType()));
			prepstmt.setString(index++, request.getHospitalInfo().getSourceId());
			prepstmt.setDouble(index++, request.getPaymentDetails().getPaidAmount());
			prepstmt.setDouble(index++, request.getPaymentDetails().getNetAmount() - request.getPaymentDetails().getPaidAmount());
			prepstmt.setDate(index++, new Date(dob.getTime()));
			prepstmt.setDouble(index++, request.getPaymentDetails().getDiscount());
			prepstmt.setDouble(index++, request.getPaymentDetails().getNetAmount());
			prepstmt.setString(index++, request.getAddress().getDistrictId());
			prepstmt.setString(index++, request.getAddress().getStateId());
			prepstmt.setString(index++, request.getAddress().getState());
			prepstmt.setString(index++, request.getAddress().getCity());
			prepstmt.setString(index++, request.getAddress().getCityId());
			prepstmt.setString(index++, request.getPaymentDetails().getDiscountDetails());

			int rowsAffected = prepstmt.executeUpdate();

			if(rowsAffected == 1) {
				response.setStatus("success");
				response.setDescription("Patient is successfully registered !");
			} else {
				response.setStatus("failure");
				response.setDescription("Patient registration failed !");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HospitalDAO.getAuthorisations - db operation failed." + e.getMessage());
			response.setStatus("failure");
			response.setDescription("Patient registration failed !");
		} finally {
			jdbcConnectionPool.returnConnectionToPool(con);
		}

		return response;
	}

	private String getReferType(String sourceType) {

		switch (sourceType) {
		case "SELF":
			return ONE;
		case "REFERRAL":
			return TWO;
		case "ORG":
			return THREE;
		}
		return ZERO;
	}

	private int getAge(String dob) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.parse(dob);
			GregorianCalendar birth = new GregorianCalendar();
			GregorianCalendar now = new GregorianCalendar();
			birth.setTime(df.parse(dob));
			now.setTime(new java.util.Date());
			return (now.get(GregorianCalendar.YEAR) - birth.get(GregorianCalendar.YEAR));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
}
