    package com.shop.smart_commerce_api.entities;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import org.hibernate.annotations.ColumnDefault;

    import java.time.Instant;
    import java.util.LinkedHashSet;
    import java.util.Set;

    @Getter
    @Setter
    @Entity
    @Table(name = "reviews", schema = "smart_commerce")
    public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        private Product product;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_review_id")
        private Review parentReview;

        @Column(name = "rating")
        private Integer rating;

        @Column(name = "comment", length = 200)
        private String comment;

        @ColumnDefault("CURRENT_TIMESTAMP")
        @Column(name = "created_at")
        private Instant createdAt;

        @OneToMany(mappedBy = "parentReview")
        private Set<Review> reviews = new LinkedHashSet<>();

    }