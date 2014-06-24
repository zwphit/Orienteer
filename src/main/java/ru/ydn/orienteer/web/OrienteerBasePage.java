package ru.ydn.orienteer.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;

public abstract class OrienteerBasePage extends BasePage
{

	public OrienteerBasePage()
	{
		super();
	}

	public OrienteerBasePage(IModel<?> model)
	{
		super(model);
	}

	public OrienteerBasePage(PageParameters parameters)
	{
		super(parameters);
	}

	@Override
	public void initialize() {
		super.initialize();
		add(new Label("pageHeader", getTitleModel()));
		boolean signedIn = OrientDbWebSession.get().isSignedIn();
		add(new BookmarkablePageLink<Object>("login", LoginPage.class).setVisible(!signedIn));
		add(new BookmarkablePageLink<Object>("logout", LogoutPage.class).setVisible(signedIn));
	}

}
