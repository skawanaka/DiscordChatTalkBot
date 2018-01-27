package spcr.speakbot.aws;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.LanguageCode;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import spcr.config.AWSConfig;
import spcr.config.ConfigReader;

public class Synthesizer extends ConfigReader {
	private final AmazonPolly pollyClient;
	private final Voice voice;

	public Synthesizer() {
		final AWSConfig awsConfig = this.configuration.as(AWSConfig.class);
		final BasicAWSCredentials credentials = new BasicAWSCredentials(awsConfig.getAccessKey(),
				awsConfig.getSecretKey());
		this.pollyClient = AmazonPollyClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_NORTHEAST_1)
				.build();
		final DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest()
				.withLanguageCode(LanguageCode.JaJP);
		final DescribeVoicesResult describeVoicesResult = this.pollyClient.describeVoices(describeVoicesRequest);
		final List<Voice> voices = describeVoicesResult.getVoices();
		// 0でTakumi
		// 1でMizuki
		this.voice = voices.get(0);
	}

	public AudioInputStream synthesize(final String text, final OutputFormat format)
			throws UnsupportedAudioFileException, IOException {
		final SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(text)
				.withVoiceId(this.voice.getId()).withOutputFormat(format);
		final SynthesizeSpeechResult synthRes = this.pollyClient.synthesizeSpeech(synthReq);
		final AudioInputStream audioStream = AudioSystem.getAudioInputStream(synthRes.getAudioStream());
		return audioStream;
	}
}