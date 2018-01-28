package spcr.speakbot.discord;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import spcr.config.ConfigReader;
import spcr.config.DiscordConfig;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

@Slf4j
public class ChannelJoiner extends ConfigReader {
	private final long voiceChannelId;

	public ChannelJoiner() {
		final DiscordConfig config = this.configuration.as(DiscordConfig.class);
		this.voiceChannelId = config.getVoiceChannelId();
	}

	@EventSubscriber
	public void onReadyEvent(final ReadyEvent event) {
		final IDiscordClient client = event.getClient();
		final List<IGuild> guilds = client.getGuilds();
		for (final IGuild guild : guilds) {
			final IVoiceChannel voiceChannel = guild.getVoiceChannelByID(this.voiceChannelId);
			if (voiceChannel == null) {
				continue;
			}
			voiceChannel.join();
			break;
		}
	}
}
