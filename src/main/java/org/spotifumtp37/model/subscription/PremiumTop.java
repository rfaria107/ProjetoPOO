package org.spotifumtp37.model.subscription;

public class PremiumTop implements SubscriptionPlan {
    @Override
    public double adicionaPontos(double pontos) {
        return 1.025* pontos;
    }
    @Override
    public boolean podeCriarPlaylist() {
        return true;
    }
    @Override
    public boolean podeNavegarPlaylist() {
        return true;
    }
    @Override
    public boolean podeAcessarPlaylistsGeradasAutomaticamente() {
        return true;
    }
}
