package org.spotifumtp37.model.user;

import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.subscription.SubscriptionPlan;

public abstract class User {
    private final String name;
    private final String email;
    private final String address;
    private SubscriptionPlan subscriptionplan;
    private final String password;
    private double pontos;
    //teste commit
    //  private String nome2;
//}

    public User(String name, String email, String address, SubscriptionPlan subscriptionPlan, String password, double pontos) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.subscriptionplan = subscriptionPlan;
        this.password = password;
        this.pontos = pontos;
    }

    public User(User other) {
        this.name = other.getName();
        this.email = other.getEmail();
        this.address = other.getAddress();
        this.subscriptionplan = other.getSubscriptionPlan();
        this.password = other.getPassword();
        this.pontos = other.getPontos();
    }

    public User() {
        this.name = "";
        this.email = "";
        this.address = "";
        this.subscriptionplan = new FreePlan();
        this.password = "";
        this.pontos = 0;
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

    public String getPassword() {
        return password;
    }

    public double getPontos() {
        return pontos;
    }

    public void setSubscriptionPlan(SubscriptionPlan newPlan) {
        if (newPlan != null) {
            this.subscriptionplan = newPlan;  // Update the user's subscription plan with the new one
        } else {
            throw new IllegalArgumentException("Subscription plan cannot be null.");
        }
    }

    public void somarPontos() {
        double newPontos = subscriptionplan.adicionaPontos(pontos); // Get the new points from the plan
        setPontos(newPontos);
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }
}

