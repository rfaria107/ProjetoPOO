package org.spotifumtp37.model.user;

import org.spotifumtp37.model.album.Album;
import org.spotifumtp37.model.album.Song;
import org.spotifumtp37.model.subscription.FreePlan;
import org.spotifumtp37.model.subscription.PremiumTop;
import org.spotifumtp37.model.subscription.SubscriptionPlan;

import java.util.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
        this.history = new ArrayList<>();
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
        for (History h : other.history) {
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
        List<History> copy = new ArrayList<>();
        for (History h : this.history) {
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
        this.history = new ArrayList<>();
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

    public void addPoints() {
        double newPoints = subscriptionplan.addPoints(pontos); // Get the new points from the plan
        setPontos(newPoints);
    }

    public User clone() {
        return new User(this);
    }

    public void updatePremiumTop(PremiumTop newPlan) {
        this.setSubscriptionPlan(newPlan);
        this.pontos += 100;
    }

    public void updateHistory(Song song) {
        History h = new History();
        LocalDateTime time = LocalDateTime.now();
        h.setSong(song.clone());
        h.setTime(time);
        this.history.add(h);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", subscription plan=" + subscriptionplan +
                ", password='" + password + '\'' +
                ", pontos=" + pontos +
                ", history=" + history +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name); // Compare by name
    }

    public String getTopGenre() {
        if (history == null || history.isEmpty()) return null;

        Map<String, Integer> genreCount = new HashMap<>();
        for (History h : history) {
            Song song = h.getSong();
            if (song != null && song.getGenre() != null) {
                String genre = song.getGenre();
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }
        return genreCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<Song> createTopGenrePlaylist(Map<String, Album> albumMap) {
        String topGenre = getTopGenre();
        if (topGenre == null || albumMap == null) return Collections.emptyList();

        List<Song> result = new ArrayList<>();
        for (Album album : albumMap.values()) {
            for (Song song : album.getSongs()) {
                if (topGenre.equals(song.getGenre())) {
                    result.add(song);
                }
            }
        }
        return result;
    }

    public List<Song> createTopGenrePlaylistWithinTime(Map<String, Album> albumMap, int maxTime) {
        String topGenre = getTopGenre();
        if (topGenre == null || albumMap == null) return Collections.emptyList();

        int totalTime = 0;
        List<Song> playlist = new ArrayList<>();
        outer:
        for (Album album : albumMap.values()) {
            for (Song song : album.getSongs()) {
                if (topGenre.equals(song.getGenre())) {
                    int songTime = song.getDurationInSeconds();
                    if (totalTime + songTime > maxTime) break outer;
                    playlist.add(song);
                    totalTime += songTime;
                }
            }
        }
        return playlist;
    }

    public List<Song> createTopGenreExplicitPlaylist(Map<String, Album> albumMap) {
        String topGenre = getTopGenre();
        if (topGenre == null || albumMap == null) return Collections.emptyList();

        List<Song> result = new ArrayList<>();
        for (Album album : albumMap.values()) {
            for (Song song : album.getSongs()) {
                if (topGenre.equals(song.getGenre()) && song.isExplicit()) {
                    result.add(song);
                }
            }
        }
        return result;
    }





}

