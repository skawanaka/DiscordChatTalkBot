package spcr.config;

import org.constretto.annotation.Configuration;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DiscordConfig {
	@Configuration("token")
	private String token;
	@Configuration("ready.join.voice.channel.id")
	private long voiceChannelId;
}
