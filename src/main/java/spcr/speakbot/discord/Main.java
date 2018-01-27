package spcr.speakbot.discord;
import spcr.config.ConfigReader;
import spcr.config.DiscordConfig;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class Main extends ConfigReader {
	private final String TOKEN;

	public static void main(final String[] args) {
		new Main().run();
	}

	public Main() {
		final DiscordConfig discordConfig = this.configuration.as(DiscordConfig.class);
		this.TOKEN = discordConfig.getToken();
	}

	public void run() {
		final IDiscordClient client = Main.createClient(this.TOKEN, true);
		final EventDispatcher dispatcher = client.getDispatcher();
		dispatcher.registerListener(new Listener());
	}

	public static IDiscordClient createClient(final String token, final boolean login) {
		final ClientBuilder clientBuilder = new ClientBuilder().withToken(token);
		if (login) {
			return clientBuilder.login();
		}
		return clientBuilder.build();
	}
}