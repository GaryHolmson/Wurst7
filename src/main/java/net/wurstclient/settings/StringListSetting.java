/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.wurstclient.WurstClient;
import net.wurstclient.clickgui.Component;
import net.wurstclient.clickgui.components.StringListEditButton;
import net.wurstclient.keybinds.PossibleKeybind;
import net.wurstclient.util.json.JsonException;
import net.wurstclient.util.json.JsonUtils;

import java.util.*;

public final class StringListSetting extends Setting
{
	
	private ArrayList<String> strings;
	private String[] defaults;
	
	public StringListSetting(String name, String description)
	{
		super(name, description);
		this.strings = new ArrayList<>();
	}
	
	public StringListSetting(String name, String description, String... defaults) {
		super(name, description);
		this.strings = (ArrayList<String>) Arrays.asList(defaults);
		Collections.sort(this.strings);
		this.defaults = defaults;
	}
	
	public ArrayList<String> getValues()
	{
		return strings;
	}
	
	public void resetToDefaults() {
		this.strings = (ArrayList<String>) Arrays.asList(defaults);
		Collections.sort(this.strings);
	}
	
	public void add(String string) {
		
		if (string == null)
			return;
		
		if(Collections.binarySearch(strings, string) >= 0)
			return;
		
		strings.add(string);
		Collections.sort(strings);
		WurstClient.INSTANCE.saveSettings();
		
	}
	
	public void remove(int index)  {
		
		if(index < 0 || index >= strings.size())
			return;
		
		strings.remove(index);
		WurstClient.INSTANCE.saveSettings();
		
	}
	
	@Override
	public Component getComponent()
	{
		return new StringListEditButton(this);
	}
	
	@Override
	public void fromJson(JsonElement json)
	{
		
		try {
			this.strings = new ArrayList<>(JsonUtils.getAsArray(json).getAllStrings());
			Collections.sort(strings);
		} catch (JsonException e) {
			this.strings = new ArrayList<>();
		}
		
	}
	
	@Override
	public JsonElement toJson()
	{
		JsonArray jsonArray = new JsonArray();
		strings.forEach(jsonArray::add);
		return jsonArray;
	}
	
	@Override
	public LinkedHashSet<PossibleKeybind> getPossibleKeybinds(
		String featureName)
	{
		return new LinkedHashSet<>();
	}
}
