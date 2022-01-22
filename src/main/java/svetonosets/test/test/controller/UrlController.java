package svetonosets.test.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import svetonosets.test.test.dto.UrlCountResponse;
import svetonosets.test.test.dto.UrlIdRequest;
import svetonosets.test.test.dto.UrlLongRequest;
import svetonosets.test.test.service.UrlService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "url", description = "the url API")
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Operation(summary = "Create short url from long version", description = "This can be done by anyone.", tags = { "url" })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @PostMapping("/create-short")
    public String convertToShortUrl(@RequestBody UrlLongRequest request) {
        return urlService.convertToShortUrl(request);
    }

    @Operation(summary = "Delete url from db.", description = "This can be done by admins.", tags = { "url" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "forbidden operation")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete-short")
    public ResponseEntity<?> deleteUrlFromDb(@RequestBody UrlIdRequest request) {
        return urlService.deleteUrlById(request.getUrlId());
    }

    @Operation(summary = "Redirect", description = "Finds original url from short and redirects.", tags = { "url", "redirect" })
    @ApiResponses(value = { @ApiResponse(responseCode = "302", description = "successful operation") })
    @GetMapping(value = "/get/{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl, HttpServletRequest request) {
        String url = urlService.getOriginalUrl(shortUrl, request);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @Operation(summary = "Number of redirections", description = "Count rows in db.", tags = { "count", "redirect" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "forbidden operation")
    })
    @GetMapping(value = "/count/{urlId}")
    @Cacheable(value = "urlCount", key = "#urlId", sync = true)
    public ResponseEntity<?> getCountOfRedirections(@PathVariable long urlId) {
        return urlService.getUrlCountResponse(urlId);
    }

    @Operation(summary = "Statistic of url", description = "Count rows in db.", tags = { "count", "statistic" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "403", description = "forbidden operation")
    })
    @GetMapping(value = "/stats/{urlId}")
    @Cacheable(value = "urlStats", key = "#urlId", sync = true)
    public ResponseEntity<?> getUrlStatistic(@PathVariable long urlId) {
        return urlService.getStatisticOfUrl(urlId);
    }
}
