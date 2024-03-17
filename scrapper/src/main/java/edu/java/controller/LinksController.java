package edu.java.controller;

import edu.java.controller.dto.AddLinkRequestDto;
import edu.java.controller.dto.ApiErrorResponseDto;
import edu.java.controller.dto.LinkResponseDto;
import edu.java.controller.dto.ListLinksResponseDto;
import edu.java.controller.dto.RemoveLinkRequestDto;
import edu.java.model.Link;
import edu.java.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@Tag(name = "Links", description = "API for working with links")
public class LinksController {
    private final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    @Operation(summary = "Get all tracking links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully got links", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "Chat not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        })
    })
    public ResponseEntity<ListLinksResponseDto> getLinks(@RequestHeader("Tg-Chat-Id") long id) {
        List<LinkResponseDto> links = linkService.listAll(id).stream().map(link -> new LinkResponseDto(
            link.id(), link.url())).toList();
        return ResponseEntity.ok().body(new ListLinksResponseDto(links, links.size()));
    }

    @PostMapping
    @Operation(summary = "Add tracking link", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true, content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = AddLinkRequestDto.class))
    }
    ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Link successfully added", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "Chat not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "409", description = "Link is already tracking", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        })
    })
    public ResponseEntity<?> addLink(
        @RequestHeader("Tg-Chat-Id") long id,
        @Valid @RequestBody AddLinkRequestDto addLinkRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return createBadRequestResponse(bindingResult);
        }
        linkService.add(addLinkRequest.getLink(), id);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping
    @Operation(summary = "Remove link tracking", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true, content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveLinkRequestDto.class))
    }
    ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully removed link", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponseDto.class))
        }),
        @ApiResponse(responseCode = "404",
                     description = "Chat not found or doesn't contain the specified link",
                     content = {
                         @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ApiErrorResponseDto.class))
                     })
    })
    public ResponseEntity<?> deleteLink(
        @RequestHeader("Tg-Chat-Id") long id,
        @Valid @RequestBody RemoveLinkRequestDto removeLinkRequest,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return createBadRequestResponse(bindingResult);
        }
        linkService.remove(removeLinkRequest.link(), id);
        return ResponseEntity.ok().body(null);
    }

    private ResponseEntity<ApiErrorResponseDto> createBadRequestResponse(BindingResult bindingResult) {
        return ResponseEntity.badRequest()
            .body(new ApiErrorResponseDto(
                bindingResult.getAllErrors().getFirst().getDefaultMessage(),
                "400",
                "",
                "",
                List.of()
            ));
    }
}
