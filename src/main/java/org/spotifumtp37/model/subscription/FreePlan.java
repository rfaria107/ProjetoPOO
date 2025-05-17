package org.spotifumtp37.model.subscription;

import java.io.Serializable;

public class FreePlan implements SubscriptionPlan, Serializable {

    @Override
    public double addPoints(double points) {
        return 5 + points;
    }

    @Override
    public boolean canCreatePlaylist() {
        return false;
    }

    @Override
    public boolean canBrowsePlaylist() {
        return false;
    }

    @Override
    public boolean canAccessFavouritesList() {
        return false;
    }
}
