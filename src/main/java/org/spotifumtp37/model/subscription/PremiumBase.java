package org.spotifumtp37.model.subscription;

public class PremiumBase implements SubscriptionPlan {
    @Override
    public double adicionaPontos(double pontos) {
        return pontos +10;
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
        return false;
    }
}
