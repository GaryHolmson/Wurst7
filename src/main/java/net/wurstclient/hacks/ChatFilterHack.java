/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.ChatInputListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.StringListSetting;

@SearchTags({"chat filter", "filter", "chat"})
public final class ChatFilterHack extends Hack implements ChatInputListener
{
	
	private final StringListSetting stringListSetting = new StringListSetting(
			"Strings",
			"Removes any messages containing the following strings.");
	
	public ChatFilterHack()
	{
		super("Chat Filter",
			"Removes messages matching the provided strings.");
		setCategory(Category.CHAT);
		
		addSetting(stringListSetting);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(ChatInputListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(ChatInputListener.class, this);
	}
	
	@Override
	public void onReceivedMessage(ChatInputEvent event)
	{
		for (String value : stringListSetting.getValues()) {
			if (event.getComponent().getString().contains(value)) {
				event.cancel();
				break;
			}
		}
	}
	
}
