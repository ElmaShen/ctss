package proj.ctworld.ctss.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.log4j.Logger;

import proj.ctworld.core.lib.ModelErrorException;
import proj.ctworld.ctss.controller.AnnouncementController;

public class WindowsADService {

	private static Logger logger = Logger.getLogger(WindowsADService.class);
	// public getUser() {
	//
	// public static void main(String[] args) {
	// String ldapURL = "ldap://192.168.1.1:389";
	// String account = "test";
	// String password = "123456";
	// try {
	// LDAP_AUTH_AD(ldapURL, account, password);
	// System.out.println("VerifySuccess!");
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// }

	/**
	 * 利用Windows AD 驗證帳密是否正確
	 * @param ldap_url AD 的server jndi
	 * @param account 驗證的帳號
	 * @param password 驗證的密碼
	 * @return 若驗證成功，則回傳 0；若驗證失敗，則回傳 -1
	 * @throws Exception 其他錯誤會送Exception
	 */
	public static int LDAP_AUTH_AD(String ldap_url, String account, String password) throws ModelErrorException {
		if (account.isEmpty() || password.isEmpty())
			throw new ModelErrorException("Username or Password is empty!");

		int code = 0;
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// 傳入AD Server位置
		env.put(Context.PROVIDER_URL, ldap_url);
		// 可以設定三種參數(none, simple, strong)
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		// 傳入帳號並冠上Domain
//		env.put(Context.SECURITY_PRINCIPAL, account + "@test.idv");
		env.put(Context.SECURITY_PRINCIPAL, account);
		// 密碼
		env.put(Context.SECURITY_CREDENTIALS, password);

		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);

		} catch (javax.naming.AuthenticationException e) {
			logger.debug("test1");
//			throw new Exception("Verify Fail!");
			code = -1;
		} catch (javax.naming.CommunicationException e) {
			logger.debug("test2");
			throw new ModelErrorException("Can't FindServer!");
		} catch (Exception e) {
			logger.debug("test3");
			throw new ModelErrorException("OtherError!");
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
				}
			}
		}
		logger.debug("test4");
		return code;
	}
	// }

}
