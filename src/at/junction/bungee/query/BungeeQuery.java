package at.junction.bungee.query;

import java.lang.Thread;
import net.md_5.bungee.api.plugin.Plugin;

//Hacked together by zifnab, please ignore the shitty code
public class BungeeQuery extends Plugin {
	private final int port = 25566;
    @Override
    public void onEnable() {
		new Thread(new BungeeQueryListener(port, this)).start();
		super.onEnable();
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}
}
