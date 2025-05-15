package org.spotifumtp37.model.user;

import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.subscription.PremiumBase;
import org.spotifumtp37.model.subscription.PremiumTop;
import org.spotifumtp37.model.subscription.SubscriptionPlan;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String email;
    private String address;
    private SubscriptionPlan subscriptionplan;
    private String password;
    private double pontos;
    private List<History> history;

    public User(String name, String email, String address, SubscriptionPlan subscriptionPlan, String password, double pontos, List<History> history) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.subscriptionplan = subscriptionPlan;
        this.password = password;
        this.pontos = pontos;
        this.history = new ArrayList<>(history);
        for (History h : history) {
            this.history.add(h.clone());
        }
    }

    public User(User other) {
        this.name = other.getName();
        this.email = other.getEmail();
        this.address = other.getAddress();
        this.subscriptionplan = other.getSubscriptionPlan();
        this.password = other.getPassword();
        this.pontos = other.getPontos();
        this.history = new ArrayList<>();
        for (History h : history) {
            this.history.add(h.clone());
        }
    }

    public User() {
        this.name = "";
        this.email = "";
        this.address = "";
        this.subscriptionplan = new FreePlan();
        this.password = "";
        this.pontos = 0;
        this.history = new ArrayList<>();
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

    public List<History> getHistory() {
        List<History> copy = new ArrayList<>(history);
        for (History h : history) {
            copy.add(h.clone());
        }
        return copy;
    }

    public void setSubscriptionPlan(SubscriptionPlan newPlan) {
        if (newPlan != null) {
            this.subscriptionplan = newPlan;  // Update the user's subscription plan with the new one
        } else {
            throw new IllegalArgumentException("Subscription plan cannot be null.");
        }
    }


    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    public void setHistory(List<History> history) {
        this.history = new ArrayList<>(history);
        for (History h : history) {
            this.history.add(h.clone());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void somarPontos() {
        double newPontos = subscriptionplan.addPoints(pontos); // Get the new points from the plan
        setPontos(newPontos);
    }

    public User clone() {
        return new User(this);
    }

    public void updatePremiumTop(PremiumTop newPlan) {
        this.setSubscriptionPlan(newPlan);
        this.pontos += 100;
    }

    public void updateFreePlan(FreePlan newPlan) {
        this.setSubscriptionPlan(newPlan);
    }

    public void updateHistory(Song song) {
        History h = new History();
        LocalDateTime time = LocalDateTime.now();
        h.setSong(song);
        h.setTime(time);
        this.history.add(h);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", subscriptionplan=" + subscriptionplan +
                ", password='" + password + '\'' +
                ", pontos=" + pontos +
                ", history=" + history +
                '}';
    }
}

