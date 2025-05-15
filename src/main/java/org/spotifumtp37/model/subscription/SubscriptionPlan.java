package org.spotifumtp37.model.subscription;

import java.io.Serializable;

public interface SubscriptionPlan extends Serializable {
    double adicionaPontos(double pontos);

    boolean podeCriarPlaylist();

    boolean podeNavegarPlaylist();

    boolean podeAcessarPlaylistsGeradasAutomaticamente();
}