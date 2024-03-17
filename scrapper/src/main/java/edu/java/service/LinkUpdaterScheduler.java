package edu.java.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class LinkUpdaterScheduler {
    private final ILinkUpdaterService linkUpdaterService;

    public LinkUpdaterScheduler(ILinkUpdaterService linkUpdaterService) {
        this.linkUpdaterService = linkUpdaterService;
    }

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() {
        log.info("Updating...");
        int updated = linkUpdaterService.update();
        log.info(String.format("Updated %d links", updated));
    }
}
