package com.example.photoAlbum.controller;

import com.example.photoAlbum.response.AlbumRest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    OAuth2AuthorizedClientService oauth2ClientService;

    @Autowired
    WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(@NotNull Model model,
                            @AuthenticationPrincipal OidcUser principal) {
        String url = "http://localhost:8083/albums";
        List<AlbumRest> albums = webClient.get().uri(url)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>() {
                }).block();
        model.addAttribute("albums", albums);
        return "albums";
    }
}
