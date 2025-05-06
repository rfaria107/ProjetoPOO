package org.spotifumtp37.model.subscription;

public interface SubscriptionPlan {
    double adicionaPontos(double pontos);
    boolean podeCriarPlaylist();
    boolean podeNavegarPlaylist();
    boolean podeAcessarPlaylistsGeradasAutomaticamente();
}