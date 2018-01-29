package spcr.config;

import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.constretto.model.ClassPathResource;

public abstract class ConfigReader {

	protected final ConstrettoConfiguration configuration;

	public ConfigReader() {
		this.configuration = new ConstrettoBuilder().createPropertiesStore()
				.addResource(new ClassPathResource("config.properties")).done().getConfiguration();
	}
}
