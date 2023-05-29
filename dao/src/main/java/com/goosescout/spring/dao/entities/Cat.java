package com.goosescout.spring.dao.entities;

import com.goosescout.spring.dao.common.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cats")
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(generator = "increment")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "birth_date", nullable = false)
    @Getter
    @Setter
    private LocalDate birthDate;

    @Column(name = "breed")
    @Getter
    @Setter
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    @Getter
    @Setter
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @Getter
    @Setter
    private CatOwner owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
         name = "cats_friends",
         joinColumns = @JoinColumn(name = "cat_id"),
         inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private final Set<Cat> friends = new HashSet<>();

    public Set<Cat> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    /**
     * Adds a friend to the cat.
     * Both cats will be added to each other's friends list.
     *
     * @param cat the cat to add as a friend
     */
    public void addFriend(Cat cat) {
        this.friends.add(cat);
        cat.friends.add(this);
    }

    /**
     * Removes a friend from the cat.
     * Both cats will be removed from each other's friends list.
     *
     * @param cat the cat to remove as a friend
     */
    public void removeFriend(Cat cat) {
        this.friends.remove(cat);
        cat.friends.remove(this);
    }

    public Cat(String name, LocalDate birthDate, String breed, Color color, CatOwner owner) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }

    @PreRemove
    private void removeFriends() {
        for (Cat friend : friends) {
            friend.friends.remove(this);
        }
    }
}
