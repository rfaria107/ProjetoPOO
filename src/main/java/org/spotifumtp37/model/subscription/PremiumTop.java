package org.spotifumtp37.model.subscription;

import java.io.Serializable;

public class PremiumTop implements Serializable, SubscriptionPlan {
    @Override
    public double addPoints(double points) {
        return 1.025 * points;
    }

    @Override
    public boolean canCreatePlaylist() {
        return true;
    }

    @Override
    public boolean canBrowsePlaylist() {
        return true;
    }

    @Override
    public boolean canAcessFavouritesList() {
        return true;
    }
}
