package edu.java.scrapper.client;

import edu.java.configuration.ApplicationConfig;
import java.time.Duration;

public class RetryBuilder {
    private final ApplicationConfig.Retry retry;

    public RetryBuilder(int maxAttempts, int[] statusCodes) {
        this.retry = new ApplicationConfig.Retry();
        this.retry.setMaxAttempts(maxAttempts);
        this.retry.setStatusCodes(statusCodes);
    }

    public ApplicationConfig.Retry constant(long step) {
        retry.setPolicy(ApplicationConfig.RetryPolicy.CONSTANT);
        retry.setStep(Duration.ofMillis(step));
        return retry;
    }

    public ApplicationConfig.Retry linear(long initialInterval, long increment, long maxInterval) {
        retry.setPolicy(ApplicationConfig.RetryPolicy.LINEAR);
        retry.setInitialInterval(Duration.ofMillis(initialInterval));
        retry.setIncrement(Duration.ofMillis(increment));
        retry.setMaxInterval(Duration.ofMillis(maxInterval));
        return retry;
    }

    public ApplicationConfig.Retry exponent(long initialInterval, double multiplier, long maxInterval) {
        retry.setPolicy(ApplicationConfig.RetryPolicy.EXPONENT  );
        retry.setInitialInterval(Duration.ofMillis(initialInterval));
        retry.setMultiplier(multiplier);
        retry.setMaxInterval(Duration.ofMillis(maxInterval));
        return retry;
    }
}
