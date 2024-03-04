package edu.java.bot.controller.dto;

import edu.java.bot.controller.validation.annotation.ILink;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkUpdateRequestDto {
    private long id;
    @ILink
    private String url;
    private String description;
    private List<Long> tgChatIds;
}
