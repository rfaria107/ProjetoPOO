package org.spotifumtp37.model.subscription;

import java.io.Serializable;

public interface SubscriptionPlan extends Serializable {
    double addPoints(double points);

    boolean canCreatePlaylist();

    boolean canBrowsePlaylist();

    boolean canAcessFavouritesList();
}