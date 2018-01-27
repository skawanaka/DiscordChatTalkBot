package spcr.speakbot.discord;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.amazonaws.services.polly.model.OutputFormat;

import lombok.extern.slf4j.Slf4j;
import spcr.speakbot.aws.Synthesizer;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.audio.providers.AudioInputStreamProvider;

@Slf4j
public class MessageSpeaker implements IListener<MessageReceivedEvent> {
	private final Synthesizer polly = new Synthesizer();

	@EventSubscriber
	public void onMessageReceivedEvent(final MessageReceivedEvent event) {
		try {
			final IMessage message = event.getMessage();
			final IGuild guild = message.getGuild();
			final String content = message.getContent();
			final String speechContent = content.replaceAll("<.+>", "");
			if (speechContent.isEmpty()) {
				return;
			}
			final IAudioManager audioManager = guild.getAudioManager();
			final AudioInputStream input = this.polly.synthesize(speechContent, OutputFormat.Mp3);
			final AudioInputStreamProvider provider = new AudioInputStreamProvider(input);
			audioManager.setAudioProvider(provider);
			log.info("content:{}を再生します。", speechContent);
		} catch (IOException | UnsupportedAudioFileException e) {
			log.warn("MessageReceivedEvent処理中に例外発生", e);
		}
	}

	@Override
	public void handle(final MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}
}
