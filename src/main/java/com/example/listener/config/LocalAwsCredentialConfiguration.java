package com.example.listener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

/**
 * We should be able to provide configuration like this in the yaml, but it's not working as
 * of 9/8/2023.  Should be able to provide something like:
 * spring:
 *   cloud:
 *     aws:
 *       credentials:
 *         profile: business-non-prod
 *
 * But it returns an error, so manually creating the credential bean below 👇
 */
@Configuration
@Profile("local")
public class LocalAwsCredentialConfiguration {

    @Value("${spring.cloud.aws.credentials.profile}")
    String profile;

    @Bean
    public AwsCredentialsProvider localAwsCredentialsProvider() {
        return ProfileCredentialsProvider
                .builder()
                .profileName(profile)
                .build();
    }
}
