package spcr.config;

import org.constretto.annotation.Configuration;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AWSConfig {
	@Configuration("access.key")
	private String accessKey;
	@Configuration("secret.key")
	private String secretKey;
}
