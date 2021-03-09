package mail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class AutenticatorProvider
  extends Authenticator
{
  String user;
  String pw;
  
  public AutenticatorProvider(String username, String password) {
    this.user = username;
    this.pw = password;
  }
  
  public PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(this.user, this.pw);
  }
}