package org.spotifumtp37.model.subscription;

import java.io.Serializable;

public class PremiumBase implements Serializable, SubscriptionPlan {
    @Override
    public double addPoints(double points) {
        return points + 10;
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
    public boolean canAccessFavouritesList() {
        return false;
    }
}
