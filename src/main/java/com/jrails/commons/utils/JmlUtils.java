package com.jrails.commons.utils;

import net.sf.jml.*;
import net.sf.jml.event.MsnContactListAdapter;
import net.sf.jml.event.MsnMessengerAdapter;
import net.sf.jml.impl.MsnMessengerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-4-11
 * Time: 14:16:38
 * To change this template use File | Settings | File Templates.
 */
public class JmlUtils {

    private String email;

    private String password;

    private MsnMessenger messenger;

    public JmlUtils(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void start() {
        messenger = MsnMessengerFactory.createMsnMessenger(email, password);
        messenger.setSupportedProtocol(new MsnProtocol[]{MsnProtocol.MSNP11});
        messenger.getOwner().setInitStatus(MsnUserStatus.OFFLINE);
        messenger.addMessengerListener(new MsnMessengerAdapter(){
                public void loginCompleted(MsnMessenger messenger) {
                    System.out.println(messenger.getOwner().getEmail() + " login");                 
                }
                public void logout(MsnMessenger messenger) {
                    System.out.println(messenger.getOwner().getEmail() + " logout");
                }
            }
        );
        //添加好友列表更新后事务
        messenger.addContactListListener(new MsnContactListAdapter()
		{
            public void contactListInitCompleted(MsnMessenger messenger)
			{
				MsnContact[] cons = messenger.getContactList().getContacts();
                for (MsnContact con : cons) {
                    System.out.println(con.getEmail());
                }
                messenger.logout();
            }
		});       
        messenger.login();
 }


    public static void main(String[] args) throws Exception {

    }
}
