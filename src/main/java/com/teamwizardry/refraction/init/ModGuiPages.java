package com.teamwizardry.refraction.init;

import com.teamwizardry.librarianlib.features.gui.provided.book.provider.PageTypes;
import com.teamwizardry.refraction.client.gui.tablet.*;

public class ModGuiPages {

	public static void init() {
		PageTypes.INSTANCE.registerPageProvider( "picture", PagePicture::new);
		PageTypes.INSTANCE.registerPageProvider( "textmodular", PageTextModular::new);


		PageTextModular.registerParser(new TextModularPlayerParser());
		PageTextModular.registerParser(new TextModularConfigParser());
		PageTextModular.registerParser(new TextModularItemParser());
	}
}
