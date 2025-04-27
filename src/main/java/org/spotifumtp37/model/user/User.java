package org.spotifumtp37.model.user;

import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.subscription.SubscriptionPlan;

public abstract class User {
    private final String name;
    private final String email;
    private final String address;
    private SubscriptionPlan subscriptionplan;

    //teste commit
    //  private String nome2;
//}

    public User(String name, String email, String address, SubscriptionPlan subscriptionPlan) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.subscriptionplan = subscriptionPlan;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionplan;
    }

    public void setSubscriptionPlan(SubscriptionPlan newPlan) {
        if (newPlan != null) {
            this.subscriptionplan = newPlan;  // Update the user's subscription plan with the new one
        } else {
            throw new IllegalArgumentException("Subscription plan cannot be null.");
        }
    }

}
