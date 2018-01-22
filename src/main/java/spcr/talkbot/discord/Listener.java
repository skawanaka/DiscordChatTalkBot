package spcr.talkbot.discord;

import lombok.extern.slf4j.Slf4j;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

@Slf4j
public class Listener {
	final static long GUILD_ID = 0l;
	private final static long VOICE_CHANNEL_ID = 0l;

	@EventSubscriber
	public void onReadyEvent(final ReadyEvent event) {
		final IDiscordClient client = event.getClient();
		final IGuild guild = client.getGuildByID(GUILD_ID);
		final IVoiceChannel voiceChannel = guild.getVoiceChannelByID(VOICE_CHANNEL_ID);
		voiceChannel.join();
	}
}
