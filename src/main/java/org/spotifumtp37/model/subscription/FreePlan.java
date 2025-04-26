package org.spotifumtp37.model.subscription;

public class FreePlan implements SubscriptionPlan {

    @Override
    public double adicionaPontos(double pontos) {
        return 5 + pontos;
    }

    @Override
    public boolean podeCriarPlaylist() {
        return false;
    }

    @Override
    public boolean podeNavegarPlaylist() {
        return false;
    }

    @Override
    public boolean podeAcessarPlaylistsGeradasAutomaticamente() {
        return false;
    }
}
