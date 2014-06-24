package ru.ydn.orienteer.web;

import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.orienteer.OrienteerWebApplication;
import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;

@MountPath("/logout")
public class LogoutPage extends BasePage {
	public LogoutPage()
	{
		OrientDbWebSession session = OrientDbWebSession.get();
		if(session.isSignedIn())
		{
			session.signOut();
		}
		setResponsePage(OrienteerWebApplication.get().getHomePage());
	}
}
